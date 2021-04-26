package com.wxj.test;

import java.lang.reflect.*;
import java.util.*;

/**
 * @ClassName: ObjectUtils
 * @Description: TODO
 * @author: wxj
 * @date: 2019年2月1日
 * @Tel:18772118541
 * @email:18772118541@163.com
 */
public class ObjectUtils {
	/**
	 * ------yuanzy 获取当前对象集合的某属性组成的集合
	 * 
	 * @param filedName
	 * @param objectList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getFieldValueListByName(String filedName, List objectList) {
		List resultList = new ArrayList();
		for (Object object : objectList) {
			resultList.add(getFieldValueByName(filedName, object));
		}
		return resultList;
	}

	/**
	 * ---yuanzy 根据属性名获取属性值
	 * 
	 * @param fieldName
	 * @param o
	 * @return
	 */
	public static Object getFieldValueByName(String fieldName, Object object) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = object.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(object, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取属性名数组
	 */
	public static String[] getFiledName(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * 获取属性名数组
	 */
	@SuppressWarnings("rawtypes")
	public static String[] getFiledName(Class clzss) {
		Field[] fields = clzss.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getFiledsInfo(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		List list = new ArrayList();
		Map infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), object));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 */
	public static Object[] getFiledValues(Object object) {
		String[] fieldNames = getFiledName(object);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			value[i] = getFieldValueByName(fieldNames[i], object);
		}
		return value;
	}

	public static void invokeMethod(Object obj, String method, Object arg) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 获取方法
		String methodName = "set" + method.substring(0, 1).toUpperCase() + method.substring(1);
		Method m = obj.getClass().getDeclaredMethod(methodName, arg.getClass());
		// 调用方法
		m.invoke(obj, arg);
	}

}
