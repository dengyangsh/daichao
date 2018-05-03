package com.dbdai.daichao.dao;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.dbdai.daichao.util.DBRouter;

public class BaseRouteDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		if (DBRouter.getCurrent() == null) {
			// 初始化时设置为写数据库
			DBRouter.switchToWrite();
		}
		return DBRouter.getCurrent();
	}
}
