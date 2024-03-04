package com.shenzhen.dai.creational;

import java.io.Serializable;

/**
 * @description: 防止反射入侵
 * @author: daiyifan
 * @create: 2024-03-05 01:11
 */
public class Singleton implements Serializable {
    private volatile static Singleton singleton;

    private Singleton() {
        if (singleton != null) {
            throw new RuntimeException("实例：【" + this.getClass().getName() + "】已经存在，该实例只允许实例化一次");
        }
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public Object readResolve() {
        return singleton;
    }
}
