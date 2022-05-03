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

    public DiaryVO insertDiary(DiaryVO diaryVO) {
        return diaryMapper.insertDiary(diaryVO);
    }

    public void insertDiaryFile(int diaryId, int fileId, int fileNo) {
        diaryMapper.insertDiaryFile(diaryId, fileId, fileNo);
    }

    public int checkIsPublic(int diaryId) {
        return diaryMapper.checkIsPublic(diaryId);
    }

    public int checkIsDeleted(int diaryId) {
        return diaryMapper.checkIsDeleted(diaryId);
    }

    public int checkDailyTopDiary(String writeDate) {
        return diaryMapper.checkDailyTopDiary(writeDate);
    }

    public int checkDiaryId(int diaryId) {
        return diaryMapper.checkDiaryId(diaryId);
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

    public List<DiaryVO> selectDiaryListInMonth(int memberId, String writeDate, int lastDiaryId, int size) {

        return diaryMapper.selectDiaryListInMonth(memberId, writeDate, lastDiaryId, size);
    }

    public List<DiaryVO> selectDailyTop(String writeDate, int lastDiaryId, int size) {
        return diaryMapper.selectDailyTop(writeDate, lastDiaryId, size);
    }

    public List<DiaryVO> selectTodayTop(int lastDiaryId, int size) {
        return diaryMapper.selectTodayTop(lastDiaryId, size);
    }

    public int selectLastIdInCoinBank(int memberId, int coinBankId) {
        return diaryMapper.selectLastIdInCoinBank(memberId, coinBankId);
    }

    public int selectDailyLastId(String writeDate) {
        return diaryMapper.selectDailyLastId(writeDate);
    }

    public int selectLastIdInMonth(int memberId, String writeDate) {
        return diaryMapper.selectLastIdInMonth(memberId, writeDate);
    }

    public int selectTodayLastId() {
        return diaryMapper.selectTodayLastId();
    }

    public List<DiaryFileVO> selectDiaryDetail(int diaryId) {
        return diaryMapper.selectDiaryDetail(diaryId);
    }

    public int selectLastId(){
        return diaryMapper.selectLastId();
    }

    public void deleteDiary(int diaryId, int memberId) {
        diaryMapper.deleteDiary(diaryId, memberId);
    }

    public void updateDiary(DiaryVO diaryVO) {
        diaryMapper.updateDiary(diaryVO);
    }

    public void deleteFileInDiary(int fileId){
        diaryMapper.deleteFileInDiary(fileId);
    }

    public int selectMaxFileNo(int diaryId){
        return diaryMapper.selectMaxFileNo(diaryId);
    }

    public List<DiaryVO> bambooGrove(int diaryId, int size) {
        return diaryMapper.bambooGrove(diaryId, size);
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


    public int checkFileInInterimDiary(int diaryId, int fileId) {
        return diaryMapper.checkFileInInterimDiary(diaryId, fileId);
    }

    public int selectInterimLastId(int memberId) {
        return diaryMapper.selectInterimLastId(memberId);
    }

    public int checkInterimDiaryId(int diaryId) {
        return diaryMapper.checkInterimDiaryId(diaryId);
    }

    public int checkInterimDiaryMemberId(int diaryId, int memberId) {
        return diaryMapper.checkInterimDiaryMemberId(diaryId, memberId);
    }

    public int insertInterimDiary(DiaryVO diaryVO) {
        return diaryMapper.insertInterimDiary(diaryVO);
    }

    public void insertInterimDiaryFile(int diaryId, int file_id, int file_no) {
        diaryMapper.insertInterimDiaryFile(diaryId, file_id, file_no);
    }

    public List<DiaryFileVO> selectInterimDiaryDetail(int diaryId){
        return diaryMapper.selectInterimDiaryDetail(diaryId);
    }

    public List<DiaryVO> selectInterimDiaryList(int memberId, int lastDiaryId, int size) {
        return diaryMapper.selectInterimDiaryList(memberId, lastDiaryId, size);
    }

    public int countInterimDiary(int memberId) {
        return diaryMapper.countInterimDiary(memberId);
    }

    public List<Integer> selectFileInInterimDiary(int diaryId) {
        return diaryMapper.selectFileInInterimDiary(diaryId);
    }

    public void deleteInterimDiary(int diaryId) {
        diaryMapper.deleteInterimDiary(diaryId);
    }

    public void deleteInterimFile(int fileId) {
        diaryMapper.deleteInterimFile(fileId);
    }

    public void updateInterimDiary(DiaryVO diaryVO) {
        diaryMapper.updateInterimDiary(diaryVO);
    }

    public void deleteFileInInterimDiary(int fileId) {
        diaryMapper.deleteFileInInterimDiary(fileId);
    }

    public int selectMaxInterimFileNo(int diaryId) {
        return diaryMapper.selectMaxInterimFileNo(diaryId);
    }

}
