package com.xuyang.blog.dao;


import com.xuyang.blog.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {
    List<Account> queryAccountList();

    int insertAccount(Account account);

    Account getAccountByUserName(String userName);

    int updateAccount(Account account);
}
