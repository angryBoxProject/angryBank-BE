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
        log.info("diaryReposiroty 들어옴");
        return diaryMapper.insertDiary(diary);
    }

    public void insertDiaryFile(int diaryId, int fileId) {
        log.info("diaryId : " + diaryId, "  fileId : " + fileId);
        diaryMapper.insertDiaryFile(diaryId, fileId);
    }


}
