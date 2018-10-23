package org.seckill.entity;

import java.io.Serializable;

public class RedisTest{
    private int id;

    private String name;

    public RedisTest(){}

    public RedisTest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "RedisTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

