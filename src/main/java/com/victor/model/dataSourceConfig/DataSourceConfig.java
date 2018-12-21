package com.victor.model.dataSourceConfig;

import java.io.Serializable;
import java.util.List;

/**
 * 动态数据源Model
 */
public class DataSourceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private int id;

    /**
     * 应用服务名
     */
    private String appCode;

    /**
     * 应用服务名
     */
    private String appName;

    /**
     * 备注
     */
    private String note;

    /**
     * 数据源配置
     */
    private List<DataSourceInfo> dataSourceInfos;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<DataSourceInfo> getDataSourceInfos() {
        return dataSourceInfos;
    }

    public void setDataSourceInfos(List<DataSourceInfo> dataSourceInfos) {
        this.dataSourceInfos = dataSourceInfos;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
