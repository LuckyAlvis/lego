package com.shenzhen.dai.creational;

public class NormalEnumSingleton {
    private NormalEnumSingleton() {
    }

    public enum SingletonEnum {
        EnumSingleton;
        private NormalEnumSingleton instance = null;

        SingletonEnum() {
            instance = new NormalEnumSingleton();
        }

        public NormalEnumSingleton getInstance() {
            return this.instance;
        }
    }
}
