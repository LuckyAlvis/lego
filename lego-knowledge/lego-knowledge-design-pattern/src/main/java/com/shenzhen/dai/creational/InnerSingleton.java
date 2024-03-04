package com.shenzhen.dai.creational;

/**
 * @description: 静态内部类实现单例模式
 * @author: daiyifan
 * @create: 2024-03-04 02:34
 */
public class InnerSingleton {
    private InnerSingleton() {

    }

    public static InnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 定义内部类来持有实例
     * 特性：类加载的时机 类会在第一次主动使用的时候被加载
     * 实例会在内部类加载（调用getInstance()的时候）会创建
     */

    private static class SingletonHolder {
        private static final InnerSingleton INSTANCE = new InnerSingleton();
    }
}
