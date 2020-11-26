package com.xuyang.blog.dao;

import com.xuyang.blog.entity.Tags;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/11/26 16:55
 * @Description:
 */
@Mapper
public interface TagsMapper {
    List<Tags> getAllTags(String name);
}
