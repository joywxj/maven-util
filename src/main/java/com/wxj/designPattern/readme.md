# 设计模式

## 概述

    设计模式代表了最佳实践，通常被有经验的程序员所使用，设计模式是程序员工在工作中不断摸索出来的很很好的解决问题的方式。且这些方式被应用于
    很多地方，具有普遍性，能解决很多地方的问题，

### 工厂模式

#### 描述

        工厂模式是程序员用得比较多的设计模式之一，它属于创建模式，它提供了一种最佳的创建对象模式，

#### 原理

        通过创建一个基类，这个基类可以是interface,在基类中去创建一些方法 以后具有相同功能的类都去实现这个基类，只是大家各自的实现方式不一样而
    而已，例如，我们定义一个动物Animal.java基类,所有的动物都会有移动的动物，那么我们可以在Animal类中定义一个move的方法,在动物中有猫，人
    ，蛇，鱼，鸟等动物，我们可以分为创建一个Fish类，Bird类，People类，Cat类，Snake类。但不同的是它的所运动的方式不一样，用我们专业 的话
    说就是实现方式不一样，fish的运动方式是在水里游，Bird的运行方式是在天上飞，Snake的运行方式是在地上爬行，people和cat呢，则是在地上行走
    当我们需要用运动的方法时，我们只需要告诉设计模式是以什么类去运行就可以了，它就会让自己去创建对应对象并执行对应的运动方法


    其实我更愿意把它理解为多态，一个方法多个实现