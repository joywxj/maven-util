package com.wxj.designPattern.factory;

/**
 * <p>@ClassName: Fish  </p>
 * <p>@Description: 鱼类</p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/5/28</p>
 */
public class Fish implements Animal {
	@Override
	public void move() {
		System.out.println("Fish move in water");
	}
}
