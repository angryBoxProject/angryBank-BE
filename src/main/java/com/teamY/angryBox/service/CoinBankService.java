package com.teamY.angryBox.service;

import com.teamY.angryBox.dto.NewCoinBankDTO;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.error.customException.SQLInquiryException;
import com.teamY.angryBox.repository.CoinBankRepository;
import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.vo.CoinBankVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoinBankService {

    private final CoinBankRepository coinBankRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createCoinBank(NewCoinBankDTO bank, int memberId){

        // 멤버에게 이미 할당 된 저금통이 있는지 없는지 검사 추가해야됨
        CoinBankVO bankVO = new CoinBankVO(memberId, bank.getName(), bank.getMemo(), bank.getAngryLimit(), bank.getReward());
        coinBankRepository.insertCoinBank(bankVO);
        memberRepository.updateCoinBankId(bankVO.getMemberId(), bankVO.getId());
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

    @Transactional
    public void expireCoinBank(int id, int memberId) {
        if(id < 1)
            throw new InvalidRequestException("유효하지 않은 적금 ID");

        int curBankId = memberRepository.selectMemberCurBank(memberId);
        if(curBankId == 0 || curBankId != id)
            throw new InvalidRequestException("현재 사용 중인 적금이 아님");

        if(coinBankRepository.expireCoinBank(id) == 0)
            throw new SQLInquiryException("해당 ID 적금 조회 실패");

        memberRepository.updateCoinBankIdToNull(memberId);
    }

    public Map<String, Object> inquiryCoinBank(int memberId) {

        Map<String, Object> data = new HashMap<>();
        int curBankId = memberRepository.selectMemberCurBank(memberId);

        if(curBankId == 0)
            throw new InvalidRequestException("현재 사용 중인 적금 없음");

        CoinBankVO coinBankVO = coinBankRepository.selectById(curBankId);

        if(coinBankVO == null)
            throw new InvalidRequestException("존재하지 않는 적금");


        int coinBankSum = coinBankRepository.selectCoinBankSum(memberId, coinBankVO.getId());

        int[] angryPhaseArr = coinBankRepository.selectAngryPhase();
        List<Integer> remaingNum = new ArrayList<>();

        int left = coinBankVO.getAngryLimit() - coinBankSum;
        boolean canCrush = false;

        if(left <= 0)
            canCrush = true;
        else {
            for (int phase : angryPhaseArr)
                remaingNum.add(((left - 1) / phase) + 1);
        }



        data.put("id", coinBankVO.getId());
        data.put("name", coinBankVO.getName());
        data.put("angryLimit", coinBankVO.getAngryLimit());
        data.put("reward", coinBankVO.getReward());
        data.put("canCrush", canCrush);
        data.put("remainingDiaryNum", remaingNum);

        return data;
    }

}
