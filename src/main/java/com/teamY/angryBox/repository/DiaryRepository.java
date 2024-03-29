package com.teamY.angryBox.repository;


import com.teamY.angryBox.dto.DiaryDTO;
import com.teamY.angryBox.dto.InterimDiaryDTO;
import com.teamY.angryBox.mapper.DiaryMapper;
import com.teamY.angryBox.vo.*;
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

    public DiaryVO insertDiary(DiaryDTO diaryDTO) {
        return diaryMapper.insertDiary(diaryDTO);
    }

    public void insertDiaryFile(int diaryId, int fileId, int fileNo) {
        diaryMapper.insertDiaryFile(diaryId, fileId, fileNo);
    }

    public String selectDiaryNoInNft(int diaryId) {
        return diaryMapper.selectDiaryNoInNft(diaryId);
    }

    public int selectDiaryMemberId(int diaryId) {
        return diaryMapper.selectDiaryMemberId(diaryId);
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

    public DiaryVO selectDiaryDetail(int diaryId) {
        return diaryMapper.selectDiaryDetail(diaryId);
    }

    public List<DiaryFileVO> selectFileInDiary(int diaryId) {
        return diaryMapper.selectFileInDiary(diaryId);
    }

    public void updateViewCount(int diaryId) {
        diaryMapper.updateViewCount(diaryId);
    }

    public int selectLastId() {
        return diaryMapper.selectLastId();
    }

    public void deleteDiary(int diaryId, int memberId) {
        diaryMapper.deleteDiary(diaryId, memberId);
    }

    public void updateDiary(DiaryDTO diaryDTO) {
        diaryMapper.updateDiary(diaryDTO);
    }

    public void deleteFileInDiary(int fileId) {
        diaryMapper.deleteFileInDiary(fileId);
    }

    public int selectMaxFileNo(int diaryId) {
        return diaryMapper.selectMaxFileNo(diaryId);
    }

    public List<DiaryVO> bambooGrove(int diaryId, int size, String filter) {
        return diaryMapper.bambooGrove(diaryId, size, filter);
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

    public int insertInterimDiary(InterimDiaryDTO interimDiaryDTO) {
        return diaryMapper.insertInterimDiary(interimDiaryDTO);
    }

    public void insertInterimDiaryFile(int diaryId, int file_id, int file_no) {
        diaryMapper.insertInterimDiaryFile(diaryId, file_id, file_no);
    }

    public InterimDiaryVO selectInterimDiaryDetail(int diaryId) {
        return diaryMapper.selectInterimDiaryDetail(diaryId);
    }

    public List<DiaryFileVO> selectFileInInterimDiary(int diaryId) {
        return diaryMapper.selectFileInInterimDiary(diaryId);
    }

    public List<InterimDiaryVO> selectInterimDiaryList(int memberId, int lastDiaryId, int size) {
        return diaryMapper.selectInterimDiaryList(memberId, lastDiaryId, size);
    }

    public int countInterimDiary(int memberId) {
        return diaryMapper.countInterimDiary(memberId);
    }

    public List<Integer> selectFileIdInInterimDiary(int diaryId) {
        return diaryMapper.selectFileIdInInterimDiary(diaryId);
    }

    public void deleteInterimDiary(int diaryId) {
        diaryMapper.deleteInterimDiary(diaryId);
    }

    public void deleteInterimFile(int fileId) {
        diaryMapper.deleteInterimFile(fileId);
    }

    public void updateInterimDiary(InterimDiaryDTO interimDiaryDTO) {
        diaryMapper.updateInterimDiary(interimDiaryDTO);
    }

    public void deleteFileInInterimDiary(int fileId) {
        diaryMapper.deleteFileInInterimDiary(fileId);
    }

    public int selectMaxInterimFileNo(int diaryId) {
        return diaryMapper.selectMaxInterimFileNo(diaryId);
    }

    public int selectGalleryLastId() {
        return diaryMapper.selectGalleryLastId();
    }
    public List<GalleryDiaryVO> selectGallery(int lastDiaryId, int size) {
        return diaryMapper.selectGallery(lastDiaryId, size);
    }

    public DiaryVO checkDiary(int diaryId) {
        return diaryMapper.checkDiary(diaryId);
    }

    public int selectDiaryCountInCoinBank(int coinBankId, int memberId) {
        return diaryMapper.selectDiaryCountInCoinBank(coinBankId, memberId);
    }
    public int selectTodackCountInCoinBank(int coinBankId, int memberId) {
        return diaryMapper.selectTodackCountInCoinBank(coinBankId, memberId);
    }


}
