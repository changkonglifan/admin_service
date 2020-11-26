package com.xuyang.blog.entity;

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/11/26 17:49
 * @Description:
 */
public class TagsTree {
    private Tags tag;
    private List<Tags> children;

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public List<Tags> getChildren() {
        return children;
    }

    public void setChildren(List<Tags> children) {
        this.children = children;
    }
}
