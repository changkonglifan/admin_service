package com.xuyang.blog.dao;

import com.xuyang.blog.entity.OpLogs;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OpLogsMapper {
    int addLogs(String userUuid, String username, String detail);

    List<OpLogs> getAllLogs(String username, String startTime, String endTime);
}
