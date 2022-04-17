package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.DiaryFileVO;
import com.teamY.angryBox.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int insertDiary(DiaryVO diary);

    void insertDiaryFile(int diaryId, int fileId);

    List<DiaryVO> selectDiaryListInCoinBank(int memberId, int coinBankId);

    List<DiaryFileVO> selectDiaryDetail(int diaryId);

}
