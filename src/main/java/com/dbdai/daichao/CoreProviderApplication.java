package com.dbdai.daichao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CoreProviderApplication {
	private static Logger logger = LoggerFactory.getLogger(CoreProviderApplication.class);

	public static void main(String[] args) {
		System.setProperty("org.grlea.logBridge.LogBridgeFactory", "com.hebao.bixia.core.aop.Log4j2LogBridgeFactory");
		logger.info("开始启动项目");
		new SpringApplicationBuilder(CoreProviderApplication.class).web(true).run(args);
		logger.info("项目启动成功");
	}
}
