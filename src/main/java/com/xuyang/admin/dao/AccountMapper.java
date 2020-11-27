package com.xuyang.admin.dao;


import com.xuyang.admin.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    Account login(String username, String password);

    Account getAccountByUsername(String username);

    Integer addUser(Account account);

    Integer deleteByUuid(String uuid);

    Integer updatePassword(String password, String uuid);

    Account getAccountByUUidAndPsw(String uuid, String password);
}
