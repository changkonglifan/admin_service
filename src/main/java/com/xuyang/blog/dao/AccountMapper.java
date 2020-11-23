package com.xuyang.blog.dao;


import com.xuyang.blog.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    Account login(String username, String password);

    Account getAccountByUsername(String username);

    Integer addUser(Account account);

    Integer deleteByUuid(String uuid);

    Integer updatePassword(String password, String uuid);
}
