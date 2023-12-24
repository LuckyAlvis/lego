package com.shenzhen.dai.req;

public class LegoPageReq {
    int current = 0;
    int size = 10;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
