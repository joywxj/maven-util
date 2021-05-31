package com.wxj.designPattern.factory;

/**
 * <p>@ClassName: AnimalFactory  </p>
 * <p>@Description: 动物工厂 </p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/5/28</p>
 */
public class AnimalFactory {
	public Animal getAnimal(String animal) {
		if ("Bird".equals(animal)) {
			return new Bird();
		} else if ("Cat".equals(animal)) {
			return new Cat();
		}
		return null;
	}
}
