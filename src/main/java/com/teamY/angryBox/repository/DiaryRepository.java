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

    public int checkAngryId(int angryPhaseId) {
        return diaryMapper.checkAngryId(angryPhaseId);
    }

    public int checkCoinBankMemberId(int coinBankId, int memberId) {
        return diaryMapper.checkCoinBankMemberId(coinBankId, memberId);
    }

    public int checkCoinBankExpired(int coinBankId, int memberId) {
        return diaryMapper.checkCoinBankExpired(coinBankId, memberId, 0);
    }

    public int insertDiary(DiaryVO diary) {
        return diaryMapper.insertDiary(diary);
    }

    public void insertDiaryFile(int diaryId, int fileId, int fileNo) {
        diaryMapper.insertDiaryFile(diaryId, fileId, fileNo);
    }

    public int checkIsPublic(int diaryId) {
        return diaryMapper.checkIsPublic(diaryId);
    }

    public int checkDailyTopDiary(int writeYear, int writeMonth, int writeDay) {
        return diaryMapper.checkDailyTopDiary(writeYear, writeMonth, writeDay, 1);
    }

    public int checkDiaryMemberId(int diaryId, int memberId) {
        return diaryMapper.checkDiaryMemberId(diaryId, memberId);
    }

    public int checkFileInDiary(int diaryId, int fileId) {
        return diaryMapper.checkFileInDiary(diaryId, fileId);
    }

    public List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size) {
        return diaryMapper.selectDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size);
    }

    public List<DiaryVO> selectDiaryListInMonth(int memberId, int year, int month, int lastDiaryId, int size) {

        return diaryMapper.selectDiaryListInMonth(memberId, year, month, lastDiaryId, size);
    }

    public List<DiaryVO> selectDailyTop(int writeYear, int writeMonth, int writeDay, int lastDiaryId, int size) {
        return diaryMapper.selectDailyTop(writeYear, writeMonth, writeDay, 1, lastDiaryId, size);
    }

    public List<DiaryVO> selectTodayTop(int lastDiaryId, int size) {
        return diaryMapper.selectTodayTop(lastDiaryId, size);
    }

    public int selectLastId(int memberId, int coinBankId) {
        return diaryMapper.selectLastId(memberId, coinBankId);
    }

    public int selectDailyLastId(int writeYear, int writeMonth, int writeDay) {
        return diaryMapper.selectDailyLastId(writeYear, writeMonth, writeDay);
    }

    public int selectDailyLastIdInMonth(int memberId, int writeYear, int writeMonth) {
        return diaryMapper.selectDailyLastIdInMonth(memberId, writeYear, writeMonth);
    }

    public List<DiaryFileVO> selectDiaryDetail(int diaryId) {
        return diaryMapper.selectDiaryDetail(diaryId);
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

    public List<DiaryVO> bambooGrove(int diaryId, int size) {
        return diaryMapper.bambooGrove(diaryId, 1, size);
    }

    public int selectDiaryCountInCoinBank(int diaryId, int memberId) {
        return diaryMapper.selectDiaryCountInCoinBank(diaryId, memberId);
    }

    public int selectTodackCountInCoinBank(int diaryId, int memberId) {
        return diaryMapper.selectTodackCountInCoinBank( diaryId, memberId);
    }

    public List<DiaryVO> searchDiary(String searchKeyword, int lastDiaryId, int size) {

        return diaryMapper.searchDiary(searchKeyword, lastDiaryId, size);
    }

}
