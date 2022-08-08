package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author Jin.Nie
 * @date 2022-08-07
 */
public class RuleConfigUtil {

    private static final Converter<Object, String> ENCODER = JSON::toJSONString;
    private static final ConcurrentMap<Class<?>, Object> DECODER_MAP = new ConcurrentHashMap<>();

    public static final String DATA_ID_POSTFIX = "rules";

    private RuleConfigUtil() {
    }

    public static String getDataId(String appName, RuleType ruleType) {
        return String.format("%s-%s-%s", appName, ruleType.getName(), DATA_ID_POSTFIX);
    }

    public static Converter<Object, String> getEncoder() {
        return ENCODER;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T extends RuleEntity> Converter<String, List<T>> getDecoder(Class<T> clazz) {
        Object decoder = DECODER_MAP.computeIfAbsent(clazz, new Function<Class<?>, Converter<String, List<T>>>() {
            @Override
            public Converter<String, List<T>> apply(final Class<?> targetClass) {
                return source -> JSON.parseArray(source, clazz);
            }
        });
        return (Converter<String, List<T>>) decoder;
    }

}
