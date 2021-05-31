package com.wxj.designPattern.factory;

/**
 * <p>@ClassName: Cat  </p>
 * <p>@Description: 猫类 </p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/5/28</p>
 */
public class Cat implements Animal {
	@Override
	public void move() {
		System.out.println("cat move on land");
	}
}
