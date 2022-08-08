package org.willingoxjin.sentinel.demo;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.Collections;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Jin.Nie
 * @date 2022-07-15
 */
@SpringBootApplication
public class SentinelWebDemoApp {

	public static void initFlowRules() {
		FlowRule rule = new FlowRule();
		//	注意： 我们的规则一定要绑定到对应的资源上，通过资源名称进行绑定
		rule.setResource("org.willingoxjin.sentinel.demo.controller.TestSentinelController#testFlow");
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setCount(2);
		// 规则管理器
		FlowRuleManager.loadRules(Collections.singletonList(rule));
	}

	public static void initDegradeRules() {
		DegradeRule rule = new DegradeRule();
		rule.setResource("org.willingoxjin.sentinel.demo.controller.TestSentinelController#testDegrade");
		// 统计10000ms内的请求。若大于5个请求，且请求中有20%的请求调用最大时长超过了50ms，则熔断20s
		// 设置熔断计算的类型为慢调用比例类型
		rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
		// 最大RT; 超过50ms则认为是慢调用
		rule.setCount(50);
		// 慢调用比例; 请求统计中占20%则会熔断
		rule.setSlowRatioThreshold(0.2D);
		// 熔断时长; 熔断20s
		rule.setTimeWindow(20);
		// 最小请求数; 当请求数大于5时，才会计算是否达到慢调用比例
		rule.setMinRequestAmount(5);
		// 统计时长; 统计10000ms内的请求
		rule.setStatIntervalMs(10000);
		DegradeRuleManager.loadRules(Collections.singletonList(rule));
	}


	public static void main(String[] args) {
        new SpringApplicationBuilder(SentinelWebDemoApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

		initFlowRules();
		initDegradeRules();
    }

}
