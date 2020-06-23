package com.xuyang.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.blog.entity.Account;
import com.xuyang.blog.service.AccountService;
import com.xuyang.blog.utils.MessageOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("user")
@Api(tags = "用户管理")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 获取所有的用户
     * @return
     */
    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    @ApiOperation("获取所有的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "username", value = "用户名", required = false),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "name", value = "姓名", required = false)
    })
    public JSONObject getAll(
            HttpServletRequest request,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "name", required = true) String name
    ){
        List<Account> list = accountService.getAccount();
        JSONObject js = new JSONObject();
        js.put("list", list);

        return MessageOut.successful(js);
    }

    /**
     * 新增用户
//     * @param request
//     * @param username
//     * @param password
     * @return
     */
    @ApiOperation(value = "添加用户", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "username", value = "用户名", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "密码", required = true),
            @ApiImplicitParam(dataType = "String", name = "realName", value = "真实姓名", required = true),
            @ApiImplicitParam(dataType = "String", name = "email", value = "邮箱"),
            @ApiImplicitParam(dataType = "String", name = "phone", value = "手机"),
    })
    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addUser(
//            HttpServletRequest request,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "realName") String realName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone
    ){
        try{
            JSONObject js = new JSONObject();
//            Account account = new Account();
            System.out.print(username);
//            Account accountList = accountService.getAccountByUserName(username);
//            System.out.print(accountList);
//            String newPassword = RSAEncrypt.decrypt(password);
//            if(newPassword.length() < 6){
//                return MessageOut.failed(-1, "密码长度不符合规则");
//            }
//            if(accountList != null){
//
//                return MessageOut.failed(-1, "用户已存在");
//            }
//            account.setUuid(UUID.randomUUID().toString());
//            account.setUsername(username);
//            // md5 加密密码
//            account.setPassword(Common.getMD5Str(newPassword));
//            account.setRealName(realName);
//            account.setEmail(email);
//            account.setPhone(phone);
//            account.setIsDel(0);
//            System.out.print(account);
//            int insert = accountService.insertAccount(account);
//            if(insert == 1){
                return MessageOut.successful();
//            }
        }catch (Exception e){
            return MessageOut.failed(-1, e.getMessage());
        }
//        return MessageOut.successful();
    }
}
