package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jin.Nie
 * @date 2022-08-05
 */
@ConfigurationProperties(prefix = "apollo.portal")
public class ApolloProperties implements InitializingBean {

    private boolean enable = false;

    private String url = "http://127.0.0.1/8070";

    private String env = "DEV";

    private String clusterName = "default";

    private String namespaceName = "application";

    private String userId = "apollo";

    private List<ApolloAppConfig> appConfigs = new ArrayList<>();

    private Map<String, ApolloAppConfig> appConfigMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        if (appConfigs != null) {
            appConfigs.forEach(app -> {
                appConfigMap.put(app.getAppName(), app);
            });
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public List<ApolloAppConfig> getAppConfigs() {
        return appConfigs;
    }

    public void setAppConfigs(List<ApolloAppConfig> appConfigs) {
        this.appConfigs = appConfigs;
    }

    public Map<String, ApolloAppConfig> getAppConfigMap() {
        return appConfigMap;
    }

    public void setAppConfigMap(
            Map<String, ApolloAppConfig> appConfigMap) {
        this.appConfigMap = appConfigMap;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
