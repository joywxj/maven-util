package com.wxj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>@ClassName: Test  </p>
 * <p>@Description: </p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/4/29</p>
 */
public class Test {

	public static void main(String[] args) {
		Set<String> es = new HashSet<String>();
		es.add("1");
		HashMap<Object, Object> map = new HashMap<>();
		map.put(null, null);
		for (String ss : es) {
			System.out.println(ss);
		}

	}
}
