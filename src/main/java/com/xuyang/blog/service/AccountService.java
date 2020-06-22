package com.xuyang.blog.service;

import com.xuyang.blog.dao.AccountMapper;
import com.xuyang.blog.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public List<Account> getAccount(){
        return accountMapper.queryAccountList();
    }
    public int insertAccount(Account account){
        return accountMapper.insertAccount(account);
    }
    public Account getAccountByUserName(String userName){return accountMapper.getAccountByUserName(userName);}
    public int updateAccount(Account account){return accountMapper.updateAccount(account);}
}
