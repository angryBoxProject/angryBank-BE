package com.teamY.angryBox.repository;

import com.teamY.angryBox.mapper.CoinBankMapper;
import com.teamY.angryBox.vo.BankStatCalenderVO;
import com.teamY.angryBox.vo.CoinBankVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    public int selectCoinBankSum(int memberId, int bankId){
        return mapper.selectCoinBankSum(memberId, bankId);
    }

    public CoinBankVO selectById(int id){
        return mapper.selectById(id);
    }

    public int[] selectAngryPhase(){
        return mapper.selectAngryPhase();
    }

    public Map<String, Object> selectBankStatProfile(int memberId) {
        return mapper.selectBankStatProfile(memberId);
    }

    public List<BankStatCalenderVO> selectBankStatCalenderByMonth(String select) {
        return mapper.selectBankStatCalenderByMonth(select);
    }

    public List<BankStatCalenderVO> selectBankStatCalenderByMonthAndBank(String select, int coinBankId) {
        return mapper.selectBankStatCalenderByMonthAndBank(select, coinBankId);
    }
}
