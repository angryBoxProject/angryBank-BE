package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.CoinBankMapper;
import com.teamY.angryBox.vo.CoinBankVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CoinBankRepository {

    private final CoinBankMapper mapper;

    public void insertCoinBank(CoinBankVO coinBankVO){
        mapper.insertCoinBank(coinBankVO);
    }

    public void updateCoinBank(CoinBankVO coinBankVO){
        mapper.updateCoinBank(coinBankVO);
    }

    public CoinBankVO selectBankExpired(int id){
        return mapper.selectBankExpired(id);
    }

    public int expireCoinBank(int id) {
        return mapper.expireCoinBank(id);
    }
}
