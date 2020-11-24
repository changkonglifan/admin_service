package com.xuyang.blog.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.blog.utils.RedisUtil;
import com.xuyang.blog.constants.Constants;
import com.xuyang.blog.entity.Account;
import com.xuyang.blog.entity.AccountInfo;
import com.xuyang.blog.service.AccountInfoService;
import com.xuyang.blog.service.AccountService;
import com.xuyang.blog.service.OpLogsService;
import com.xuyang.blog.service.TokenService;
import com.xuyang.blog.utils.Common;
import com.xuyang.blog.utils.MessageOut;
import com.xuyang.blog.utils.ObjectUtils;
import com.xuyang.blog.utils.RSAEncrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: XuYang
 * @Date: 2020/6/29 14:04
 * @Description:
 */
@RestController
@RequestMapping(value = "/admin/user")
@Api(tags = "后台用户管理")
@CrossOrigin
public class AdminAccountController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OpLogsService opLogsService;

    private  Long expireTime = (long) 24 * 60 * 60;

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "form", name = "username", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "password", value = "密码", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "nickName", value = "昵称", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "sex", value = "性别", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "email", value = "邮箱", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "name", value = "姓名", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "mobile", value = "手机", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "authName", value = "用户类型", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "job", value = "职业", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "introduction", value = "介绍", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @ResponseBody
    public JSONObject addUser(
        HttpServletRequest request,
        String username,
        String nickName,
        String sex,
        String name,
        String mobile,
        String email,
        String authName,
        String job,
        String introduction,
        String token
    ){
        try{

            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            //  新增用户， 判断用户username 是否重复
            Account accountRes = accountService.getAccountByUsername(username);
            if(accountRes != null){
                return MessageOut.failed(-3, "当前用户已存在");
            }
            Map accountToken = (Map) tokenJS;
            String uuid = UUID.randomUUID().toString().replace("-","");
            AccountInfo accountInfo = new AccountInfo();
            Account account = new Account();
            // account 表
            // 默认密码
            account.setPassword(Common.getMD5Str(Constants.PASSWORD));
            account.setUsername(username);
            account.setErrCount("0");
            account.setIsDel("0");
            account.setUuid(uuid);
            account.setUsedPwd("");
            account.setIsDisable("0");
            // account_info 表
            accountInfo.setUsername(username);
            accountInfo.setUuid(uuid);
            accountInfo.setAuthName(authName);
            accountInfo.setIntroduction(introduction);
            accountInfo.setIsDel("0");
            accountInfo.setJob(job);
            accountInfo.setLevel("1");
            accountInfo.setMobile(mobile);
            accountInfo.setName(name);
            accountInfo.setNickName(nickName);
            accountInfo.setSex(sex);
            accountInfo.setEmail(email);
            Integer addAccount = accountService.addUser(account);
            Integer addAccountInfo = accountInfoService.addUser(accountInfo);
            if(addAccount > 0 && addAccountInfo > 0){
                logger.info((String) accountToken.get("username") +"新增用户");
                opLogsService.addLogs((String) accountToken.get("uuid"), (String) accountToken.get("username"), "新增用户" +username);
                return MessageOut.successful("新增成功");
            }
            if(addAccount > 0 && addAccountInfo <= 0){
                // 一项 成功，删除另外以项
                accountService.deleteByUuid(uuid);
                return MessageOut.failed(-1, "新增失败");
            }else if(addAccount <= 0 && addAccountInfo > 0){
                accountInfoService.deleteByUuid(uuid);
                return MessageOut.failed(-1, "新增失败");
            }
        }catch (Exception e){
            return MessageOut.failed(-1, e.getMessage());
        }
        return MessageOut.successful();
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "uuid", value = "id", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "nickName", value = "昵称", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "email", value = "邮箱", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "sex", value = "性别", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "name", value = "姓名", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "mobile", value = "手机", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "authName", value = "用户类型", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "job", value = "职业", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "introduction", value = "介绍", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
    })
    @ResponseBody
    public JSONObject updateUser(
            HttpServletRequest request,
            String uuid,
            String nickName,
            String sex,
            String name,
            String mobile,
            String email,
            String authName,
            String job,
            String introduction,
            String token
    ){
        try{

            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            Map account = (Map) tokenJS;
            AccountInfo accountInfo = new AccountInfo();
            // account_info 表
            accountInfo.setUuid(uuid);
            accountInfo.setAuthName(authName);
            accountInfo.setIntroduction(introduction);
            accountInfo.setIsDel("0");
            accountInfo.setJob(job);
            accountInfo.setLevel("1");
            accountInfo.setMobile(mobile);
            accountInfo.setName(name);
            accountInfo.setNickName(nickName);
            accountInfo.setSex(sex);
            accountInfo.setEmail(email);
            Integer result = accountInfoService.updateAccount(accountInfo);
            logger.info(account.get("username") + "修改用户");
            opLogsService.addLogs((String ) account.get("uuid"),(String) account.get("username"), "修改用户,uuid=" + uuid );
            if(result > 0 ){
                return MessageOut.successful("修改成功");
            }
        }catch (Exception e){
            return MessageOut.failed(-1, e.getMessage());
        }
        return MessageOut.successful();
    }

    /**
     * 获取所有的用户列表
     * @return
     */
    @ApiOperation(value = "获取所有用户", notes = "获取所有用户")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "nickName", value = "昵称", dataType = "String", required = false),
        @ApiImplicitParam(paramType = "query", name = "name", value = "姓名", dataType = "String", required = false),
        @ApiImplicitParam(paramType = "query", name = "mobile", value = "手机", dataType = "String", required = false),
        @ApiImplicitParam(paramType = "query", name = "isDel", value = "是否删除", dataType = "String", required = false),
        @ApiImplicitParam(paramType = "query", name = "authName", value = "用户类型", dataType = "String", required = false),
        @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String", required = true),
        @ApiImplicitParam(paramType = "query", name = "page", value = "page", dataType = "String", required = true),
        @ApiImplicitParam(paramType = "query", name = "pageSize", value = "pageSize", dataType = "String", required = true),
     })
    @ResponseBody
    public JSONObject getAllUser(
        String username,
        String name,
        String mobile,
        String isDel,
        String authName,
        Integer page,
        Integer pageSize,
        String token
    ){
        JSONObject js = new JSONObject();
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(ObjectUtils.isEmpty(token)){
                // token失效
                return MessageOut.sessionOut();
            }
            int limitBefore = (page - 1) * pageSize;
            List<AccountInfo> accountList = accountInfoService.getAllAccount(username, name, mobile, authName, isDel, limitBefore, pageSize);
            js.put("list", accountList);
            js.put("page", page);
            js.put("pageSize", pageSize);
            js.put("totalRecord", accountInfoService.getAccountTotal(username, name, mobile, authName, isDel));
            return MessageOut.successful(js);
        }catch (Exception e){
            return MessageOut.failed(-1, e.getMessage());
        }
    }

    /**
     * 登录
     * @return
     */

    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", dataType = "String", value = "用户名", name = "username", required = true),
            @ApiImplicitParam(paramType="form", dataType = "String", value = "密码", name = "password", required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    JSONObject login(
            String username,
            String password
    ) {
        JSONObject js = new JSONObject();
        try{
            String newPassword = "";
            newPassword = RSAEncrypt.decrypt(password);
            newPassword = Common.getMD5Str(newPassword).toUpperCase();
            Account account = accountService.login(username, newPassword);
            if(account == null){
                return MessageOut.failed(-1, "用户名或密码错误");
            }
            if(account.getIsDel() == "1"){
                return MessageOut.failed(-2, "当前用户已删除");
            }
            AccountInfo accountInfo = accountInfoService.getAccountInfoByUuid(account.getUuid());
            if(accountInfo.getAuthName()  != "0"){
                return MessageOut.failed(-1, "您没有此系统权限");
            }
            String uuid = Constants.PC_ADMIN_TOKEN.concat(UUID.randomUUID().toString());
            JSONObject redisAccount = new JSONObject();
            redisAccount.put("uuid", accountInfo.getUuid());
            redisAccount.put("username", accountInfo.getUsername());
//            生成uuid写入redis
            redisUtil.set(uuid, redisAccount, expireTime);
            js.put("account", accountInfo);
            js.put("token", uuid);
            logger.info( "登录成功", js);
            // 登录写入日志
            opLogsService.addLogs(accountInfo.getUuid(), accountInfo.getUsername(), "登录系统");

            return MessageOut.successful(js);
        }catch (Exception e){

            return MessageOut.failed(-3, e.getMessage());
        }
    }

    /**
     * 删除
     * @return
     */

    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType = "String", value = "uuid", name = "uuid", required = true),
            @ApiImplicitParam(paramType="query", dataType = "String", value = "token", name = "token", required = true)
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    JSONObject deleteUse(
            HttpServletRequest request,
            String uuid,
            String token
    ){
        JSONObject js = new JSONObject();
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            Map accountToken = (Map) tokenJS;
            Integer result = accountInfoService.deleteByUuid(uuid);
            Integer aResult = accountService.deleteByUuid(uuid);
            if(result > 0 && aResult > 0){
                logger.info((String) accountToken.get("username") +"删除用户");
                opLogsService.addLogs((String) accountToken.get("uuid"), (String) accountToken.get("username"), "删除用户" +uuid);
                return MessageOut.successful("删除成功");
            }else {
                return MessageOut.failed(-1, "删除失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return MessageOut.failed(-1, e.getMessage());
        }
    }
    /**
     * 删除
     * @return
     */

    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", dataType = "String", value = "uuid", name = "uuid", required = true),
            @ApiImplicitParam(paramType="query", dataType = "String", value = "token", name = "token", required = true)
    })
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    JSONObject resetPassword(
            String uuid,
            String token
    ){
        JSONObject js = new JSONObject();
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            if(tokenJS == null){
                // token失效
                return MessageOut.sessionOut();
            }
            Map accountToken = (Map) tokenJS;
            Integer result = accountService.updatePassword(Common.getMD5Str(Constants.PASSWORD) , uuid);
            if(result > 0 ){
                logger.info((String) accountToken.get("username") +"重置密码");
                opLogsService.addLogs((String) accountToken.get("uuid"), (String) accountToken.get("username"), "重置密码" +uuid);
                return MessageOut.successful("重置成功");
            }else {
                return MessageOut.failed(-1, "重置失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return MessageOut.failed(-1, e.getMessage());
        }
    }
}
