package com.dbdai.daichao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hebao.bixia.common.util.Table;

/**
 * 反射相关的类方法
 * 
 * @author whitemoon
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReflectUtils {
	public static Map<String, String> tableNames = new HashMap<String, String>();

	/**
	 * 获取父类发型的真实class
	 * 
	 * @param clazz
	 *            当前类
	 * @param index
	 *            第几个泛型参数class
	 * @return
	 */

	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type type = clazz.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		if (index < 0 || index > params.length) {
			throw new RuntimeException("你输入的参数" + (index < 0 ? "小于o" : "大于参数总长"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 得到某个实体类的注解名字，如果没注解，名字和实体类名一致
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getObjectTableName(Class clazz) {
		String name = tableNames.get(clazz.getName());
		if (name == null) {
			Table table = (Table) clazz.getAnnotation(Table.class);
			name = table == null ? clazz.getSimpleName().toLowerCase() : table.name();
			tableNames.put(clazz.getName(), name);
		}
		return name;
	}

	/**
	 * 得到某个实体类的注解名字的ID值，如果没注解，就是
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getObjectIDName(Class clazz) {
		Table table = (Table) clazz.getAnnotation(Table.class);
		return table == null ? "id" : table.id();
	}
	
	/**
	 * 根据属性名获取属性值
	 * @param clazz
	 * @param prop
	 * @return
	 */
	public static Object getValueByProp(Class clazz,String prop) {
		try {
			Field f = clazz.getDeclaredField(prop);
			f.setAccessible(true);
			return f.get(clazz);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 得到某个实体类的所有private名字
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<String> getPropertyName(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		ArrayList<String> cs = new ArrayList<String>();
		for (Field f : fields) {
			if (f.getModifiers() == Modifier.PRIVATE) {
				cs.add(f.getName());
			}
		}
		return cs;
	}

	/**
	 * 得到某个实体类的所有private名字 去除主键
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<String> getPropertyNameNoID(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String id = getObjectIDName(clazz);
		ArrayList<String> cs = new ArrayList<String>();
		for (Field f : fields) {
			if (f.getModifiers() == Modifier.PRIVATE && !f.getName().equals(id)) {
				cs.add(f.getName());
			}
		}
		return cs;
	}

	public static void main(String[] args) {
	}
}
