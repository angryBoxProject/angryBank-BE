package com.teamY.angryBox.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatsMapper {

    int selectCoinBankLimit(int memberId, int coinBankId);

    List<Integer> selectAngryPhaseInMonth(int memberId, String writeDate);

    int selectAngryPhaseSumInMonth(int memberId, String writeDate);

    List<Integer> selectAngryPhaseInCoinBank(int memberId, int coinBankId);

    int selectAngryPhaseSumInCoinBank(int memberId, int coinBankId);

    List<Integer> selectWriteDay(int memberId, int writeYear, int writeMonth);


}
