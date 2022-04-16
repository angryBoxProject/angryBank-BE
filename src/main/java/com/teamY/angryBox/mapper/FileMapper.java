package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    public void insertFile(FileVO file);
}
