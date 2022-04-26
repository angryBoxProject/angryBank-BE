package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.StatsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class StatsRepository {

    private final StatsMapper statsMapper;

    public int selectCoinBankLimit(int memberId, int coinBankId) {
        return statsMapper.selectCoinBankLimit(memberId, coinBankId);
    }

    public List<Integer> selectAngryPhase(int memberId, int writeYear, int writeMonth) {
        return statsMapper.selectAngryPhase(memberId, writeYear, writeMonth);
    }

    public int selectAngryPhaseSum(int memberId, int writeYear, int writeMonth) {
        return statsMapper.selectAngryPhaseSum(memberId, writeYear, writeMonth);
    }






    public List<Integer> selectWriteDay(int memberId, int writeYear, int writeMonth) {
        return statsMapper.selectWriteDay(memberId, writeYear, writeMonth);
    }


}
