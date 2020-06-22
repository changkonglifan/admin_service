package com.xuyang.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.blog.entity.Account;
import com.xuyang.blog.entity.Result;
import com.xuyang.blog.service.AccountService;
import com.xuyang.blog.utils.MessageOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public JSONObject getAll(){
        List<Account> list = accountService.getAccount();
        JSONObject js = new JSONObject();
        js.put("list", list);

        return MessageOut.successful(js);
    }

    /**
     * 新增用户
     * @param account
     * @return
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public Result insertAccount(@RequestBody Account account){
        Result result = new Result();

        Account accountList = getAccountByUserName((account.getUsername()));
        System.out.print(account.getUsername());
        if(accountList != null){
            result.setCode(-1);
            result.setMsg("当前用户名已存在");
            return result;
        }
        account.setUuid(UUID.randomUUID().toString());
        int insert = accountService.insertAccount(account);
        if(insert == 1){
            result.setCode(1);
            result.setMsg("成功");
            return result;
        }
        return result;
    }

    /**
     * 更新用户
     * @param account
     * @return
     */
    public Result updateAccount(@RequestBody Account account){
        Result result = new Result();
        int update = accountService.updateAccount(account);
        if(update == 1){
            result.setCode(1);
            result.setMsg("成功");
            return result;
        }
        return result;
    }
    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public Account getAccountByUserName(String userName){
        return accountService.getAccountByUserName(userName);
    }
}
