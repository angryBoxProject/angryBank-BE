package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.CoinBankVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoinBankMapper {
    void insertCoinBank(CoinBankVO coinBankVO);

    void updateCoinBank(CoinBankVO coinBankVO);

    CoinBankVO selectBankExpired(int id);

    int expireCoinBank(int id);

    int selectCoinBankSum(int memberId, int bankId);

    CoinBankVO selectById(int id);

    int[] selectAngryPhase();
}
