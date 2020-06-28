package com.xuyang.blog.service;

import com.xuyang.blog.dao.AccountInfoMapper;
import com.xuyang.blog.entity.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: XuYang
 * @Date: 2020/6/28 17:16
 * @Description:
 */
@Service
public class AccountInfoService {
    @Autowired
    private  AccountInfoMapper accountInfoMapper;

    /**
     * 根据uuid获取用户信息
     * @param uuid
     * @return
     */
    public AccountInfo getAccountInfoByUuid(String uuid){return accountInfoMapper.getAccountInfo(uuid);}
}
