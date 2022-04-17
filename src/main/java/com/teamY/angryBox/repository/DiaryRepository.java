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

    public int insertDiary(DiaryVO diary) {
        int diaryId = diaryMapper.insertDiary(diary);
        return diaryId;
    }

    public void insertDiaryFile(int diaryId, int fileId) {
        diaryMapper.insertDiaryFile(diaryId, fileId);
    }


}
