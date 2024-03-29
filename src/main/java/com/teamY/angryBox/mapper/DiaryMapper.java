package com.teamY.angryBox.mapper;


import com.teamY.angryBox.vo.*;
import com.teamY.angryBox.dto.DiaryDTO;
//import com.teamY.angryBox.dto.DiaryFileDTO;
import com.teamY.angryBox.dto.InterimDiaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int checkAngryId(int angryPhaseId);

    DiaryVO insertDiary(DiaryDTO diaryDTO);

    void insertDiaryFile(int diaryId, int fileId, int fileNo);

    String selectDiaryNoInNft(int diaryId);

    int selectDiaryMemberId(int diaryId);

    int checkFileInDiary(int diaryId, int fileId);

    List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size);

    List<DiaryVO> selectDiaryListInMonth(int memberId, String writeDate, int lastDiaryId, int size);

    List<DiaryVO> selectDailyTop(String writeDate, int lastDiaryId, int size);

    List<DiaryVO> selectTodayTop(int lastDiaryId, int size);

    //List<DiaryFileVO> selectDiaryDetail(int diaryId);
    DiaryVO selectDiaryDetail(int diaryId);

    List<DiaryFileVO> selectFileInDiary(int diaryId);

    void updateViewCount(int diaryId);

    int selectLastId();

    int selectLastIdInCoinBank(int memberId, int coinBankId);

    int selectDailyLastId(String writeDate);

    int selectLastIdInMonth(int memberId, String writeDate);

    int selectTodayLastId();

    void deleteDiary(int diaryId, int memberId);

    void updateDiary(DiaryDTO diaryDTO);

    void deleteFileInDiary(int fileId);

    int selectMaxFileNo(int diaryId);

    List<DiaryVO> bambooGrove(int lastDiaryId, int size, String filter);

    List<DiaryVO> bambooTest(String test);


    List<DiaryVO> searchDiary(String searchKeyword, int lastDiaryId, int size);


    //임시저장 관련
    int checkFileInInterimDiary(int diaryId, int fileId);

    int selectInterimLastId(int memberId);

    int checkInterimDiaryId(int diaryId);

    int checkInterimDiaryMemberId(int diaryId, int memberId);

    int insertInterimDiary(InterimDiaryDTO interimDiaryDTO);

    void insertInterimDiaryFile(int diaryId, int fileId, int fileNo);

    InterimDiaryVO selectInterimDiaryDetail(int diaryId);

    List<DiaryFileVO> selectFileInInterimDiary(int diaryId);

    List<InterimDiaryVO> selectInterimDiaryList(int memberId, int lastDiaryId, int size);

    int countInterimDiary(int memberId);

    List<Integer> selectFileIdInInterimDiary(int diaryId);

    void deleteInterimDiary(int diaryId);

    void deleteInterimFile(int fileId);

    void updateInterimDiary(InterimDiaryDTO interimDiaryDTO);

    void deleteFileInInterimDiary(int fileId);

    int selectMaxInterimFileNo(int diaryId);

    int selectGalleryLastId();
    List<GalleryDiaryVO> selectGallery(int lastDiaryId, int size);
    DiaryVO checkDiary(int diaryId);

    int selectDiaryCountInCoinBank(int coinBankId, int memberId);
    int selectTodackCountInCoinBank(int coinBankId, int memberId);

}
