package com.xuyang.blog.dao;

import com.xuyang.blog.entity.AccountInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/6/28 17:16
 * @Description:
 */
@Mapper
public interface AccountInfoMapper {

    AccountInfo getAccountInfo(String uuid);

    Integer addUser(AccountInfo accountInfo);

    Integer deleteByUuid(String uuid);

    List<AccountInfo> getAllAccount();

    Integer updateAccount(AccountInfo accountInfo);
}
