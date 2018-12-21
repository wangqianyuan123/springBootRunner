package com.victor.model.dataSourceConfig;

import java.io.Serializable;

/**
 * 数据源详细配置
 */
public class DataSourceInfo implements Serializable {

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
     * 数据库code
     */
    private String dbCode;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库连接URL
     */
    private String dbUrl;

    /**
     * 数据库登陆用户名
     */
    private String dbUserName;

    /**
     * 数据库登陆密码
     */
    private String dbPassWord;

    /**
     * 数据库连接驱动
     */
    private String driverClassName;

    /**
     * 备注
     */
    private String note;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 最大连接数
     */
    private int dbMaxProcess;

    /**
     * 最小连接数
     */
    private int dbMinProcess;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDbCode() {
        return dbCode;
    }

    public void setDbCode(String dbCode) {
        this.dbCode = dbCode;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDbMaxProcess() {
        return dbMaxProcess;
    }

    public void setDbMaxProcess(int dbMaxProcess) {
        this.dbMaxProcess = dbMaxProcess;
    }

    public int getDbMinProcess() {
        return dbMinProcess;
    }

    public void setDbMinProcess(int dbMinProcess) {
        this.dbMinProcess = dbMinProcess;
    }
}
