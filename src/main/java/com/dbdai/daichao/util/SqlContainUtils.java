package com.dbdai.daichao.util;

import java.util.HashMap;
import java.util.Map;


/**
 *  @author whitemoon
 *
 */
public class SqlContainUtils {
	/* ��������map */
	public static Map<String, String> sqlAdd = new HashMap<String, String>();
	/* ɾ�� ����map */
	public static Map<String, String> sqlDelete = new HashMap<String, String>();
	/* �޸�����map */
	public static Map<String, String> sqlUpdate = new HashMap<String, String>();
	/* ��ѯ����map */
	public static Map<String, String> sqlFind = new HashMap<String, String>();
	
	public static void putAdd(String key,String value) {
		sqlAdd.put(key, value);
	}
	public static String getAdd(String key) {
		return sqlAdd.get(key);
	}
	public static void putDelete(String key,String value) {
		sqlAdd.put(key, value);
	}
	public static String getDelete(String key) {
		return sqlAdd.get(key);
	}
	public static void putUpdate(String key,String value) {
		sqlAdd.put(key, value);
	}
	public static String getUpdate(String key) {
		return sqlAdd.get(key);
	}
	public static void putFind(String key,String value) {
		sqlAdd.put(key, value);
	}
	public static String getFind(String key) {
		return sqlAdd.get(key);
	}

}
