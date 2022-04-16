package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryMapper {
    void insertDiary(DiaryVO diary);
}
