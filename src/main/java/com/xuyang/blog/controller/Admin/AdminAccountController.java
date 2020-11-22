package com.xuyang.blog.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.blog.entity.Account;
import com.xuyang.blog.entity.AccountInfo;
import com.xuyang.blog.service.AccountInfoService;
import com.xuyang.blog.service.AccountService;
import com.xuyang.blog.utils.Common;
import com.xuyang.blog.utils.MessageOut;
import com.xuyang.blog.utils.RSAEncrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * @Author: XuYang
 * @Date: 2020/6/29 14:04
 * @Description:
 */
@RestController
@RequestMapping(value = "/admin/user")
@Api(tags = "后台用户管理")
public class AdminAccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInfoService accountInfoService;


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
            @ApiImplicitParam(paramType = "form", name = "authRange", value = "权限范围", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "avatar", value = "头像地址", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "job", value = "职业", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "introduction", value = "介绍", dataType = "String", required = false),
    })
    @ResponseBody
    public JSONObject addUser(
        HttpServletRequest request,
        String username,
        String password,
        String nickName,
        String sex,
        String name,
        String mobile,
        String email,
        String authName,
        String authRange,
        String avatar,
        String job,
        String introduction
    ){
        try{
            Account aa = (Account) request.getSession().getAttribute("userinfo");
            if(aa == null){
                return MessageOut.sessionOut();
            }
            //  新增用户， 判断用户username 是否重复
            Account accountRes = accountService.getAccountByUsername(username);
            if(accountRes != null){
                return MessageOut.failed(-3, "当前用户已存在");
            }
            String newPassword = RSAEncrypt.decrypt(password);
            if(newPassword.length() < 6){
                return MessageOut.failed(-4, "密码长度不符合规范");
            }
            String uuid = UUID.randomUUID().toString().replace("-","");
            AccountInfo accountInfo = new AccountInfo();
            Account account = new Account();
            // account 表
            account.setPassword(Common.getMD5Str(newPassword));
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
//            @ApiImplicitParam(paramType = "form", name = "username", value = "用户名", dataType = "String", required = true),
//            @ApiImplicitParam(paramType = "form", name = "password", value = "密码", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "uuid", value = "id", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "nickName", value = "昵称", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "email", value = "邮箱", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "sex", value = "性别", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "name", value = "姓名", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "mobile", value = "手机", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "authName", value = "用户类型", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "form", name = "authRange", value = "权限范围", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "avatar", value = "头像地址", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "job", value = "职业", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "form", name = "introduction", value = "介绍", dataType = "String", required = false),
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
            String authRange,
            String avatar,
            String job,
            String introduction
    ){
        try{
            Account aa = (Account) request.getSession().getAttribute("userinfo");
            if(aa == null){
                return MessageOut.sessionOut();
            }
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
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllUser(
        HttpServletRequest request
    ){
        try{
            Account account = (Account) request.getSession().getAttribute("user");
            if(account == null){
                return MessageOut.sessionOut();
            }
            List<AccountInfo> accountList = accountInfoService.getAllAccount();
            JSONObject js = new JSONObject();
            js.put("list", accountList);
            return MessageOut.successful(js);
        }catch (Exception e){
            return MessageOut.successful();
        }
    }

    /**
     * 登录
     * @param request
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
            HttpServletRequest request,
            String username,
            String password
    ) {
        System.out.println(username);
        JSONObject js = new JSONObject();
        try{
            String newPassword = "";
            newPassword = RSAEncrypt.decrypt(password);
            newPassword = Common.getMD5Str(newPassword).toUpperCase();
            System.out.println(newPassword);
            Account account = accountService.login(username, newPassword);
            if(account == null){
                return MessageOut.failed(-1, "用户名或密码错误");
            }
            if(account.getIsDel() == "1"){
                return MessageOut.failed(-2, "当前用户已删除");
            }
            AccountInfo accountInfo = accountInfoService.getAccountInfoByUuid(account.getUuid());
            HttpSession session = request.getSession(true);
            session.setAttribute("user", account);
            js.put("account", accountInfo);
            return MessageOut.successful(js);
        }catch (Exception e){
            return MessageOut.failed(-3, e.getMessage());
        }
    }
}
