package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleStore;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleStoreFactory;
import com.alibaba.csp.sentinel.dashboard.rule.RuleType;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin.Nie
 * @date 2022-08-05
 */
@Configuration
@ConditionalOnProperty(
        prefix = "apollo.portal",
        name = {"enable"},
        havingValue = "true",
        matchIfMissing = false
)
@ComponentScan("com.alibaba.csp.sentinel.dashboard.rule.apollo")
@EnableConfigurationProperties(ApolloProperties.class)
public class ApolloConfig {

    @Autowired
    private ApolloProperties apolloProperties;

    private final ConcurrentMap<String, ApolloOpenApiClient> apolloClientMap = new ConcurrentHashMap<>();

    public ApolloOpenApiClient createApolloOpenApiClient(String appName) {
        ApolloOpenApiClient apolloOpenApiClient = apolloClientMap.get(appName);
        if (apolloOpenApiClient != null) {
            return apolloOpenApiClient;
        }

        String url = apolloProperties.getUrl();
        ApolloAppConfig apolloAppConfig = apolloProperties.getAppConfigMap().get(appName);
        String token = apolloAppConfig.getToken();

        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl(url)
                .withToken(token)
                .build();
        apolloClientMap.putIfAbsent(appName, client);
        return client;
    }

    @Bean
    public DynamicRuleStoreFactory dynamicRuleStoreFactory() {
        RuleType[] ruleTypes = RuleType.values();

        List<DynamicRuleStore<? extends RuleEntity>> storeList = new ArrayList<>();
        for (RuleType ruleType : ruleTypes) {
            Class<RuleEntity> clazz = ruleType.getClazz();
            DynamicRuleStore<? extends RuleEntity> ruleStore = new DynamicRuleApolloStore<>(
                    ruleType,
                    apolloProperties,
                    this
            );
            storeList.add(ruleStore);
        }
        return new DynamicRuleStoreFactory(storeList);
    }

}
