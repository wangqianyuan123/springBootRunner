package com.victor.utils;

import java.util.List;

/**
 * Created by wqy on 2019/2/18.
 */
public class UserInfo2 {
    private String sum;
    private String faterName;
    private List<Detail> detailList;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getFaterName() {
        return faterName;
    }

    public void setFaterName(String faterName) {
        this.faterName = faterName;
    }

    public UserInfo2(String sum, String faterName) {
        this.sum = sum;
        this.faterName = faterName;
    }

    public UserInfo2() {
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }
}
