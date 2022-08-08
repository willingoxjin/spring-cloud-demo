package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public enum RuleType {

    /**
     * 流控规则
     */
    FLOW("flow", FlowRuleEntity.class),
    /**
     * 熔断规则
     */
    DEGRADE("degrade", DegradeRuleEntity.class),
    /**
     * 热点规则
     */
    PARAM_FLOW("param-flow", ParamFlowRuleEntity.class),
    /**
     * 系统规则
     */
    SYSTEM("system", SystemRuleEntity.class),
    /**
     * 授权规则
     */
    AUTHORITY("authority", AuthorityRuleEntity.class),
    /**
     * 网关流控规则
     */
    GW_FLOW("gw-flow", GatewayFlowRuleEntity.class),
    /**
     * api 分组
     */
    GW_API_GROUP("gw-api-group", ApiDefinitionEntity.class);

    /**
     * alias for {@link com.alibaba.csp.sentinel.slots.block.AbstractRule}.
     */
    private final String name;

    /**
     * concrete {@link com.alibaba.csp.sentinel.slots.block.AbstractRule} class.
     */
    private Class<? extends RuleEntity> clazz;

    RuleType(String name, Class<? extends RuleEntity> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public <T extends RuleEntity> Class<T> getClazz() {
        return (Class<T>) clazz;
    }

    public static Optional<RuleType> of(String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        return Arrays.stream(RuleType.values())
                .filter(ruleType -> name.equals(ruleType.getName())).findFirst();
    }

}
