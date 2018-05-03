package com.dbdai.daichao.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.dbdai.daichao.util.ReflectUtils;
import com.dbdai.daichao.util.SqlContainUtils;
import com.hebao.bixia.common.page.PageView;

/**
 * 对底层接口的一个实现
 *
 * @param <T>
 * @author jianfu.wang
 */

public abstract class BaseDAOImpl<T> implements BaseDAO<T> {
	@SuppressWarnings("unchecked")
	protected Class<T> entitryClass = ReflectUtils.getSuperClassGenricType(this.getClass());

	public NamedParameterJdbcTemplate jdbc;

	private static Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

	public void setJdbc(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public int add(T o) {
		String sql = SqlContainUtils.getAdd(entitryClass.getName() + "_add");
		if (sql == null) {
			sql = addDeal(1);
			SqlContainUtils.putAdd(entitryClass.getName() + "_add", sql);
		}
		return jdbc.update(sql, new BeanPropertySqlParameterSource(o));
	}

	@Override
	public int addBatch(List<T> os) {
		String sql = SqlContainUtils.getAdd(entitryClass.getName() + "_add");
		if (sql == null) {
			sql = addDeal(1);
			SqlContainUtils.putAdd(entitryClass.getName() + "_add", sql);
		}
		List<BeanPropertySqlParameterSource> sources = new ArrayList<BeanPropertySqlParameterSource>();
		for (T t : os) {
			sources.add(new BeanPropertySqlParameterSource(t));
		}
		return jdbc.batchUpdate(sql, sources.toArray(new BeanPropertySqlParameterSource[sources.size()])).length;
	}

	private String addDeal(int type) {
		String sql;
		StringBuffer sqlBuffer = new StringBuffer("insert into ");
		String tableName = ReflectUtils.getObjectTableName(entitryClass);
		List<String> proNames = type == 1 ? ReflectUtils.getPropertyName(entitryClass)
				: ReflectUtils.getPropertyNameNoID(entitryClass);
		sqlBuffer.append(tableName + " (");
		StringBuffer temp = new StringBuffer();
		int size = proNames.size();
		for (int i = 0; i < size; i++) {
			sqlBuffer.append(proNames.get(i) + ",");
			temp.append(":" + proNames.get(i) + ",");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
		temp.deleteCharAt(temp.length() - 1);
		sqlBuffer.append(") values (" + temp.toString() + ")");
		sql = sqlBuffer.toString();
		return sql;
	}

	@Override
	public int addWithoutId(T o) {
		String sql = SqlContainUtils.getAdd(entitryClass.getName() + "_addID");
		if (sql == null) {
			sql = addDeal(2);
			SqlContainUtils.putAdd(entitryClass.getName() + "_addID", sql);
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(sql, new BeanPropertySqlParameterSource(o), keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void delete(Serializable... ids) {
		String sql = SqlContainUtils.getDelete(entitryClass.getName() + "_delete");
		if (sql == null) {
			StringBuffer sb = new StringBuffer("delete from " + ReflectUtils.getObjectTableName(entitryClass)
					+ " where " + ReflectUtils.getObjectIDName(entitryClass) + " =?");
			sql = sb.toString();
			SqlContainUtils.putDelete(entitryClass.getName() + "_delete", sql);
		}
		List<Object[]> list = new ArrayList<Object[]>();
		for (Serializable id : ids) {
			list.add(new Object[] { id });
		}
		jdbc.getJdbcOperations().batchUpdate(sql, list);
	}

	@Override
	public int update(T o) {
		String sql = SqlContainUtils.getUpdate(entitryClass.getName() + "_update");
		if (sql == null) {
			StringBuffer sb = new StringBuffer("update " + ReflectUtils.getObjectTableName(entitryClass) + " set ");
			List<String> proNames = ReflectUtils.getPropertyName(entitryClass);
			int size = proNames.size();
			for (int i = 0; i < size; i++) {
				sb.append(proNames.get(i) + " =:" + proNames.get(i) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" where " + ReflectUtils.getObjectIDName(entitryClass) + "=:"
					+ ReflectUtils.getObjectIDName(entitryClass));
			sql = sb.toString();
			SqlContainUtils.putUpdate(entitryClass.getName() + "_update", sql);
		}
		return jdbc.update(sql, new BeanPropertySqlParameterSource(o));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updatePropertes(T o) {
		String sql = null;
		StringBuffer sb = new StringBuffer("update " + ReflectUtils.getObjectTableName(entitryClass) + " set ");
		List<String> proNames = ReflectUtils.getPropertyName(entitryClass);
		int size = proNames.size();
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		// 取消每个属性的安全检查
		for (Field f : fields) {
			f.setAccessible(true);
		}
		for (int j = 0; j < fields.length; j++) {
			for (int i = 0; i < size; i++) {
				try {
					if (proNames.get(i).equals(fields[j].getName()) && fields[j].get(o) != null) {
						sb.append(proNames.get(i) + " =:" + proNames.get(i) + ",");
					}
				} catch (IllegalAccessException e) {
				}
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where " + ReflectUtils.getObjectIDName(entitryClass) + "=:"
				+ ReflectUtils.getObjectIDName(entitryClass));
		sql = sb.toString();
		SqlContainUtils.putUpdate(entitryClass.getName() + "_update", sql);
		jdbc.update(sql, new BeanPropertySqlParameterSource(o));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer updatePropertesByColumn(T o, String column) {
		String sql = null;
		StringBuffer sb = new StringBuffer("update " + ReflectUtils.getObjectTableName(entitryClass) + " set ");
		List<String> proNames = ReflectUtils.getPropertyName(entitryClass);
		int size = proNames.size();
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		// 取消每个属性的安全检查
		for (Field f : fields) {
			f.setAccessible(true);
		}
		for (int j = 0; j < fields.length; j++) {
			for (int i = 0; i < size; i++) {
				try {
					if (proNames.get(i).equals(fields[j].getName()) && fields[j].get(o) != null) {
						sb.append(proNames.get(i) + " =:" + proNames.get(i) + ",");
					}
				} catch (IllegalAccessException e) {
				}
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where " + column + "=:" + column);
		sql = sb.toString();
		SqlContainUtils.putUpdate(entitryClass.getName() + "_update", sql);
		return jdbc.update(sql, new BeanPropertySqlParameterSource(o));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer updatePropertesByColumns(T o, List<String> colList) {
		String sql = null;
		StringBuffer sb = new StringBuffer("update " + ReflectUtils.getObjectTableName(entitryClass) + " set ");
		List<String> proNames = ReflectUtils.getPropertyName(entitryClass);
		int size = proNames.size();
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		// 取消每个属性的安全检查
		for (Field f : fields) {
			f.setAccessible(true);
		}
		for (int j = 0; j < fields.length; j++) {
			for (int i = 0; i < size; i++) {
				try {
					if (proNames.get(i).equals(fields[j].getName()) && fields[j].get(o) != null) {
						sb.append(proNames.get(i) + " =:" + proNames.get(i) + ",");
					}
				} catch (IllegalAccessException e) {
				}
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where ");
		for (String item : colList) {
			sb.append(item + "=:" + item + " and ");
		}
		sql = sb.delete(sb.length() - 4, sb.length()).toString();
		SqlContainUtils.putUpdate(entitryClass.getName() + "_update", sql);
		return jdbc.update(sql, new BeanPropertySqlParameterSource(o));
	}

	@Override
	public T find(Serializable id) {
		return this.find(id, false);
	}

	@Override
	public <V> V find(Serializable id, Class<V> cls) {
		return this.find(id, false, cls);
	}

	private T find(Serializable id, boolean lock) {
		String sql = SqlContainUtils.getFind(entitryClass.getName() + "_find");
		if (sql == null) {
			StringBuffer sb = new StringBuffer("select * from " + ReflectUtils.getObjectTableName(entitryClass)
					+ " where " + ReflectUtils.getObjectIDName(entitryClass) + " = ?");
			sql = sb.toString();
			SqlContainUtils.putFind(entitryClass.getName() + "_find", sql);
		}
		if (lock) {
			sql += " for update";
		}
		RowMapper<T> mapper = BeanPropertyRowMapper.newInstance(entitryClass);
		try {
			return jdbc.getJdbcOperations().queryForObject(sql, mapper, id);
		} catch (DataAccessException e) {
		}
		return null;
	}

	private <V> V find(Serializable id, boolean lock, Class<V> voClass) {
		String sql = SqlContainUtils.getFind(entitryClass.getName() + "_find");
		if (sql == null) {
			StringBuffer sb = new StringBuffer("select * from " + ReflectUtils.getObjectTableName(entitryClass)
					+ " where " + ReflectUtils.getObjectIDName(entitryClass) + " = ?");
			sql = sb.toString();
			SqlContainUtils.putFind(entitryClass.getName() + "_find", sql);
		}
		if (lock) {
			sql += " for update";
		}
		RowMapper<V> mapper = BeanPropertyRowMapper.newInstance(voClass);
		try {
			return jdbc.getJdbcOperations().queryForObject(sql, mapper, id);
		} catch (DataAccessException e) {
		}
		return null;
	}

	@Override
	public T lock(Serializable id) {
		return this.find(id, true);
	}

	@Override
	public <V> V lock(Serializable id, Class<V> cls) {
		return this.find(id, true, cls);
	}

	private Map<String, Object> findProperty(String targetPropertys, Serializable id, boolean lock) {
		StringBuffer sql = new StringBuffer("select ");
		if (targetPropertys != null && !"".equals(targetPropertys.trim())) {
			String[] targetProperty = targetPropertys.split(",");
			if (targetProperty != null && targetProperty.length > 0) {
				for (String pro : targetProperty) {
					sql.append(pro + ",");
				}
				sql = sql.deleteCharAt(sql.length() - 1);
			} else {
				sql.append(" * ");
			}
		} else {
			sql.append(" * ");
		}

		sql.append(" from " + ReflectUtils.getObjectTableName(entitryClass) + " where "
				+ ReflectUtils.getObjectIDName(entitryClass) + " = ?");
		if (lock) {
			sql.append(" for update ");
		}
		try {
			return jdbc.getJdbcOperations().queryForMap(sql.toString(), id);
		} catch (DataAccessException e) {
		}
		return null;
	}

	@Override
	public Map<String, Object> findPropertys(String targetPropertys, Serializable id) {
		return findProperty(targetPropertys, id, false);
	}

	@Override
	public List<T> getData() {
		return getData(null, null, null, -1);
	}

	@Override
	public List<T> getData(int limit) {
		return getData(null, null, null, limit);
	}

	@Override
	public List<T> getData(LinkedHashMap<String, String> orderby, int limit) {
		return getData(null, null, orderby, limit);
	}

	@Override
	public List<T> getData(String whereSql, Object[] params, int limit) {
		return getData(whereSql, params, null, limit);
	}

	@Override
	public List<T> getData(LinkedHashMap<String, String> orderby) {
		return getData(null, null, orderby, -1);
	}

	@Override
	public List<T> getData(String whereSql, Object[] params) {
		return getData(whereSql, params, null, -1);
	}

	@Override
	public List<T> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby) {
		return getData(whereSql, params, orderby, -1);
	}

	@Override
	public List<T> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby, int limit) {
		return this.getData(whereSql, params, orderby, limit, entitryClass);
	}

	public <V> List<V> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby, int limit,
			Class<V> voClass) {
		StringBuffer sb = new StringBuffer("select * from " + ReflectUtils.getObjectTableName(entitryClass)
				+ ((whereSql == null || whereSql.trim().equals("")) ? "" : (" where " + whereSql)));
		if (orderby != null && orderby.size() > 0) {
			sb.append(" order by ");
			for (String k : orderby.keySet()) {
				sb.append(k + " " + orderby.get(k) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (limit > 0) {
			sb.append(" limit " + limit);
		}
		List<V> pageView = new ArrayList<V>();
		RowMapper<V> mapper = BeanPropertyRowMapper.newInstance(voClass);
		pageView = jdbc.getJdbcOperations().query(sb.toString(), mapper, params);
		return pageView;
	}

	@Override
	public PageView<T> getPageData(int pageNow, int pageSize, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		return this.getPageData(entitryClass, pageNow, pageSize, whereSql, params, orderby);
	}

	@Override
	public <V> PageView<V> getPageData(Class<V> voClass, int pageNow, int pageSize, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		StringBuffer sb = new StringBuffer("select * from " + ReflectUtils.getObjectTableName(entitryClass)
				+ ((whereSql == null || whereSql.toString().trim().equals("")) ? "" : (" where " + whereSql)));
		if (orderby != null && orderby.size() > 0) {
			sb.append(" order by ");
			for (String k : orderby.keySet()) {
				sb.append(k + " " + orderby.get(k) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (pageNow >= 0 && pageSize >= 0) {
			sb.append(" limit ?,?");
		}
		PageView<V> pageView = new PageView<V>(pageSize, pageNow);
		RowMapper<V> mapper = BeanPropertyRowMapper.newInstance(voClass);
		pageView.setTotalrecord(getRecordCount(whereSql, params));

		try {
			if (whereSql == null || whereSql.toString().equals("")) {
				if (pageNow >= 0 && pageSize >= 0) {
					pageView.setRecords(
							jdbc.getJdbcOperations().query(sb.toString(), mapper, pageView.getFirstResult(), pageSize));
				} else {
					pageView.setRecords(jdbc.getJdbcOperations().query(sb.toString(), mapper));
				}
			} else {
				if (pageNow >= 0 && pageSize >= 0) {
					List<Object> ls = new ArrayList<Object>();
					if (params != null && params.length > 0) {
						for (Object o : params) {
							ls.add(o);
						}
					}
					ls.add(pageNow == 0 ? 0 : ((pageNow - 1) * pageSize));
					ls.add(pageSize);
					pageView.setRecords(jdbc.getJdbcOperations().query(sb.toString(), mapper, ls.toArray()));
				} else {
					pageView.setRecords(jdbc.getJdbcOperations().query(sb.toString(), mapper, params));
				}
			}
		} catch (DataAccessException e) {
			logger.error("", e);
		}
		return pageView;
	}

	@Override
	public PageView<T> getPageData(int pageNow, int pageSize, String whereSql, Object[] params) {
		return getPageData(pageNow, pageSize, whereSql, params, null);
	}

	@Override
	public PageView<T> getPageData(int pageNow, int pageSize, LinkedHashMap<String, String> orderby) {
		return getPageData(pageNow, pageSize, null, null, orderby);
	}

	@Override
	public PageView<T> getPageData(int pageNow, int pageSize) {
		return getPageData(pageNow, pageSize, null, null, null);
	}

	@Override
	public PageView<T> getPageData() {
		return getPageData(-1, -1, null, null, null);
	}

	public int getRecordCount(String whereSql, Object[] params) {
		StringBuffer sb = new StringBuffer("select count(1) from " + ReflectUtils.getObjectTableName(entitryClass)
				+ ((whereSql == null || whereSql.toString().equals("")) ? "" : (" where " + whereSql)));
		return jdbc.getJdbcOperations().queryForObject(sb.toString(), params, Integer.class);
	}

	@Override
	public int generateId() {
		final String tableName = ReflectUtils.getObjectTableName(entitryClass) + "_id";
		final String autoSql = "insert into " + tableName + " values ()";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(autoSql, new MapSqlParameterSource(), keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public int updatePropertys(String property, Object[] value, Serializable id) {
		StringBuffer sb = new StringBuffer("update " + ReflectUtils.getObjectTableName(entitryClass) + " set ");
		String[] propertys = property.split(",");
		for (String p : propertys) {
			sb.append(p + " = ? ,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where " + ReflectUtils.getObjectIDName(entitryClass) + "= ?");
		List<Object> params = new ArrayList<>();
		for (Object v : value) {
			params.add(v);
		}
		params.add(id);
		return jdbc.getJdbcOperations().update(sb.toString(), params.toArray());
	}

	@Override
	public int updatePropertysByProperties(String property, Object[] value, Map<String, Object> condition) {
		if (condition == null || condition.size() < 1) {
			return 0;
		}
		StringBuffer sb = new StringBuffer("update " + ReflectUtils.getObjectTableName(entitryClass) + " set ");
		String[] propertys = property.split(",");
		for (String p : propertys) {
			sb.append(p + " = ? ,");
		}
		sb.deleteCharAt(sb.length() - 1);
		List<Object> params = new ArrayList<>();
		for (Object v : value) {
			params.add(v);
		}
		// 拼接修改条件
		sb.append(" where ");
		for (String key : condition.keySet()) {
			sb.append(key + " = ? and ");
			params.add(condition.get(key));
		}
		sb.delete(sb.length() - 4, sb.length());
		return jdbc.getJdbcOperations().update(sb.toString(), params.toArray());
	}

	@Override
	public void updatePropertys(String property, Object value, Serializable id) {
		updatePropertys(property, new Object[] { value }, id);
	}

	@Override
	public void updatePropertys(Object[] property, Object[] value, Serializable id) {
		StringBuffer sb = new StringBuffer();
		for (Object b : property) {
			sb.append(b + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		updatePropertys(sb.toString(), value, id);
	}

	@Override
	public int addPropertys(Map<String, Object> map) {
		String sql = getAddProSql(map);
		return jdbc.update(sql, map);
	}

	public String getAddProSql(Map<String, Object> map) {
		String sql;
		StringBuffer sqlBuffer = new StringBuffer("insert into ");
		String tableName = ReflectUtils.getObjectTableName(entitryClass);
		sqlBuffer.append(tableName + " (");
		StringBuffer temp = new StringBuffer();
		for (Map.Entry<String, Object> m : map.entrySet()) {
			sqlBuffer.append(m.getKey() + ",");
			temp.append(":" + m.getKey() + ",");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
		temp.deleteCharAt(temp.length() - 1);
		sqlBuffer.append(") values (" + temp.toString() + ")");
		sql = sqlBuffer.toString();
		return sql;
	}

	@Override
	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize,
			String whereSql, Object[] params, LinkedHashMap<String, String> orderby) {
		StringBuffer sb = new StringBuffer(
				"select " + targetPropertys + " from " + ReflectUtils.getObjectTableName(entitryClass)
						+ ((whereSql == null || whereSql.toString().equals("")) ? "" : (" where " + whereSql)));
		if (orderby != null && orderby.size() > 0) {
			sb.append(" order by ");
			for (String k : orderby.keySet()) {
				sb.append(k + " " + orderby.get(k) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (pageNow >= 0 && pageSize >= 0) {
			sb.append(" limit ?,?");
		}
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(pageSize, pageNow);
		pageView.setTotalrecord(getRecordCount(whereSql, params));
		try {
			if (whereSql == null) {
				if (pageNow >= 0 && pageSize >= 0) {
					pageView.setRecords(
							jdbc.getJdbcOperations().queryForList(sb.toString(), pageView.getFirstResult(), pageSize));
				} else {
					pageView.setRecords(jdbc.getJdbcOperations().queryForList(sb.toString()));
				}
			} else {
				if (pageNow >= 0 && pageSize >= 0) {
					List<Object> ls = new ArrayList<Object>();
					if (params != null && params.length > 0) {
						for (Object o : params) {
							ls.add(o);
						}
					}
					ls.add(pageNow == 0 ? 0 : ((pageNow - 1) * pageSize));
					ls.add(pageSize);
					pageView.setRecords(jdbc.getJdbcOperations().queryForList(sb.toString(), ls.toArray()));
				} else {
					pageView.setRecords(jdbc.getJdbcOperations().queryForList(sb.toString(), params));
				}
			}
		} catch (DataAccessException e) {
			logger.error("", e);
		}
		return pageView;
	}

	@Override
	public PageView<Map<String, Object>> getPageDataBySQL(String sql, int pageNow, int pageSize, Object[] params) {
		StringBuffer sb = new StringBuffer(sql);
		if (pageNow >= 0 && pageSize >= 0) {
			sb.append(" limit ?,?");
		}
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(pageSize, pageNow);

		String ct = "1";
		if (sql.contains("group")) {
			ct = " distinct " + sql.substring(sql.indexOf("group") + 8, sql.length());
		}
		if (sql.contains("union all")) {
			String count1 = "select count(" + ct + ") from " + sql.substring(sql.indexOf("from") + 4,
					sql.contains("union") ? sql.indexOf("union") : sql.length());
			sql = sql.substring(sql.indexOf("all") + 3, sql.length());
			String count2 = "select count(" + ct + ") from " + sql.substring(sql.indexOf("from") + 4,
					sql.contains("order") ? sql.indexOf("order") : sql.length());
			pageView.setTotalrecord(jdbc.getJdbcOperations().queryForObject(count1, params, Long.class)
					+ jdbc.getJdbcOperations().queryForObject(count2, params, Long.class));
		} else {
			String count = "select count(" + ct + ") from " + sql.substring(sql.indexOf("from") + 4,
					sql.contains("group") ? sql.indexOf("group") : sql.length());
			if (count.contains("order")) {
				count = count.substring(0, count.indexOf("order"));
			}
			pageView.setTotalrecord(jdbc.getJdbcOperations().queryForObject(count, params, Long.class));
		}
		try {
			if (pageNow >= 0 && pageSize >= 0) {
				List<Object> ls = new ArrayList<Object>();
				if (params != null && params.length > 0) {
					for (Object o : params) {
						ls.add(o);
					}
				}
				ls.add(pageNow == 0 ? 0 : ((pageNow - 1) * pageSize));
				ls.add(pageSize);
				pageView.setRecords(jdbc.getJdbcOperations().queryForList(sb.toString(), ls.toArray()));
			} else {
				pageView.setRecords(jdbc.getJdbcOperations().queryForList(sb.toString(), params));
			}
		} catch (DataAccessException e) {
			logger.error("", e);
		}
		return pageView;
	}

	public List<Map<String, Object>> getDataBySQL(String sql, Object[] params) {
		return jdbc.getJdbcOperations().queryForList(sql, params);
	}

	@Override
	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize,
			String whereSql, Object[] params) {
		return getPageDataPropertys(targetPropertys, pageNow, pageSize, whereSql, params, null);
	}

	@Override
	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize,
			LinkedHashMap<String, String> orderby) {
		return getPageDataPropertys(targetPropertys, pageNow, pageSize, null, null, orderby);
	}

	@Override
	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize) {
		return getPageDataPropertys(targetPropertys, pageNow, pageSize, null, null, null);
	}

	@Override
	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys) {
		return getPageDataPropertys(targetPropertys, -1, -1, null, null, null);
	}

	@Override
	public List<Map<String, Object>> getDataPropertys(String targetPropertys, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby, int limit) {
		StringBuffer sb = new StringBuffer("select " + targetPropertys + " from "
				+ ReflectUtils.getObjectTableName(entitryClass) + (whereSql == null ? "" : (" where " + whereSql)));
		if (orderby != null && orderby.size() > 0) {
			sb.append(" order by ");
			for (String k : orderby.keySet()) {
				sb.append(k + " " + orderby.get(k) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (limit > 0) {
			sb.append(" limit " + limit);
		}
		List<Map<String, Object>> pageView = new ArrayList<Map<String, Object>>();
		pageView = jdbc.getJdbcOperations().queryForList(sb.toString(), params);
		return pageView;
	}

	@Override
	public List<Map<String, Object>> getDataPropertys(String targetPropertys, LinkedHashMap<String, String> orderby,
			int limit) {
		return getDataPropertys(targetPropertys, null, null, orderby, limit);
	}

	@Override
	public List<Map<String, Object>> getDataPropertys(String targetPropertys, String whereSql, Object[] params,
			int limit) {
		return getDataPropertys(targetPropertys, whereSql, params, null, limit);
	}

	@Override
	public List<Map<String, Object>> getDataPropertys(String targetPropertys) {
		return getDataPropertys(targetPropertys, null, null, null, -1);
	}

	@Override
	public List<Map<String, Object>> getDataPropertys(String targetPropertys, int limit) {
		return getDataPropertys(targetPropertys, null, null, null, limit);
	}

	@Override
	public T getSingleData() {
		return this.getSingleData(null);
	}

	@Override
	public T getSingleData(String whereSql, Object[] params) {
		return this.getSingleData(whereSql, params, null);
	}

	@Override
	public T getSingleData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby) {
		List<T> list = this.getData(whereSql, params, orderby, 1);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public T getSingleData(LinkedHashMap<String, String> orderby) {
		return this.getSingleData(null, null, orderby);
	}

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize, String whereSql, Object[] params) {
		return this.getPageData(voCls, pageNow, pageSize, whereSql, params, null);
	}

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize,
			LinkedHashMap<String, String> orderby) {
		return this.getPageData(voCls, pageNow, pageSize, null, null, null);
	}

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize) {
		return this.getPageData(voCls, pageNow, pageSize, null, null, null);
	}

	public <V> PageView<V> getPageData(Class<V> voCls) {
		return this.getPageData(voCls, -1, -1, null, null, null);
	}

	@Override
	public <V> List<V> getData(LinkedHashMap<String, String> orderby, int limit, Class<V> cls) {
		return this.getData(null, null, orderby, limit, cls);
	}

	@Override
	public <V> List<V> getData(String whereSql, Object[] params, int limit, Class<V> cls) {
		return this.getData(whereSql, params, null, limit, cls);
	}

	@Override
	public <V> List<V> getData(Class<V> cls) {
		return this.getData(null, null, null, -1, cls);
	}

	@Override
	public <V> List<V> getData(int limit, Class<V> cls) {
		return this.getData(null, null, null, limit, cls);
	}

	@Override
	public <V> List<V> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby, Class<V> cls) {
		return this.getData(whereSql, params, orderby, -1, cls);
	}

	@Override
	public <V> List<V> getData(LinkedHashMap<String, String> orderby, Class<V> cls) {
		return this.getData(null, null, orderby, -1, cls);
	}

	@Override
	public <V> List<V> getData(String whereSql, Object[] params, Class<V> cls) {
		return this.getData(whereSql, params, null, -1, cls);
	}

	@Override
	public <V> V getSingleData(Class<V> voCls, String whereSql, Object[] params) {
		return getSingleData(voCls, whereSql, params, null);
	}

	@Override
	public <V> V getSingelData(Class<V> voCls) {
		return getSingleData(voCls, null, null, null);
	}

	@Override
	public <V> V getSingleData(Class<V> voCls, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		List<V> list = this.getData(whereSql, params, orderby, 1, voCls);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public <V> V getSingleData(Class<V> voCls, LinkedHashMap<String, String> orderby) {
		return getSingleData(voCls, null, null, orderby);
	}
}
