package org.willingoxjin.sentinel.demo.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Jin.Nie
 * @date 2022-07-24
 */
public class ApolloDataSourceListener implements InitializingBean {
    private static final String FLOW_RULE_TYPE = "flow";

    private static final String DEGRADE_RULE_TYPE = "degrade";
    //  *-flow-rules
    private static final String FLOW_DATA_ID_POSTFIX = "-" + FLOW_RULE_TYPE + "-rules";

    private String applicationName ;

    public ApolloDataSourceListener(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFlowRules();
        /**
         *  TODO 初始化其他的规则
         *      initDegradeRules();
         *      initAuthorityRuleRules();
         *      ......
         */
    }

    private void initFlowRules() {
        // apollo-test-flow-rules
        String flowRuleKey = applicationName + FLOW_DATA_ID_POSTFIX;
        // 动态监听
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource =
                new ApolloDataSource<>("application",
                        flowRuleKey,
                        "[]",
                        source -> JSON.parseObject(
                                source,
                                new TypeReference<List<FlowRule>>() {
                                }
                        ));
        // 刷新内存
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }


}
