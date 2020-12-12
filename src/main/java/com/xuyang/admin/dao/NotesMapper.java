package com.xuyang.admin.dao;

import org.mapstruct.Mapper;

@Mapper
public interface NotesMapper {

    public int addNotes(String uuid, String title, String detail, String userId);
}
