package com.xuyang.admin.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/11/26 16:50
 * @Description:
 */
public class Tags {
    private String id;
    private String pid;
    private String name;
    private String parentName;
    private String count;
    private String icon;
    private String sort;
    private String description;


    private String createTime;
    private String updateTime;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    private  List<Tags> children = new ArrayList<Tags>();

    public List<Tags> getChildren() {
        return children;
    }

    public void setChildren(List<Tags> children) {
        this.children = children;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
