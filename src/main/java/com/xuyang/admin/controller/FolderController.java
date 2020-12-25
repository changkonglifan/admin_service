package com.xuyang.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: XuYang
 * @Date: 2020/12/24 14:11
 * @Description:
 */
@CrossOrigin
@RestController
@Api(tags = "目录")
@RequestMapping("/folder")
@EnableSwagger2
public class FolderController {
    @Autowired
    private FolderService folderService;

    @ApiOperation(value = "获取目录列表", notes = "获取目录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pid", value = "父id", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @RequestMapping(value = "/getFoldersByPId", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getNotes(
            String pid,
            String token
    ){
        return folderService.getFoldersByPid(pid, token);
    }
    @ApiOperation(value = "获取笔记列表", notes = "获取笔记列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "title", value = "名称", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "pid", value = "父节点", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    JSONObject addNote(
            String token,
            String title,
            String pid
    ){
        return folderService.addFolder(title, pid, token);
    }

}
