package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.TodackVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodackMapper {

    void upTodackCount(TodackVO todack);

    void downTodackCount(TodackVO todack);

    int checkSendTodack(int memberId, int diaryId, int send);


}
