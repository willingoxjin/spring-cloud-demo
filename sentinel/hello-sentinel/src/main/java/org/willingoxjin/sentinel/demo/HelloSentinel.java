package org.willingoxjin.sentinel.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jin.Nie
 * @date 2022-07-14
 */
public class HelloSentinel {

    /**
     	对主流的5种流控策略做了底层的抽象和资源的封装

      	对于规则： FlowRule 、DegradeRule、ParamFlowRule、SystemRule、AuthorityRule
      	对于管理器：FlowRuleManager、DegradeRuleManager、ParamFlowRuleManager、SystemRuleManager、AuthorityRuleManager
        对于异常：FlowException、DegradeException、ParamFlowException、SystemBlockException、AuthorityException

     */

    public static void hello() {
        System.out.println("hello: 访问redis");
        System.out.println("hello: 访问mysql");
        System.out.println("hello: 保存数据到redis");
    }

    /**
     * 初始化流量控制规则 FlowRule，并将其加入到流量控制管理器中
     */
    public static void intFlowRules() {
        FlowRule rule = new FlowRule();
        // 限制的资源
        rule.setResource("hello");
        // 流量控制阈值类型(0:线程数，1:QPS)。默认为1-QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置QPS为20，每秒最多20个请求
        rule.setCount(20);

        // 将规则加入到规则管理器
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.forEach(item -> {
            if (item.equals(4)) {
                return;
            }
            System.out.println(item);
        });


        // // 1.初始化流量控制规则
        // intFlowRules();
        //
        // // 2.定义资源
        // while (true) {
        //     // 1.5.0 版本开始可以直接利用 try-with-resources 特性
        //     // 1.1 定义资源名称
        //     try (Entry entry = SphU.entry("hello")) {
        //         // 1.2 执行资源逻辑代码.
        //         hello();
        //         Thread.sleep(20);
        //     } catch (BlockException e) {
        //         // 资源访问阻止，被限流或被降级
        //         // 进行相应的处理操作
        //         System.out.println("blocked! 要访问的资源被流控");
        //     }
        // }

    }

}
