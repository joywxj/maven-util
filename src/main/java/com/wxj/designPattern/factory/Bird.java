package com.wxj.designPattern.factory;

/**
 * <p>@ClassName: Bird  </p>
 * <p>@Description: 鸟类 </p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/5/28</p>
 */
public class Bird implements Animal {

	@Override
	public void move() {
		System.out.println("Bird move in air");
	}
}
