package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public class DynamicRuleStoreFactory {

    private final Map<RuleType, DynamicRuleStore<? extends RuleEntity>> storeMap;

    public DynamicRuleStoreFactory(final List<DynamicRuleStore<? extends RuleEntity>> storeList) {
        Objects.requireNonNull(storeList, "dynamic store must not be null");
        storeMap = new HashMap<>(storeList.size());
        storeList.forEach(item -> storeMap.putIfAbsent(item.getRuleType(), item));
    }

    @SuppressWarnings({"unchecked"})
    public <T extends RuleEntity> DynamicRuleStore<T> getDynamicRuleStoreByType(final RuleType ruleType) {
        DynamicRuleStore<T> store = (DynamicRuleStore<T>) storeMap.get(ruleType);
        if (store == null) {
            throw new RuntimeException("can not find DynamicRuleStore by type: " + ruleType.getName());
        }
        return store;
    }

}