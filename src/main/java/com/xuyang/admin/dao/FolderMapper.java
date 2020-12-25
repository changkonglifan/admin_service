package com.xuyang.admin.dao;

import com.xuyang.admin.entity.Folder;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/12/24 13:55
 * @Description:
 */
@Mapper
public interface FolderMapper {
    List<Folder> getFoldersByPid(String pid, String userUuid);

    int addFolder(String title, String pid,String uuid, String userUuid);

}
