package com.dbdai.daichao.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.dbdai.daichao.constant.DataSourceEnum;
import com.dbdai.daichao.dao.BaseRouteDataSource;

/**
 * 配置多数据源
 * 
 * @author hebao
 *
 */
@Configuration
public class DynamicDataSourceConfig {

	@Resource(name = "mainDaichaoDataSource")
	private DruidDataSource mainDaichaoDataSource;
	@Resource(name = "slaveDaichaoDataSource")
	private DruidDataSource slaveDaichaoDataSource;

	@Bean(name = "DaichaoDataSource")
	public DataSource dbRouterDaichaoDatasource() {
		BaseRouteDataSource dataSource = new BaseRouteDataSource();
		Map<Object, Object> dataSources = new HashMap<Object, Object>();
		dataSources.put(DataSourceEnum.WRITE, mainDaichaoDataSource);
		dataSources.put(DataSourceEnum.READ, slaveDaichaoDataSource);
		dataSource.setTargetDataSources(dataSources);
		dataSource.setDefaultTargetDataSource(mainDaichaoDataSource);
		return dataSource;
	}

}
