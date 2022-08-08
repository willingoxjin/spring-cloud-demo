package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleStore;
import com.alibaba.csp.sentinel.dashboard.rule.RuleConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.RuleType;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public class DynamicRuleApolloStore<T extends RuleEntity> extends DynamicRuleStore<T> {

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleApolloStore.class);

    private final ApolloProperties apolloProperties;

    private final ApolloConfig apolloConfig;

    public DynamicRuleApolloStore(final RuleType ruleType,
            final ApolloProperties apolloProperties, ApolloConfig apolloConfig) {
        super.ruleType = ruleType;
        this.apolloProperties = apolloProperties;
        this.apolloConfig = apolloConfig;
    }


    @Override
    public List<T> getRules(final String appName) throws Exception {
        String appId = apolloProperties.getAppConfigMap().get(appName).getAppId();
        String env = apolloProperties.getEnv();
        String clusterName = apolloProperties.getClusterName();
        String namespace = apolloProperties.getNamespaceName();
        String flowDataId = RuleConfigUtil.getDataId(appName, ruleType);
        ApolloOpenApiClient apolloOpenApiClient = apolloConfig.createApolloOpenApiClient(appName);
        OpenNamespaceDTO openNamespaceDTO = apolloOpenApiClient.getNamespace(appId, env, clusterName, namespace);
        String rules = openNamespaceDTO
                .getItems()
                .stream()
                .filter(p -> p.getKey().equals(flowDataId))
                .map(OpenItemDTO::getValue)
                .findFirst()
                .orElse("");

        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        Converter<String, List<T>> decoder = RuleConfigUtil.getDecoder(ruleType.getClazz());
        return decoder.convert(rules);
    }

    @Override
    public void publish(final String app, final List<T> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }

        String appId = apolloProperties.getAppConfigMap().get(app).getAppId();
        String env = apolloProperties.getEnv();
        String clusterName = apolloProperties.getClusterName();
        String namespace = apolloProperties.getNamespaceName();
        String userId = apolloProperties.getUserId();
        String dataId = RuleConfigUtil.getDataId(app, ruleType);
        Converter<Object, String> encoder = RuleConfigUtil.getEncoder();
        String value = encoder.convert(rules);
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(dataId);
        openItemDTO.setValue(value);
        openItemDTO.setComment("update from sentinel-dashboard");
        openItemDTO.setDataChangeCreatedBy(userId);
        openItemDTO.setDataChangeLastModifiedBy(userId);
        ApolloOpenApiClient apolloOpenApiClient = apolloConfig.createApolloOpenApiClient(app);
        apolloOpenApiClient.createOrUpdateItem(appId, env, clusterName, namespace, openItemDTO);

        // Release configuration
        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setEmergencyPublish(true);
        namespaceReleaseDTO.setReleaseComment("Modify or add configurations");
        namespaceReleaseDTO.setReleaseTitle("Modify or add configurations");
        namespaceReleaseDTO.setReleasedBy(userId);
        apolloOpenApiClient.publishNamespace(appId, env, clusterName, namespace, namespaceReleaseDTO);
        log.info("publish rule success - app: {}, type: {}, value: {}", app, ruleType.getName(), value);
    }
}
