package com.shenzhen.dai.creational;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description: 测试单例模式
 * @author: daiyifan
 * @create: 2024-03-04 01:06
 */
public class SingletonTest {
    @Test
    public void testSingleton() {
        System.out.println("EagerSingleton.getInstance() == EagerSingleton.getInstance() = " + (EagerSingleton.getInstance() == EagerSingleton.getInstance()));
        System.out.println("LazySingleton.getInstance() == LazySingleton.getInstance() = " + (LazySingleton.getInstance() == LazySingleton.getInstance()));
        System.out.println("InnerSingleton.getInstance() == InnerSingleton.getInstance() = " + (InnerSingleton.getInstance() == InnerSingleton.getInstance()));
        System.out.println("NormalEnumSingleton.SingletonEnum.EnumSingleton.getInstance() == NormalEnumSingleton.SingletonEnum.EnumSingleton.getInstance() = " + (NormalEnumSingleton.SingletonEnum.EnumSingleton.getInstance() == NormalEnumSingleton.SingletonEnum.EnumSingleton.getInstance()));
    }

    @Test
    public void testFailFast() {
        Map<String, String> empName = new HashMap<String, String>();
        empName.put("name", "dai");
        empName.put("sex", "male");
        empName.put("age", "18");
        for (String s : empName.keySet()) {
            System.out.println(empName.get(s));
            empName.put("work", "java");
        }
    }

    @Test
    public void testFailSafe() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 7, 9, 11});
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
            if (next == 7) {
                list.add(15); //在fail-safe模式下，这里是执行不到的，不会打印
            }
        }
    }

    @Test
    public void testReflect() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Singleton> clazz = Singleton.class;
        Constructor<Singleton> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        boolean flag = Singleton.getInstance() == constructor.newInstance();
        System.out.println("flag = " + flag);
    }

    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        // 获取单例并序列化
        Singleton singleton = Singleton.getInstance();
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/ivan/code/lego/lego-knowledge/lego-knowledge-design-pattern/src/test/java/com/shenzhen/dai/creational/singleton.txt");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(singleton);
        // 将实例反序列化出来
        FileInputStream fileInputStream = new FileInputStream("/Users/ivan/code/lego/lego-knowledge/lego-knowledge-design-pattern/src/test/java/com/shenzhen/dai/creational/singleton.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object o = objectInputStream.readObject();
        System.out.println("是不是同一个实例" + (o == singleton));
    }
}
