package com.xuyang.blog.service;

import com.xuyang.blog.dao.AccountInfoMapper;
import com.xuyang.blog.entity.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Integer addUser(AccountInfo accountInfo){return accountInfoMapper.addUser(accountInfo);}

    public Integer deleteByUuid(String uuid){return accountInfoMapper.deleteByUuid(uuid);}

    public List<AccountInfo> getAllAccount(){return accountInfoMapper.getAllAccount();};

    public Integer updateAccount(AccountInfo accountInfo){return accountInfoMapper.updateAccount(accountInfo);}
}
