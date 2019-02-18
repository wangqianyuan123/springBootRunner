package com.victor.utils;

/**
 * Created by wqy on 2019/2/18.
 */
public class Detail {

    private Integer  sun;
    private String flag;

    public Detail(Integer sun, String flag) {
        this.sun = sun;
        this.flag = flag;

    }

    public Integer getSun() {
        return sun;
    }

    public void setSun(Integer sun) {
        this.sun = sun;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
