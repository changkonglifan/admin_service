package com.xuyang.blog.service;

import com.xuyang.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
     * @param token
     * @return
     */
    public String getPcTokenInfo(String token){
        boolean isExists = redisUtil.exists(token);
        String uuid = "";
        if(isExists){
            // token 存在
            Object rs = new Object();
            rs = redisUtil.get(token);
            //更新 失效时间
            redisUtil.set(token, rs, (long) 24 * 60 * 60);
            uuid = (String) rs;
        }
        return uuid;
    }
}
