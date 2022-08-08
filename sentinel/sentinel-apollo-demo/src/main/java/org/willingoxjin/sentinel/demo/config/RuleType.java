package org.willingoxjin.sentinel.demo.config;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public enum RuleType {

    /**
     * 流控规则
     */
    FLOW("flow"),
    /**
     * 熔断规则
     */
    DEGRADE("degrade"),
    /**
     * 热点规则
     */
    PARAM_FLOW("param-flow"),
    /**
     * 系统规则
     */
    SYSTEM("system"),
    /**
     * 授权规则
     */
    AUTHORITY("authority"),
    /**
     * 网关流控规则
     */
    GW_FLOW("gw-flow"),
    /**
     * api 分组
     */
    GW_API_GROUP("gw-api-group");

    /**
     * alias for {@link com.alibaba.csp.sentinel.slots.block.AbstractRule}.
     */
    private final String name;

    RuleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<RuleType> of(String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        return Arrays.stream(RuleType.values())
                .filter(ruleType -> name.equals(ruleType.getName())).findFirst();
    }

}
