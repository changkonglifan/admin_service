package com.xuyang.admin.entity;

/**
 * @Author: XuYang
 * @Date: 2020/12/24 14:23
 * @Description:
 */
public class Token {
    private String uuid;
    private  String userName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
