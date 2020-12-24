package com.xuyang.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.xuyang.admin.controller.Admin.AdminAccountController;
import com.xuyang.admin.dao.FolderMapper;
import com.xuyang.admin.entity.Folder;
import com.xuyang.admin.entity.Token;
import com.xuyang.admin.utils.MessageOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XuYang
 * @Date: 2020/12/24 14:08
 * @Description:
 */
@Service
public class FolderService {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private TokenService tokenService;
    /**
     * 根据用户id 和父ID获取目录列表
     * @param pid
     * @param token
     * @return
     */
    public JSONObject getFoldersByPid(String pid, String token){
        try{
            Token tk = tokenService.getPcTokenInfo(token);
            if(tk.getUuid() == null){
                return MessageOut.failed(-101, "token已过期");
            }
            List<Folder> list = folderMapper.getFoldersByPid(pid, tk.getUuid());
            logger.info(tk.getUserName() +"获取文件夹列表");
            JSONObject js = new JSONObject();
            js.put("list", list);
            return MessageOut.successful(js);
        }catch (Exception e){
            e.printStackTrace();
            return MessageOut.failed(-1, e.toString());
        }
    }
}
