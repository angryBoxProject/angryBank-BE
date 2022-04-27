package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.StatsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class StatRepository {

    private final StatsMapper statsMapper;

    public int selectCoinBankLimit(int memberId, int coinBankId) {
        return statsMapper.selectCoinBankLimit(memberId, coinBankId);
    }

    public List<Integer> selectAngryPhaseInMonth(int memberId, String writeDate) {
        return statsMapper.selectAngryPhaseInMonth(memberId, writeDate);
    }

    public int selectAngryPhaseSumInMonth(int memberId, String writeDate) {
        return statsMapper.selectAngryPhaseSumInMonth(memberId, writeDate);
    }

    public List<Integer> selectAngryPhaseInCoinBank(int memberId, int coinBankId) {
        return statsMapper.selectAngryPhaseInCoinBank(memberId, coinBankId);
    }

    public int selectAngryPhaseSumInCoinBank(int memberId, int coinBankId) {
        return statsMapper.selectAngryPhaseSumInCoinBank(memberId, coinBankId);
    }






    public List<Integer> selectWriteDay(int memberId, int writeYear, int writeMonth) {
        return statsMapper.selectWriteDay(memberId, writeYear, writeMonth);
    }


}
