package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.DiaryMapper;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class DiaryRepository {
    private final DiaryMapper diaryMapper;

    public int selectAngryId(int angryPhaseId) {
        return diaryMapper.selectAngryId(angryPhaseId);
    }

    public int selectCoinBankMemberId(int coinBankId, int memberId) {
        return diaryMapper.selectCoinBankMemberId(coinBankId, memberId);
    }

    public int insertDiary(DiaryVO diary) {
        return diaryMapper.insertDiary(diary);
    }

    public void insertDiaryFile(int diaryId, int fileId, int fileNo) {
        diaryMapper.insertDiaryFile(diaryId, fileId, fileNo);
    }

    public List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId) {
        return diaryMapper.selectDiaryListInCoinBank(memberId, coinBankId);
    }

    public List<DiaryVO> selectDiaryListInMonth(int memberId, int year, int month) {
        return diaryMapper.selectDiaryListInMonth(memberId, year, month);
    }

    public List<DiaryFileVO> selectDiaryDetail(int diaryId) {
        return diaryMapper.selectDiaryDetail(diaryId);
    }

    public int selectDiaryMemberId(int diaryId, int memberId) {
        return diaryMapper.selectDiaryMemberId(diaryId, memberId);
    }

    public void deleteDiary(int diaryId, int memberId) {
        diaryMapper.deleteDiary(diaryId, memberId);
    }

    public void updateDiary(DiaryVO diary) {
        diaryMapper.updateDiary(diary);
    }

    public void deleteFileInDiary(int fileId){
        diaryMapper.deleteFileInDiary(fileId);
    }

    public int selectMaxFileNo(int diaryId){
        return diaryMapper.selectMaxFileNo(diaryId);
    }

}
