package com.xuyang.blog.service;

import com.xuyang.blog.dao.AccountMapper;
import com.xuyang.blog.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    public Account getAccountByUsername(String username){return accountMapper.getAccountByUsername(username);}

    public Integer addUser(Account account){return accountMapper.addUser(account);};

    public Integer deleteByUuid(String uuid) {return accountMapper.deleteByUuid(uuid);}
}
