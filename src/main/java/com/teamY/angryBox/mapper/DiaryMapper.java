package com.teamY.angryBox.mapper;


import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int checkAngryId(int angryPhaseId);

    int checkCoinBankMemberId(int coinBankId, int memberId);

    int checkCoinBankExpired(int coinBankId, int memberId, int expired);

    DiaryVO insertDiary(DiaryVO diary);

    void insertDiaryFile(int diaryId, int fileId, int fileNo);

    int checkDiaryId(int diaryId);

    int checkDiaryMemberId(int diaryId, int memberId);

    int checkFileInDiary(int diaryId, int fileId);

    int checkIsPublic(int diaryId);

    int checkDailyTopDiary(String writeDate);

    List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size);

    List<DiaryVO> selectDiaryListInMonth(int memberId, String writeDate, int lastDiaryId, int size);

    List<DiaryVO> selectDailyTop(String writeDate, int lastDiaryId, int size);

    List<DiaryVO> selectTodayTop(int lastDiaryId, int size);

    List<DiaryFileVO> selectDiaryDetail(int diaryId);

    int selectLastId();

    int selectLastIdInCoinBank(int memberId, int coinBankId);

    int selectDailyLastId(String writeDate);

    int selectLastIdInMonth(int memberId, String writeDate);

    int selectTodayLastId();

    void deleteDiary(int diaryId, int memberId);

    void updateDiary(DiaryVO diaryVO);

    void deleteFileInDiary(int fileId);

    int selectMaxFileNo(int diaryId);

    List<DiaryVO> bambooGrove(int lastDiaryId, int size);

    //저금통 내 다이어리 개수
    int selectDiaryCountInCoinBank(int diaryId, int memberId);

    //저금통 별 토댝 총 개수
    int selectTodackCountInCoinBank(int diaryId, int memberId);


    List<DiaryVO> searchDiary(String searchKeyword, int lastDiaryId, int size);
}
