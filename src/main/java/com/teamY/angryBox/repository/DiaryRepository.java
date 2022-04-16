package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.DiaryMapper;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class DiaryRepository {
    private final DiaryMapper diaryMapper;

    public void insertDiary(DiaryVO diary) {
        log.info("diaryReposiroty 들어옴");
        diaryMapper.insertDiary(diary);
    }
}
