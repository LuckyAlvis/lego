package com.shenzhen.dai.thread;

public class ThreadCreate {
    public static void main(String[] args) {
        ThreadSubClass thread = new ThreadSubClass();
        thread.start();
        Tr(ThreadSubClass.class);
        System.out.println(java.lang.String.format("这里是 %s 线程", Thread.currentThread().getName()));
    }

    static class ThreadSubClass extends Thread {
        @Override
        public void run() {
            System.out.println(java.lang.String.format("hi, 这里是由%s创建出的 %s 线程", this.getClass().getSimpleName(), Thread.currentThread().getName()));
        }
    }

    public static void Tr(Class<? extends Thread> c){
        try {
            c.newInstance().start();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
