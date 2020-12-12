package com.xuyang.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.controller.Admin.AdminAccountController;
import com.xuyang.admin.service.NotesService;
import com.xuyang.admin.service.OpLogsService;
import com.xuyang.admin.service.TokenService;
import com.xuyang.admin.utils.MessageOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@Api(tags = "笔记")
@RequestMapping("/notes")
@EnableSwagger2
public class NotesController {  @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

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
    public JSONObject addNotes(
            String title,
            String detail,
            String token
    ){
        Object tokenJS = tokenService.getTokenInfo(token);
        if(tokenJS == null){
            // token失效
            return MessageOut.sessionOut();
        }
        try{
            JSONObject js = new JSONObject();
            String  uuid = UUID.randomUUID().toString().replace("-","");;
            Map accountToken = (Map) tokenJS;

            int result = notesService.addNotes(uuid, title, detail, (String) accountToken.get("uuid"));
            if(result > 0){
                return MessageOut.successful("新增成功");
            }
        }catch (Exception e){
            return MessageOut.failed(-1, e.getMessage());
        }

        return MessageOut.successful();
    }
}
