package com.xuyang.blog.entity;

public class Account {

    private String uuid;
    private String username;

    private String password;
    private String createTime;
    private String modifyTime;
    private String isDel;
    private String isDisable;
    private String errCount;
    private String usedPwd;
    private String lastUpdateTime;
    private String lockTime;

    public  static  final String IS_USER_DELETE = "1";

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public String getErrCount() {
        return errCount;
    }

    public void setErrCount(String errCount) {
        this.errCount = errCount;
    }

    public String getUsedPwd() {
        return usedPwd;
    }

    public void setUsedPwd(String usedPwd) {
        this.usedPwd = usedPwd;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }
}
