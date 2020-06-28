package com.xuyang.blog.controller;

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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@EnableSwagger2
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInfoService accountInfoService;
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
            newPassword = Common.getMD5Str(newPassword);
            Account account = accountService.login(username, newPassword);
            if(account == null){
                return MessageOut.failed(-1, "用户名或密码错误");
            }
            if(account.getIsDel() == "1"){
                return MessageOut.failed(-2, "当前用户已删除");
            }
            AccountInfo accountInfo = accountInfoService.getAccountInfoByUuid(account.getUuid());
            HttpSession session = request.getSession(true);
            session.setAttribute("userinfo", account);
            js.put("account", accountInfo);
            return MessageOut.successful(js);
        }catch (Exception e){
            return MessageOut.failed(-3, e.getMessage());
        }
    }
}
