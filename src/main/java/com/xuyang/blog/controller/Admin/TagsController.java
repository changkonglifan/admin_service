package com.xuyang.blog.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.blog.entity.Tags;
import com.xuyang.blog.entity.TagsTree;
import com.xuyang.blog.service.TagsService;
import com.xuyang.blog.service.TokenService;
import com.xuyang.blog.utils.MessageOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/11/26 17:03
 * @Description:
 */
@RestController
@Api(tags = "标签管理")
@RequestMapping(value = "/admin/tags")
@CrossOrigin
public class TagsController {
    @Autowired
    public static Logger logger = LoggerFactory.getLogger(TagsController.class);

    @Autowired
    private TagsService tagsService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "标签操作", notes = "标签操作 ")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "name", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllTags(
            String name,
            String token
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            List<Tags> list = tagsService.getAllTags(name);
            JSONObject js = new JSONObject();
            js.put("treeList", getTreeData(list));
            return  MessageOut.successful(js);
        }catch (Exception e) {
            return MessageOut.failed(-1, e.getMessage());
        }
    }

    /**
     * 获取树形结构
     * @param list
     * @return
     */
    private List<TagsTree> getTreeData(List<Tags> list) {
        ArrayList<TagsTree> result = new ArrayList<TagsTree>();
        for(Tags t : list){
            if("0".equals(t.getPid())){
                TagsTree tt = new TagsTree();
                tt.setTag(t);
                tt.setChildren(getChildren(list, t));
                result.add(tt);
            }
        }
        return  result;
    }
    /**
     * 获取树形结构
     * @param list
     * @return
     */
    private List<Tags> getChildren(List<Tags> list, Tags tag) {
        ArrayList<Tags> result = new ArrayList<Tags>();
        for(Tags t : list){
            if(t.getPid() == tag.getId()){
                result.add(t);
            }
        }
        return result;
    }
}
