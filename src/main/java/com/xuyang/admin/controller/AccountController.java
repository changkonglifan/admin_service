package com.xuyang.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.constants.Constants;
import com.xuyang.admin.controller.Admin.AdminAccountController;
import com.xuyang.admin.service.OpLogsService;
import com.xuyang.admin.utils.MessageOut;
import com.xuyang.admin.utils.RSAEncrypt;
import com.xuyang.admin.entity.Account;
import com.xuyang.admin.entity.AccountInfo;
import com.xuyang.admin.service.AccountInfoService;
import com.xuyang.admin.service.AccountService;
import com.xuyang.admin.utils.Common;
import com.xuyang.admin.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
@EnableSwagger2
public class AccountController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OpLogsService opLogsService;

    private  Long expireTime = (long) 24 * 60 * 60;

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
            newPassword = Common.getMD5Str(newPassword).toUpperCase();;
            System.out.println(newPassword);
            Account account = accountService.login(username, newPassword);
            if(account == null){
                return MessageOut.failed(-1, "用户名或密码错误");
            }
            if(account.getIsDel() == "1"){
                return MessageOut.failed(-2, "当前用户已删除");
            }
            AccountInfo accountInfo = accountInfoService.getAccountInfoByUuid(account.getUuid());
            if(!"0".equals(accountInfo.getAuthName())){
                return MessageOut.failed(-1, "您没有此系统权限");
            }
            String uuid = Constants.APP_TOKEN.concat(UUID.randomUUID().toString());
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
            return MessageOut.failed(-3, e.toString());
        }
    }
}
