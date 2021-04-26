package com.wxj.test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Map工具类
 */
public class CollectionUtils {

	/**
	 * @param obj 任意对象
	 * @param map 传进来的对象
	 * @return
	 * @Title: entityToMap
	 * @Description: TODO 把任意对象转换成map
	 * @date:2018-12-28 15:34
	 */
	public static Map<String, Object> entityToMap(Object obj, Map<String, Object> map) {
		if (obj == null) {
			return map;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String key = field.getName();
			try {
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value != null) {
					map.put(key, value.toString());
				}
				field.setAccessible(accessible);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * @param map
	 * @return
	 * @Title: list  Convert the key of the map to a list
	 * @date:2019-03-15 11:54
	 */
	public static List<Object> convertKeyToList(Map<Object, Object> map) {
		List<Object> list = new ArrayList<Object>();
		Set<Object> keySet = map.keySet();
		Iterator<Object> it = keySet.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	/**
	 * @param map
	 * @return
	 * @Title: convertValueToList
	 * @Description: TODO Convert the value of the map to a List;
	 * @date:2019-03-15 12:00
	 */
	public static List<Object> convertValueToList(Map<Object, Object> map) {
		List<Object> list = new ArrayList<Object>();
		Set<Object> keySet = map.keySet();
		Iterator<Object> it = keySet.iterator();
		while (it.hasNext()) {
			list.add(map.get(it.next()));
		}
		return list;
	}
}
