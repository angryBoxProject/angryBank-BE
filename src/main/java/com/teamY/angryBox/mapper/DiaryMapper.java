package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int insertDiary(DiaryVO diary);

    void insertDiaryFile(int diaryId, int fileId, int fileNo);

    List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId);

    List<DiaryFileVO> selectDiaryDetail(int diaryId);

    List<DiaryVO> selectDiaryListInMonth(int memberId, int year, int month);

    int selectDiaryMemberId(int diaryId, int memberId);

    void deleteFileInDiary(int diaryId);

    void deleteDiary(int diaryId);

}
