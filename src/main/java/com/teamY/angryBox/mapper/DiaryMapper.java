package com.teamY.angryBox.mapper;

import com.teamY.angryBox.dto.TopDiaryDTO;
import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int selectAngryId(int angryPhaseId);

    int selectCoinBankMemberId(int coinBankId, int memberId, int expired);

    int insertDiary(DiaryVO diary);

    void insertDiaryFile(int diaryId, int fileId, int fileNo);

    List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId, int lastDiaryId, int size);

    List<DiaryVO> selectDiaryListInMonth(int memberId, int writeYear, int writeMonth, int lastDiaryId, int size);

    List<DiaryVO> selectDailyTop(TopDiaryDTO topDiaryDTO);

    List<DiaryVO> selectTodayTop(int lastDiaryId);

    List<DiaryFileVO> selectDiaryDetail(int diaryId);

    int selectLastId();

    int selectDiaryMemberId(int diaryId, int memberId);

    void deleteDiary(int diaryId, int memberId);

    void updateDiary(DiaryVO diaryVO);

    void deleteFileInDiary(int fileId);

    int selectMaxFileNo(int diaryId);

    List<DiaryVO> bambooGrove(int lastDiaryId, int isPublic, int size);

    //저금통 내 다이어리 개수
    int selectDiaryCountInCoinBank(int diaryId, int memberId);

    //저금통 별 토댝 총 개수
    int selectTodackCountInCoinBank(int diaryId, int memberId);


    List<DiaryVO> searchDiary(String searchKeyword, int lastDiaryId, int size);
}
