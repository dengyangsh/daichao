package com.dbdai.daichao.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hebao.bixia.common.page.PageView;

/**
 * @param <T>
 *            具体的对象类型
 *            <p>
 *            五个通用接口实现的要求 约定是字段名和数据库字段名一致
 *            <p>
 *            原本是想做灵活的可配置方案，但是觉得那样更加容易增加程序的复杂度，还不如统一遵循约定优于配置的方案。
 * @author 最基础的增删改查接口
 */
public interface BaseDAO<T> {
	/**
	 * 增加的接口
	 *
	 * @param o
	 * @return
	 */
	public int add(T o);

	/**
	 * 批量增加
	 *
	 * @param os
	 * @return
	 */
	public int addBatch(List<T> os);

	/**
	 * 增加的接口，但是ID是自增长的
	 *
	 * @param o
	 * @return 返回生成的主键ID
	 */
	public int addWithoutId(T o);

	/**
	 * 删除的接口
	 *
	 * @param id
	 */
	public void delete(Serializable... ids);

	/**
	 * 修改
	 *
	 * @param o
	 */
	public int update(T o);

	/**
	 * 修改有值的属性
	 *
	 * @param o
	 */
	public void updatePropertes(T o);

	/**
	 * 指定修改条件 修改不为空的属性 [单个条件]
	 *
	 * @param o
	 * @param column
	 *            修改条件:属性名（字段名）
	 * @return Integer
	 */
	Integer updatePropertesByColumn(T o, String column);

	/**
	 * 指定修改条件 修改不为空的属性 【符合条件】
	 *
	 * @param o
	 * @param colList
	 *            修改条件:属性名（字段名）
	 * @return Integer
	 */
	Integer updatePropertesByColumns(T o, List<String> colList);

	/**
	 * 查询
	 *
	 * @param id
	 *            主键ID
	 * @return 查询的对象
	 */
	public T find(Serializable id);

	/**
	 * 行锁
	 *
	 * @param id
	 * @return
	 */
	public T lock(Serializable id);

	/**
	 * 根据id查询某些指定的属性(多个属性用逗号隔开)
	 *
	 * @param id
	 * @param targetPropertys
	 * @return
	 */
	public Map<String, Object> findPropertys(String targetPropertys, Serializable id);

	/**
	 * 指定字段增加的接口
	 *
	 * @param map
	 * @return 影响的条数
	 */
	public int addPropertys(Map<String, Object> map);

	/**
	 * 分页接口
	 *
	 * @param pageNow
	 *            当前页
	 * @param pageSize
	 *            每页显示条数
	 * @param whereSql
	 *            条件语句
	 * @param params
	 *            条件变量值
	 * @param orderby
	 *            排序变量和排序方式 同时下面复写了几个方法
	 * @return
	 */
	public PageView<T> getPageData(int pageNow, int pageSize, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 查询指定字段的通用接口分布接口
	 *
	 * @param targetPropertys
	 * @param pageNow
	 * @param pageSize
	 * @param whereSql
	 * @param params
	 * @param orderby
	 * @return
	 */
	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize,
			String whereSql, Object[] params, LinkedHashMap<String, String> orderby);

	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize,
			String whereSql, Object[] params);

	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize,
			LinkedHashMap<String, String> orderby);

	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys, int pageNow, int pageSize);

	public PageView<Map<String, Object>> getPageDataPropertys(String targetPropertys);

	/**
	 * 根据SQL语句返回分页数据
	 *
	 * @param sql
	 * @param pageNow
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public PageView<Map<String, Object>> getPageDataBySQL(String sql, int pageNow, int pageSize, Object[] params);

	/**
	 * 根据SQL获取数据
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getDataBySQL(String sql, Object[] params);

	/**
	 * 指定字段的无分页接口
	 *
	 * @param targetPropertys
	 * @param whereSql
	 * @param params
	 * @param orderby
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getDataPropertys(String targetPropertys, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby, int limit);

	public List<Map<String, Object>> getDataPropertys(String targetPropertys, LinkedHashMap<String, String> orderby,
			int limit);

	public List<Map<String, Object>> getDataPropertys(String targetPropertys, String whereSql, Object[] params,
			int limit);

	public List<Map<String, Object>> getDataPropertys(String targetPropertys);

	public List<Map<String, Object>> getDataPropertys(String targetPropertys, int limit);

	/**
	 * 无分页获取列表
	 *
	 * @param whereSql
	 * @param params
	 * @param orderby
	 * @return
	 */
	public List<T> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby, int limit);

	public List<T> getData(LinkedHashMap<String, String> orderby, int limit);

	public List<T> getData(String whereSql, Object[] params, int limit);

	public List<T> getData();

	public List<T> getData(int limit);

	public List<T> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby);

	public List<T> getData(LinkedHashMap<String, String> orderby);

	public List<T> getData(String whereSql, Object[] params);

	/**
	 * 无分页获取列表
	 *
	 * @param whereSql
	 * @param params
	 * @param orderby
	 * @return
	 */

	public T getSingleData(String whereSql, Object[] params);

	public T getSingleData();

	public T getSingleData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby);

	public T getSingleData(LinkedHashMap<String, String> orderby);

	public PageView<T> getPageData(int pageNow, int pageSize, String whereSql, Object[] params);

	public PageView<T> getPageData(int pageNow, int pageSize, LinkedHashMap<String, String> orderby);

	public PageView<T> getPageData(int pageNow, int pageSize);

	public PageView<T> getPageData();

	/**
	 * 不使用自增长时的 ID生成策略
	 *
	 * @return
	 */
	public int generateId();

	/**
	 * 根据id 更新字段的值
	 *
	 * @param property
	 *            要更新的字段，字段之间用逗号隔开
	 * @param value
	 *            要设置的值
	 * @param id
	 *            主键 ID
	 */
	public int updatePropertys(String property, Object[] value, Serializable id);

	/**
	 * 增加单个字段更新接口
	 *
	 * @param property
	 * @param value
	 * @param id
	 */
	public void updatePropertys(String property, Object value, Serializable id);

	/**
	 * 增加多个字段更新接口
	 *
	 * @param property
	 * @param value
	 * @param id
	 */
	public void updatePropertys(Object[] property, Object[] value, Serializable id);

	/**
	 * 指定更新条件 多个字段更新接口
	 * <p>
	 * 当condition 为null 时不会修改，修改返回 0。
	 *
	 * @param property
	 * @param value
	 * @param condition
	 *            key: 列 value:值
	 * @return
	 */
	int updatePropertysByProperties(String property, Object[] value, Map<String, Object> condition);

	/**
	 * 获取条件数据条数
	 *
	 * @param whereSql
	 * @param params
	 * @return
	 */
	public int getRecordCount(String whereSql, Object[] params);

	/**
	 * 查询
	 *
	 * @param id
	 *            主键ID
	 * @param cls
	 *            要查询的类
	 * @return 查询的对象
	 */
	public <V> V find(Serializable id, Class<V> cls);

	/**
	 * 查询
	 *
	 * @param id
	 *            主键ID
	 * @param cls
	 *            要查询的类
	 * @return 查询的对象
	 */
	public <V> V lock(Serializable id, Class<V> cls);

	public <V> List<V> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby, int limit,
			Class<V> cls);

	public <V> List<V> getData(LinkedHashMap<String, String> orderby, int limit, Class<V> cls);

	public <V> List<V> getData(String whereSql, Object[] params, int limit, Class<V> cls);

	public <V> List<V> getData(Class<V> cls);

	public <V> List<V> getData(int limit, Class<V> cls);

	public <V> List<V> getData(String whereSql, Object[] params, LinkedHashMap<String, String> orderby, Class<V> cls);

	public <V> List<V> getData(LinkedHashMap<String, String> orderby, Class<V> cls);

	public <V> List<V> getData(String whereSql, Object[] params, Class<V> cls);

	public <V> V getSingleData(Class<V> voCls, String whereSql, Object[] params);

	public <V> V getSingelData(Class<V> voCls);

	public <V> V getSingleData(Class<V> voCls, String whereSql, Object[] params, LinkedHashMap<String, String> orderby);

	public <V> V getSingleData(Class<V> voCls, LinkedHashMap<String, String> orderby);

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize, String whereSql, Object[] params,
			LinkedHashMap<String, String> orderby);

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize, String whereSql, Object[] params);

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize,
			LinkedHashMap<String, String> orderby);

	public <V> PageView<V> getPageData(Class<V> voCls, int pageNow, int pageSize);

	public <V> PageView<V> getPageData(Class<V> voCls);
}
