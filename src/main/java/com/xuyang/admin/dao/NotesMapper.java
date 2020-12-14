package com.xuyang.admin.dao;

import com.xuyang.admin.entity.Notes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface NotesMapper {

    int addNotes(String uuid, String title, String detail, String userId);

    List<Notes> getAllByUser(String uuid, Integer pageBefore, Integer pageSize);
}
