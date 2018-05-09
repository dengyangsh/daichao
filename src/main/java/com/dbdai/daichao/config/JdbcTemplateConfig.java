package com.dbdai.daichao.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

@Configuration
public class JdbcTemplateConfig {

	private static Logger logger = LoggerFactory.getLogger(JdbcTemplateConfig.class);

	@Resource(name = "daichaoDataSource")
	private DataSource daichaoDataSource;


	@Bean(name = "jdbcDaichao")
	public NamedParameterJdbcTemplate jdbcCS() {
		Assert.notNull(daichaoDataSource, "数据源csDataSource不存在没有实例化");
		NamedParameterJdbcTemplate jdbcCS = new NamedParameterJdbcTemplate(daichaoDataSource);
		logger.info("daichaoDataSource jdbcTemplate初始化成功");
		return jdbcCS;
	}

}
