package com.wxj.designPattern.factory;

/**
 * <p>@ClassName: AnimalFactoryTest  </p>
 * <p>@Description: 动物工厂测试类 </p>
 * <p>@author: wxj  </p>
 * <p>@date: 2021/5/28</p>
 */
public class AnimalFactoryTest {

	public static void main(String[] args) {
		AnimalFactory animalFactory = new AnimalFactory();

		animalFactory.getAnimal("Bird").move();

		animalFactory.getAnimal("Cat").move();
	}
}
