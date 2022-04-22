package com.teamY.angryBox.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatsMapper {

    int selectCoinBankLimit(int memberId, int coinBankId);

    List<Integer> selectAngryPhase(int memberId, int writeYear, int writeMonth);

    int selectAngryPhaseSum(int memberId, int writeYear, int writeMonth);

    List<Integer> selectWriteDay(int memberId, int writeYear, int writeMonth);


}
