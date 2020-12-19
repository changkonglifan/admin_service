package com.xuyang.admin.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.service.TagsService;
import com.xuyang.admin.service.TokenService;
import com.xuyang.admin.utils.MessageOut;
import com.xuyang.admin.entity.Tags;
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

    @ApiOperation(value = "新增标签", notes = "新增标签")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "pid", value = "父节点", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "form", name = "name", value = "名称", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "form", name = "description", value = "描述", dataType = "String",required = false),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true)
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addTag(
            String pid,
            String name,
            String description,
            String token
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            Tags tag = tagsService.getTagsByName(name, null);
            if(tag != null){
                return MessageOut.failed(-2, "标签名已存在");
            }
            Integer rs = tagsService.addTag(pid, name, description);
            if(rs > 0){
                return MessageOut.successful("添加成功");
            }else {
                return MessageOut.failed(-1, "添加失败");
            }
        }catch (Exception e){
            return MessageOut.failed(-1,e.getMessage());
        }
    }

    @ApiOperation(value = "修改标签", notes = "修改标签")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "id", value = "id", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "form", name = "name", value = "名称", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "form", name = "description", value = "描述", dataType = "String",required = false),
            @ApiImplicitParam(paramType = "form", name = "count", value = "统计", dataType = "String",required = false),
            @ApiImplicitParam(paramType = "form", name = "sort", value = "排序", dataType = "String",required = false),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true)
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateTag(
            String id,
            String name,
            String description,
            String count,
            String sort,
            String pid,
            String token
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            Tags tag = tagsService.getTagsByName(name, id);
            if(tag != null){
                return MessageOut.failed(-2, "标签名已存在");
            }
            Integer rs = tagsService.updateTag(id,name,description,count,sort, pid);
            if(rs > 0){
                return MessageOut.successful("修改成功");
            }else {
                return MessageOut.failed(-1, "修改失败");
            }
        }catch (Exception e){
            return MessageOut.failed(-1,e.getMessage());
        }
    }

    @ApiOperation(value = "删除标签", notes = "删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true)
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject updateTag(
            String id,
            String token
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            Integer rs = tagsService.deleteTag(id);
            if(rs > 0){
                return MessageOut.successful("删除成功");
            }else {
                return MessageOut.failed(-1, "删除失败");
            }
        }catch (Exception e){
            return MessageOut.failed(-1,e.getMessage());
        }
    }


    @ApiOperation(value = "获取所有标签", notes = "获取所有标签 ")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "name", dataType = "String", required = false),
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
            if(tokenJS == null || token == null){
                // token失效
                return MessageOut.sessionOut();
            }
            List<Tags> list = tagsService.getAllTags(name);
            JSONObject js = new JSONObject();
            js.put("treeList", getTreeData(list, "0"));
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
    private List<Tags> getTreeData(List<Tags> list, String pid) {
        List<Tags> result = new ArrayList<>();
        for(Tags t : list){
            if(pid.equals(t.getPid())){
                t.setChildren(getTreeData(list, t.getId()));
               result.add(t);
            }
        }
        return  result;
    }
}
