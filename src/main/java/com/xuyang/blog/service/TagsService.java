package com.xuyang.blog.service;

import com.xuyang.blog.dao.TagsMapper;
import com.xuyang.blog.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/11/26 17:02
 * @Description:
 */
@Service
@Transactional
public class TagsService {
    @Autowired
    private TagsMapper tagsMapper;


    public List<Tags> getAllTags(String name){return tagsMapper.getAllTags(name);}

    public Integer addTag(String pid, String name,String description){return tagsMapper.addTag(pid, name, description);}

    public Integer updateTag(String id, String name,String description,String count, String sort, String pid){return tagsMapper.updateTag(id, name, description, count, sort, pid);}

    public Tags getTagsByName(String name){return tagsMapper.getTagByName(name);}

    public Tags getTagsByName(String name, String id){return tagsMapper.getTagByName(name, id);}

    public Integer deleteTag(String id){return tagsMapper.deleteTag(id);}
}
