package com.xuyang.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.service.NotesService;
import com.xuyang.admin.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin
@RestController
@Api(tags = "笔记")
@RequestMapping("/notes")
@EnableSwagger2
public class NotesController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private NotesService notesService;

    @ApiOperation(value = "添加", notes = "添加笔记")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "title", value = "标题", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "detail", value = "详情", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    JSONObject addNotes(
            String title,
            String detail,
            String token
    ){
        return notesService.addNotes(title, detail, token);
    }


    @ApiOperation(value = "获取笔记列表", notes = "获取笔记列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uuid", value = "uuid", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getNotesDetail(
            String uuid,
            String token
    ){
        return notesService.getDetail(uuid, token);
    }

    @ApiOperation(value = "获取笔记列表", notes = "获取笔记列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页数", dataType = "Integer", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页数量", dataType = "Integer", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    JSONObject getNotes(
            Integer page,
            Integer pageSize,
            String token
    ){
        return notesService.getAllByUser(page,pageSize,token);
    }

    @ApiOperation(value = "获取笔记列表", notes = "获取笔记列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "folderUuid", value = "文件夹uuid", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "noteUuid", value = "笔记ID", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ResponseBody
    JSONObject moveToFolder(
            String folderUuid,
            String noteUuid,
            String token
    ){
        return notesService.moveToFolder(noteUuid, folderUuid, token);
    }

}
