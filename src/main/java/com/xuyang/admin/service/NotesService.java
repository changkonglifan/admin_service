package com.xuyang.admin.service;

import com.xuyang.admin.dao.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotesService {

    @Autowired
    private NotesMapper notesMapper;

    /**
     * 添加笔记
     * @param uuid
     * @param title
     * @param detail
     * @return
     */
    public Integer addNotes(String uuid, String title, String detail, String userId){return notesMapper.addNotes(uuid, title, detail, userId);}
}
