package com.shenzhen.dai.creational;

/**
 * @description: Double Check Lock 双检锁实现单例模式
 * @author: daiyifan
 * @create: 2024-02-29 11:50
 */
public class DclSingleton {

    /**
     * 1. 持有一个jvm全局唯一的实例
     * 2. 创建对象，不是一个原子性操作，即使使用了双重检查锁，也可能在创建过程中产生半初始化状态
     * 3. volatile 1 保证内存可见 2 保证有序性
     * 事实上，jdk1.9以上，不加volatile也可以，JVM内部会处理有序性
     */

    private static volatile DclSingleton instance = new DclSingleton();

    // 2. 为了避免别人随意创建，我们需要私有化构造器
    private DclSingleton() {

    }

    // 3. 暴露一个方法，用来获取实例
    // 第一次创建需要双检锁，一旦创建好，就不再需要上锁。
    public static DclSingleton getInstance() {
        if (null == instance) {
            synchronized (DclSingleton.class) {
                if (null == instance) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

}
