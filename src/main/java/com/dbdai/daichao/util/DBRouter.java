package com.dbdai.daichao.util;

import com.dbdai.daichao.constant.DataSourceEnum;

/**
 * @author hebao 数据库动态切换操作类
 *
 */
public class DBRouter {
	private static final ThreadLocal<DataSourceEnum> CONTEXT_HOLDER = new ThreadLocal<DataSourceEnum>();

	public static void switchToWrite() {
		CONTEXT_HOLDER.set(DataSourceEnum.WRITE);
	}

	public static void switchToRead() {
		CONTEXT_HOLDER.set(DataSourceEnum.READ);
	}

	public static DataSourceEnum getCurrent() {
		return CONTEXT_HOLDER.get();
	}

	public static void switchToDb(DataSourceEnum db) {
		CONTEXT_HOLDER.set(db);
	}

	public static void clear() {
		CONTEXT_HOLDER.remove();
	}

}
