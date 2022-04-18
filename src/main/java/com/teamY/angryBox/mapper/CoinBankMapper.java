package com.teamY.angryBox.mapper;

import com.teamY.angryBox.vo.CoinBankVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoinBankMapper {
    void insertCoinBank(CoinBankVO coinBankVO);

    void updateCoinBank(CoinBankVO coinBankVO);

    CoinBankVO selectBankExpired(int id);
}
