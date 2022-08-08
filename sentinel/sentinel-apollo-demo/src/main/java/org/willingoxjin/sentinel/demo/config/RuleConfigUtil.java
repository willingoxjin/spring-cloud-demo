package org.willingoxjin.sentinel.demo.config;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public class RuleConfigUtil {

    public static final String DATA_ID_POSTFIX = "rules";

    private RuleConfigUtil() {
    }

    public static String getDataId(String appName, RuleType ruleType) {
        return String.format("%s-%s-%s", appName, ruleType.getName(), DATA_ID_POSTFIX);
    }


}
