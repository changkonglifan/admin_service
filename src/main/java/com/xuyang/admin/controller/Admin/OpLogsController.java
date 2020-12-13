package com.xuyang.admin.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.excels.ExportExcelUtil;
import com.xuyang.admin.excels.ExportExcelWrapper;
import com.xuyang.admin.service.OpLogsService;
import com.xuyang.admin.service.TokenService;
import com.xuyang.admin.utils.MessageOut;
import com.xuyang.admin.entity.OpLogs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
            if(!tokenService.isEffect(token)){
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

    @ApiOperation(value = "导出日志",notes = "导出日志")
    @RequestMapping(value = "/exportAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String",required = true)
    })
    @ResponseBody
    public void exportAllLogs (
            String token,
            HttpServletResponse response
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(!tokenService.isEffect(token)){
                return;
            }
            // 导出
            ExportExcelWrapper<OpLogs> util = new ExportExcelWrapper<OpLogs>();

            List<OpLogs> list = opLogsService.getAllLogs(null, null, null, null,null);

            String[] columns  = {"ID", "用户ID" ,"用户名","详情", "创建时间"};

            util.exportExcel("logs", "日志", columns, list, response, ExportExcelUtil.EXCEl_FILE_2007);

            logger.info("导出成功");
        }catch (Exception e){
        }
    }


    @ApiOperation(value = "导出选中",notes = "导出选中")
    @RequestMapping(value = "/exportSelected", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "选中ID", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String",required = true)
    })
    @ResponseBody
    public void exportSelected (
            String ids,
            String token,
            HttpServletResponse response
    ){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(!tokenService.isEffect(token)){
                return;
            }
            // 导出
            ExportExcelWrapper<OpLogs> util = new ExportExcelWrapper<OpLogs>();

            String[] idList =  ids.split(",");
            List<OpLogs> list = opLogsService.getLogsByIds(idList);
            System.out.println(list);
            String[] columns  = {"ID", "用户ID" ,"用户名","详情", "创建时间"};

            util.exportExcel("logs", "日志", columns, list, response,ExportExcelUtil.EXCEl_FILE_2007);

            logger.info("导出选中成功："+ ids);
        }catch (Exception e){
        }
    }
}
