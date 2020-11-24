package com.xuyang.blog.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.blog.entity.OpLogs;
import com.xuyang.blog.service.OpLogsService;
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

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/11/24 10:03
 * @Description:
 */

@RestController
@RequestMapping(value = "/admin/logs")
@Api(tags = "日志管理")
@CrossOrigin
public class OpLogsController {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    private OpLogsService opLogsService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "获取所有日志", notes = "获取所有日志")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "query", name = "page", value = "page", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "pageSize", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @ResponseBody
    public JSONObject getAllLogs(
            String username,
            String startTime,
            String endTime,
            Integer page,
            Integer pageSize,
            String token
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            JSONObject js = new JSONObject();
            int limitBefore = (page - 1) * pageSize;
            List<OpLogs> list = opLogsService.getAllLogs(username, startTime, endTime, limitBefore, pageSize);
            Integer total = opLogsService.getTotalRecord(username, startTime, endTime);
            js.put("list", list);
            js.put("page", page);
            js.put("pageSize", pageSize);
            js.put("totalRecord", total);
            return  MessageOut.successful(js);
        }catch (Exception e){
            return MessageOut.failed(-1, e.getMessage());
        }
    }

}
