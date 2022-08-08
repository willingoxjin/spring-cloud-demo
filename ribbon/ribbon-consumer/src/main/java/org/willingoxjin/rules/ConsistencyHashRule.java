package org.willingoxjin.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash
 * @author Jin.Nie
 * @date 2022-01-02
 */
@NoArgsConstructor
public class ConsistencyHashRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        // 获取请求内容
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 根据实际的业务来获取摘要，可谓用户id、订单id
        String uri = request.getRequestURI() + "?" + request.getQueryString();

        return route(uri.hashCode(), getLoadBalancer().getAllServers());
    }

    /**
     * 根据路由hash获取服务
     * @param hashId 哈希值
     * @param addressList 服务列表
     * @return 获取的服务
     */
    public Server route(int hashId, List<Server> addressList) {
        if (CollectionUtils.isEmpty(addressList)) {
            return null;
        }

        TreeMap<Long, Server> address = new TreeMap<>();
        addressList.stream().forEach(e -> {
            // 虚化若干个节点，到hash环上；key为serverId
            for (int i = 0; i < 8; i++) {
                long hash = hash(e.getId());
                address.put(hash, e);
            }
        });

        long hash = hash(String.valueOf(hashId));
        // 找到大于hash节点的所有Server
        SortedMap<Long, Server> last = address.tailMap(hash);

        // 当requestURL的hash值大于任意一个服务器对应的hashKey（即到达尾环/首尾相连的情况）
        // 取address的第一个节点
        if (last.isEmpty()) {
            return address.firstEntry().getValue();
        }

        // 距离hash最近的Server
        return last.get(last.firstKey());
    }

    /**
     * 一致性hash
     * md5 增加节点的散列 需要优化，需要尽可能的散列均匀
     */
    private static long hash(String key) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] keyBytes = null;
        try {
            keyBytes = key.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // 左移16位，就是在右边补上16个零，左移8位就是补8个0，最后一个不补零
        // 三块合在一起组成了一个long类型的数字
        long hashCode = ((long) ((digest[2] & 0xFF) << 16))
                | ((long) ((digest[1] & 0xFF) << 8))
                | ((long) (digest[0] & 0xFF));

        // 取低32位
        return hashCode & 0xFFFFFFFFL;
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println(hash("abcdefgwasdsadfsdfsfdsasda/adasasdaas65486486486456465as55666?asdasdasdas78"));
    }

}
