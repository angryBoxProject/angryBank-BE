package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.TodackMapper;
import com.teamY.angryBox.vo.TodackVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@AllArgsConstructor
@Repository
public class TodackRepository {

    private final TodackMapper todackMapper;

    public void upTodackCount(TodackVO todack) {
        todackMapper.upTodackCount(todack);
    }

    public void downTodackCount(TodackVO todack) {
        todackMapper.downTodackCount(todack);
    }

    public int checkSendTodack(int memberId, int diaryId, int send) {
        return todackMapper.checkSendTodack(memberId, diaryId, send);
    }
}
