package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void insertFile(FileVO file);
    FileVO selectById(int id);
    void deleteFile(int id);
    void insertInterimFile(FileVO file);
}
