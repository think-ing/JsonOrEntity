package com.mzw.jsonorentity.bean;

/**
 * Created by think on 2018/6/15.
 */

public class User {

    public int id;
    public String name;
    public String password;
    public Boolean aaa;

    public User() {
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password, Boolean aaa) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.aaa = aaa;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", aaa=" + aaa +
                '}';
    }
}
