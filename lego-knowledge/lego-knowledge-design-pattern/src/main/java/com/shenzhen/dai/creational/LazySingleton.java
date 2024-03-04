package com.shenzhen.dai.creational;

/**
 * @description: 懒汉式加载
 * @author: daiyifan
 * @create: 2024-02-29 11:50
 */
public class LazySingleton {

    /**
     * 创建单例对象本身的复杂度
     * 很多的单例对象创建过程
     * 可能极其复杂
     * 或者可能需要占用很大内存，比如缓存对象
     * 很多单例对象可能创建完 成后很少使用，这时可以考虑懒加载
     * 面试不怎么问这个，因为比较简单，更多要学习懒汉式
     */

    private static LazySingleton instance = new LazySingleton();

    // 2. 为了避免别人随意创建，我们需要私有化构造器
    private LazySingleton() {

    }

    // 3. 暴露一个方法，用来获取实例
    public static LazySingleton getInstance() {
        if (null == instance) {
            instance = new LazySingleton();
        }
        return instance;
    }

}
