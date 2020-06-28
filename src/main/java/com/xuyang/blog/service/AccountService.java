package com.xuyang.blog.service;

import com.xuyang.blog.dao.AccountMapper;
import com.xuyang.blog.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public Account login(String username, String password){return accountMapper.login(username, password);}
}
