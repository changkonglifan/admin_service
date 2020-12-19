package com.xuyang.admin.service;

import com.xuyang.admin.entity.AccountInfo;
import com.xuyang.admin.dao.AccountInfoMapper;
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

    public List<AccountInfo> getAllAccount(String username, String name, String mobile,String isDel, String authName, Integer pageBefore, Integer pageSize){return accountInfoMapper.getAllAccountByParams(username, name, mobile, authName,isDel, pageBefore, pageSize);};

    public Integer getAccountTotal(String username, String name, String mobile, String authName, String isDel){return accountInfoMapper.getAccountTotal(username, name, mobile, authName, isDel);}


    public Integer updateAccount(AccountInfo accountInfo){return accountInfoMapper.updateAccount(accountInfo);}
}
