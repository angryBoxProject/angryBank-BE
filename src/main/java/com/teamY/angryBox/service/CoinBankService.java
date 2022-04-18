package com.teamY.angryBox.service;

import com.teamY.angryBox.dto.NewCoinBankDTO;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.error.customException.SQLInquiryException;
import com.teamY.angryBox.repository.CoinBankRepository;
import com.teamY.angryBox.vo.CoinBankVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoinBankService {

    private final CoinBankRepository coinBankRepository;

    public void createCoinBank(NewCoinBankDTO bank, int memberId){

        CoinBankVO bankVO = new CoinBankVO(memberId, bank.getName(), bank.getMemo(), bank.getAngryLimit(), bank.getReward());
        coinBankRepository.insertCoinBank(bankVO);
    }

    public void modifyCoinBank(NewCoinBankDTO bank, int memberId) {

        CoinBankVO coinBankVO = coinBankRepository.selectBankExpired(bank.getId());
        if(coinBankVO == null)
            throw new SQLInquiryException("해당 ID 적금 조회 실패");
        else if(coinBankVO.isExpired())
            throw new InvalidRequestException("해당 적금은 이미 만료되었음");
        else if(coinBankVO.getMemberId() != memberId)
            throw new InvalidRequestException("해당 사용자의 적금이 아님");

        CoinBankVO bankVO = new CoinBankVO(bank.getId(), memberId, bank.getName(), bank.getMemo(), bank.getAngryLimit(), bank.getReward());
        log.info("수정될 저금통 내용 : " + bankVO);
        coinBankRepository.updateCoinBank(bankVO);
    }
}
