package com.xuyang.blog.service;

import com.xuyang.blog.dao.OpLogsMapper;
import com.xuyang.blog.entity.OpLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OpLogsService {
    @Autowired
    private OpLogsMapper opLogsMapper;

    public int addLogs( String userUuid, String username, String detail){return opLogsMapper.addLogs( userUuid, username, detail);}

    public List<OpLogs> getAllLogs(String username, String startTime, String endTime, Integer pageBefore, Integer pageSize){return opLogsMapper.getAllLogs(username, startTime, endTime, pageBefore, pageSize);}

    public int getTotalRecord(String username, String startTime, String endTime){return opLogsMapper.getTotalRecord(username, startTime, endTime);}

    public List<OpLogs> getLogsByIds(String[] ids){return opLogsMapper.getLogsByIds(ids);}
}
