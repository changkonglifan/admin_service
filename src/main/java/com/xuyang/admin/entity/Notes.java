package com.xuyang.admin.entity;
/**
 * @Author: XuYang
 * @Date: 2020/12/12 23:25
 * @Description:
 */
public class Notes {
    private String uuid;
    private String title;
    private String userId;
    private String folderUuid;
    private Integer isDel;
    private Integer isCollection;
    private String detail;
    private String createTime;
    private String updateTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFolderUuid() {
        return folderUuid;
    }

    public void setFolderUuid(String folderUuid) {
        this.folderUuid = folderUuid;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection) {
        this.isCollection = isCollection;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
