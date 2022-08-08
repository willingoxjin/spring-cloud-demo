package com.alibaba.csp.sentinel.dashboard.rule.apollo;

/**
 * @author Jin.Nie
 * @date 2022-08-05
 */
public class ApolloAppConfig {

    private String appName;

    private String token;

    private String appId;

    private String thirdId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }
}
