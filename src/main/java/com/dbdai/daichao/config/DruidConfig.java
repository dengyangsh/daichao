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
	@Value("${druid.datasource.daichao.mainJdbcUrl}")
	private String mainDaichaoJdbcUrl;
	@Value("${druid.datasource.daichao.mainUserName}")
	private String mainDaichaoUserName;
	@Value("${druid.datasource.daichao.mainPassword}")
	private String mainDaichaoPassword;

	// 从数据库配置
	@Value("${druid.datasource.daichao.slaveJdbcUrl}")
	private String slaveDaichaoJdbcUrl;
	@Value("${druid.datasource.daichao.slaveUserName}")
	private String slaveDaichaoUserName;
	@Value("${druid.datasource.daichao.slavePasswod}")
	private String slaveDaichaoPasswod;

	@Bean(name = "mainDaichaoDataSource", initMethod = "init", destroyMethod = "close")
	public DataSource mainDaichaoDataSource() {
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
		mainDataSource.setUrl(mainDaichaoJdbcUrl);
		mainDataSource.setUsername(mainDaichaoUserName);
		mainDataSource.setPassword(mainDaichaoPassword);
		logger.info("主库数据源设置完成");
		return mainDataSource;
	}

	@Bean(name = "slaveDaichaoDataSource", initMethod = "init", destroyMethod = "close")
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
		mainDataSource.setUrl(slaveDaichaoJdbcUrl);
		mainDataSource.setUsername(slaveDaichaoUserName);
		mainDataSource.setPassword(slaveDaichaoPasswod);
		logger.info("从库数据源设置完成");
		return mainDataSource;
	}

}
