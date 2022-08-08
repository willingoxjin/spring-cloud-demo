package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import java.util.List;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public abstract class DynamicRuleStore<T extends RuleEntity> implements DynamicRuleProvider<List<T>>,
        DynamicRulePublisher<List<T>> {

    protected RuleType ruleType;

    public RuleType getRuleType() {
        return ruleType;
    }

}
