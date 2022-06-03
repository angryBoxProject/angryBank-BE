package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.BankStatCalenderVO;
import com.teamY.angryBox.vo.CoinBankVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CoinBankMapper {
    void insertCoinBank(CoinBankVO coinBankVO);

    void updateCoinBank(CoinBankVO coinBankVO);

    CoinBankVO selectBankExpired(int id);

    int expireCoinBank(int id);

    int selectCoinBankSum(int memberId, int bankId);

    CoinBankVO selectById(int id);

    int[] selectAngryPhase();

    Map<String, Object> selectBankStatProfile(int memberId);

    List<BankStatCalenderVO> selectBankStatCalenderByMonth(String select);
    List<BankStatCalenderVO> selectBankStatCalenderByMonthAndBank(String select, int coinBankId);

    List<Map<String, Object>> selectAllBank(int memberId);
    int selectUnExpiredCoinBank(int memberId);

}
