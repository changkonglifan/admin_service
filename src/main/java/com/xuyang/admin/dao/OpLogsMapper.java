package com.xuyang.admin.dao;

import com.xuyang.admin.entity.OpLogs;
import io.lettuce.core.dynamic.annotation.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OpLogsMapper {
    int addLogs(String userUuid, String username, String detail);

    List<OpLogs> getAllLogs(String username, String startTime, String endTime, Integer pageBefore, Integer pageSize);

    int getTotalRecord(String username, String startTime, String endTime);

    List<OpLogs> getLogsByIds(@Param("idList")String[] idList);
}
