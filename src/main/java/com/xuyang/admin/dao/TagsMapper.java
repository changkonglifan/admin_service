package com.xuyang.admin.dao;

import com.xuyang.admin.entity.Tags;
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

    Integer addTag(String pid, String name, String description);

    Integer updateTag(String id,String name, String description, String count, String sort, String pid);

    Tags getTagByName(String name, String id);

    Tags getTagByName(String name);

    Integer deleteTag(String id);
}
