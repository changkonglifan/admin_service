package com.xuyang.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.controller.Admin.AdminAccountController;
import com.xuyang.admin.dao.NotesMapper;
import com.xuyang.admin.entity.Notes;
import com.xuyang.admin.entity.Token;
import com.xuyang.admin.utils.MessageOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            Token tk = tokenService.getPcTokenInfo(token);
            if(tk.getUuid() == null){
                return MessageOut.failed(-101, "token已过期");
            }
            JSONObject js = new JSONObject();
            String  uuid = UUID.randomUUID().toString().replace("-","");

            int result = notesMapper.addNotes(uuid, title, detail, (String) tk.getUuid());
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
            Token tk = tokenService.getPcTokenInfo(token);
            if(tk.getUuid() == null){
                return MessageOut.failed(-101, "token已过期");
            }
            JSONObject js = new JSONObject();
            Integer pageBefore = (page - 1) * pageSize;
            List<Notes> list = notesMapper.getAllByUser( (String) tk.getUuid(), pageBefore, pageSize);
            js.put("list", list);
            logger.info((String) tk.getUserName() +"获取所有笔记");
            return MessageOut.successful(js);
        }catch(Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }

    }

    /**
     * 获取笔记详情
     * @param uuid
     * @return
     */
    public JSONObject getDetail(String uuid, String token){
        try{
            Token tk = tokenService.getPcTokenInfo(token);
            if(tk.getUuid() == null){
                return MessageOut.failed(-101, "token已过期");
            }
            JSONObject js = new JSONObject();
            Notes note = notesMapper.getDetail(uuid);
            js.put("note", note);
            logger.info("获取笔记详情"+ uuid);
            return MessageOut.successful(js);
        }catch (Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }
    }
    /**
     * 移动到文件夹下
     * @param uuid
     * @return
     */
    public JSONObject moveToFolder(String uuid,String folderUuid, String token){
        try{
            Token tk = tokenService.getPcTokenInfo(token);
            if(tk.getUuid() == null){
                return MessageOut.failed(-101, "token已过期");
            }
            int result = notesMapper.moveToFolder(folderUuid, uuid);
            logger.info(tk.getUserName() + "移动文件" + uuid +"到文件夹" + folderUuid);
            if(result > 0){
                return MessageOut.successful("移动成功");
            }else {
                return MessageOut.failed(-1,"移动失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }
    }

    /**
     * 收藏笔记
     * @param uuid
     * @param isCollection 0 取消收藏 1 收藏
     * @param token
     * @return
     */
    public JSONObject collectionNote(String uuid,Integer isCollection, String token){
        try{
            Token tk = tokenService.getPcTokenInfo(token);
            if(tk.getUuid() == null){
                return MessageOut.failed(-101, "token已过期");
            }
            int result = notesMapper.collectionNote(uuid, isCollection);
            logger.info(tk.getUserName()+"收藏笔记" + uuid);
            if(result >0){
                return MessageOut.successful("收藏成功");
            }else {
                return MessageOut.failed(-1, "收藏失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }
    }
}
