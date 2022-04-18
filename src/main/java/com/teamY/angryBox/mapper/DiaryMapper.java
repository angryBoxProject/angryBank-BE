package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int selectAngryId(int angryPhaseId);

    int insertDiary(DiaryVO diary);

    void insertDiaryFile(int diaryId, int fileId, int fileNo);

    List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId);

    List<DiaryVO> selectDiaryListInMonth(int memberId, int year, int month);

    List<DiaryFileVO> selectDiaryDetail(int diaryId);

    int selectDiaryMemberId(int diaryId, int memberId);

    void deleteDiary(int diaryId, int memberId);

    void updateDiary(DiaryVO diaryVO);

    void deleteFileInDiary(int fileId);

    int selectMaxFileNo(int diaryIn);


}
