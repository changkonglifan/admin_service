package com.xuyang.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.controller.Admin.AdminAccountController;
import com.xuyang.admin.dao.NotesMapper;
import com.xuyang.admin.entity.Notes;
import com.xuyang.admin.utils.MessageOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class NotesService {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * 添加笔记
     * @param title
     * @param detail
     * @param token
     * @return
     */
    public JSONObject addNotes(String title, String detail, String token){
        try{
            Object tokenJS = tokenService.getTokenInfo(token);
            JSONObject js = new JSONObject();
            String  uuid = UUID.randomUUID().toString().replace("-","");
            Map accountToken = (Map) tokenJS;

            int result = notesMapper.addNotes(uuid, title, detail, (String) accountToken.get("uuid"));
            if(result > 0){
                return MessageOut.successful("新增成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }

        return MessageOut.successful();
    }

    /**
     * 获取最新的笔记
     * 翻页
     * @param page
     * @param pageSize
     * @param token
     * @return
     */
    public JSONObject getAllByUser(Integer page, Integer pageSize, String token){
        try{
            JSONObject js = new JSONObject();
            Object tokenJS = tokenService.getTokenInfo(token);
            Map accountToken = (Map) tokenJS;
            Integer pageBefore = (page - 1) * pageSize;
            List<Notes> list = notesMapper.getAllByUser( (String) accountToken.get("uuid"), pageBefore, pageSize);
            js.put("list", list);
            logger.info((String) accountToken.get("username")+"获取所有笔记");
            return MessageOut.successful(js);
        }catch(Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }

    }
}
