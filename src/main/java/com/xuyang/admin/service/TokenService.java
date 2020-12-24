package com.xuyang.admin.service;

import com.xuyang.admin.entity.Token;
import com.xuyang.admin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: XuYang
 * @Date: 2020/11/23 11:57
 * @Description:
 */
@Service
public class TokenService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 是否存在token
     * @param token
     * @return
     */
    public boolean isEffect (String token) {
        return redisUtil.exists(token);
    }
    /**
     * 获取到token 并且更新 时间
     * @param token
     * @return
     */
    public Object getTokenInfo(String token){
        boolean isExists = redisUtil.exists(token);
        Object rs = new Object();
        if(isExists){
            // token 存在
            rs = redisUtil.get(token);
            //更新 失效时间
            redisUtil.set(token, rs, (long) 24 * 60 * 60);
        }
        return rs;
    }

    /**
     * 获取token信息
     * @param token
     * @return
     */
    public Token getPcTokenInfo(String token){
        Token tk =  new Token();
        try{
            boolean isExists = redisUtil.exists(token);
            String uuid = "";
            String userName = "";
            if(isExists){
                // token 存在
                Map rs =(Map) redisUtil.get(token);

                //更新 失效时间
                redisUtil.set(token, rs, (long) 24 * 60 * 60);
                uuid = (String) rs.get("uuid");
                userName = (String) rs.get("username");
                tk.setUuid(uuid);
                tk.setUserName(userName);
            }
            return tk;
        }catch (Exception e){
            e.printStackTrace();
            tk.setUserName(null);
            tk.setUuid(null);
            return tk;
        }

    }
}
