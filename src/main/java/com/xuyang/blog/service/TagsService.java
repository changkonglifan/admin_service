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
}
