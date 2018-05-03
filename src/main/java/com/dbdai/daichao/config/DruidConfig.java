package com.dbdai.daichao.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author hebao 数据源实例配置
 *
 */
@Configuration
public class DruidConfig {

	private Logger logger = LoggerFactory.getLogger(DruidConfig.class);
	// 主数据库配置
	@Value("${druid.datasource.base.mainJdbcUrl}")
	private String mainBaseJdbcUrl;
	@Value("${druid.datasource.base.mainUserName}")
	private String mainBaseUserName;
	@Value("${druid.datasource.base.mainPassword}")
	private String mainBasePassword;

	// 从数据库配置
	@Value("${druid.datasource.base.slaveJdbcUrl}")
	private String slaveBaseJdbcUrl;
	@Value("${druid.datasource.base.slaveUserName}")
	private String slaveBaseUserName;
	@Value("${druid.datasource.base.slavePasswod}")
	private String slaveBasePasswod;

	@Bean(name = "mainBaseDataSource", initMethod = "init", destroyMethod = "close")
	public DataSource mainBaseDataSource() {
		DruidDataSource mainDataSource = new DruidDataSource();
		mainDataSource.setInitialSize(5);
		mainDataSource.setMaxActive(20);
		mainDataSource.setMinIdle(3);
		mainDataSource.setMaxWait(60000);
		mainDataSource.setTimeBetweenEvictionRunsMillis(60000);
		mainDataSource.setMinEvictableIdleTimeMillis(300000);
		mainDataSource.setValidationQuery("select 1");
		mainDataSource.setTestWhileIdle(true);
		mainDataSource.setTestOnBorrow(false);
		mainDataSource.setTestOnReturn(false);
		mainDataSource.setUrl(mainBaseJdbcUrl);
		mainDataSource.setUsername(mainBaseUserName);
		mainDataSource.setPassword(mainBasePassword);
		logger.info("主库数据源设置完成");
		return mainDataSource;
	}

	@Bean(name = "slaveBaseDataSource", initMethod = "init", destroyMethod = "close")
	public DataSource slaveBaseDataSource() {
		DruidDataSource mainDataSource = new DruidDataSource();
		mainDataSource.setInitialSize(5);
		mainDataSource.setMaxActive(20);
		mainDataSource.setMinIdle(3);
		mainDataSource.setMaxWait(60000);
		mainDataSource.setTimeBetweenEvictionRunsMillis(60000);
		mainDataSource.setMinEvictableIdleTimeMillis(300000);
		mainDataSource.setValidationQuery("select 1");
		mainDataSource.setTestWhileIdle(true);
		mainDataSource.setTestOnBorrow(false);
		mainDataSource.setTestOnReturn(false);
		mainDataSource.setUrl(slaveBaseJdbcUrl);
		mainDataSource.setUsername(slaveBaseUserName);
		mainDataSource.setPassword(slaveBasePasswod);
		logger.info("从库数据源设置完成");
		return mainDataSource;
	}

}
