package com.teamY.angryBox.repository;

import com.teamY.angryBox.dto.TopDiaryDTO;
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
        return diaryMapper.selectCoinBankMemberId(coinBankId, memberId, 0);
    }

    public int insertDiary(DiaryVO diary) {
        return diaryMapper.insertDiary(diary);
    }

    public void insertDiaryFile(int diaryId, int fileId, int fileNo) {
        diaryMapper.insertDiaryFile(diaryId, fileId, fileNo);
    }

    public List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size) {
        return diaryMapper.selectDiaryListInCoinBank(memberId, coinBankId, lastDiaryId, size);
    }

    public List<DiaryVO> selectDiaryListInMonth(int memberId, String date, int lastDiaryId, int size) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        return diaryMapper.selectDiaryListInMonth(memberId, year, month, lastDiaryId, size);
    }

    public List<DiaryVO> selectDailyTop(TopDiaryDTO topDiaryDTO) {
        return diaryMapper.selectDailyTop(topDiaryDTO);
    }

    public List<DiaryVO> selectTodayTop(int lastDiaryId) {
        return diaryMapper.selectTodayTop(lastDiaryId);
    }

    public int selectLastId() {
        return diaryMapper.selectLastId();
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
