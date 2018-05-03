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

	@Resource(name = "mainDunDataSource")
	private DruidDataSource mainDunDataSource;
	@Resource(name = "slaveDunDataSource")
	private DruidDataSource slaveDunDataSource;

	@Bean(name = "dunDataSource")
	public DataSource dbRouterDunDatasource() {
		BaseRouteDataSource dataSource = new BaseRouteDataSource();
		Map<Object, Object> dataSources = new HashMap<Object, Object>();
		dataSources.put(DataSourceEnum.WRITE, mainDunDataSource);
		dataSources.put(DataSourceEnum.READ, slaveDunDataSource);
		dataSource.setTargetDataSources(dataSources);
		dataSource.setDefaultTargetDataSource(mainDunDataSource);
		return dataSource;
	}

}
