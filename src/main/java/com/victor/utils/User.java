package com.victor.utils;

import java.util.List;

/**
 * Created by wqy on 2019/2/18.
 */
public class User {
    private String name;
    private String age;
    private String sex;
    private List<UserInfo> infos;
    private List<UserInfo2> info2s;

    public List<UserInfo2> getInfo2s() {
        return info2s;
    }

    public void setInfo2s(List<UserInfo2> info2s) {
        this.info2s = info2s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<UserInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<UserInfo> infos) {
        this.infos = infos;
    }

    public User() {
    }

    public User(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
