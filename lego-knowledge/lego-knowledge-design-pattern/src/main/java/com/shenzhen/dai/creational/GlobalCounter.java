package com.shenzhen.dai.creational;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 枚举实现全局计数器单例
 * @author: daiyifan
 * @create: 2024-03-05 00:38
 */
public enum GlobalCounter {

    INSTANCE;

    private AtomicLong atomicLong = new AtomicLong(0);

    public Long getNumber() {
        return atomicLong.incrementAndGet();
    }
}
