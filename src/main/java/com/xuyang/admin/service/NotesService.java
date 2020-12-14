package com.xuyang.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.dao.NotesMapper;
import com.xuyang.admin.entity.Notes;
import com.xuyang.admin.utils.MessageOut;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NotesService {

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * 添加笔记
     * @param uuid
     * @param title
     * @param detail
     * @return
     */
    public Integer addNotes(String uuid, String title, String detail, String userId){return notesMapper.addNotes(uuid, title, detail, userId);}

    /**
     * 获取最新的笔记
     * 翻页
     * @param page
     * @param pageSize
     * @param token
     * @return
     */
    public JSONObject getAllByUser(Integer page, Integer pageSize, String token){
        JSONObject js = new JSONObject();
        Object tokenJS = tokenService.getTokenInfo(token);
        Map accountToken = (Map) tokenJS;
        Integer pageBefore = (page - 1) * pageSize;
        List<Notes> list = notesMapper.getAllByUser( (String) accountToken.get("uuid"), pageBefore, pageSize);
        js.put("list", list);
        return MessageOut.successful(js);
    }
}
