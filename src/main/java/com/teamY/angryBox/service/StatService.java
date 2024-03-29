package com.teamY.angryBox.service;

import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.repository.CoinBankRepository;
import com.teamY.angryBox.repository.DiaryRepository;
import com.teamY.angryBox.repository.StatRepository;
import com.teamY.angryBox.vo.CoinBankVO;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatService {

    private final StatRepository statRepository;
    private final CoinBankRepository coinBankRepository;

    //월별 분노
    public Map<String, Object> getAngryPhaseInMonth(int memberId, String writeDate) {
        List<Integer> apList = statRepository.selectAngryPhaseInMonth(memberId, writeDate);
        double sum = statRepository.selectAngryPhaseSumInMonth(memberId, writeDate);

        Map<String, Object> data = getAngryPhasePer(apList, sum);

        return data;
    }

    //저금통별 분노
    public Map<String, Object> getAngryPhaseInCoinBank(int memberId, int coinBankId) {

        CoinBankVO coinBankVO = coinBankRepository.selectById(coinBankId);
        if (coinBankVO == null || coinBankVO.getMemberId() != memberId) {
            throw new InvalidRequestException("적금 번호 확인 필요");
        }

        List<Integer> apList = statRepository.selectAngryPhaseInCoinBank(memberId, coinBankId);
        double sum = statRepository.selectAngryPhaseSumInCoinBank(memberId, coinBankId);
        Map<String, Object> data = getAngryPhasePer(apList, sum);

        return data;
    }

    public Map<String, Object> getAngryPhasePer(List<Integer> apList, double sum) {
        List<Double> apPerList = new ArrayList<>();
        int n = 5;

        for(int i = 0; i < apList.size(); i++) {
            if(sum == 0) {
                apPerList.add(0.0);
            } else {
                double m = ((apList.get(i) * n) / sum) * 100; // 퍼센트
                m = Math.floor(m * 10) / 10.0;
                n += 5;
                apPerList.add(m);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("apList", apList);
        data.put("apPerList", apPerList);
        if(sum == 0) {
            data.put("zero", null);
        }

        return data;

    }







}
