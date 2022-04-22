package com.teamY.angryBox.service;

import com.teamY.angryBox.repository.StatsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class StatsService {

    private final StatsRepository statsRepository;

//    public Map<String, Object> selectAngryPhase(int memberId, String date) {
//        int writeYear = Integer.parseInt(date.substring(0, 4));
//        int writeMonth = Integer.parseInt(date.substring(5, 7));
//
//        List<Integer> apList = statsRepository.selectAngryPhase(memberId, writeYear, writeMonth);
//        Map<String, Object> apMap = new LinkedHashMap<>();
//        for(int i = 0; i < apList.size(); i++) {
//            apMap.put("apCountList", apList);
//        }
//        return apMap;
//    }

    public List<Integer> selectAngryPhase(int memberId, String date) {
        int writeYear = Integer.parseInt(date.substring(0, 4));
        int writeMonth = Integer.parseInt(date.substring(5, 7));

        List<Integer> apList = statsRepository.selectAngryPhase(memberId, writeYear, writeMonth);

        return apList;
    }

    //% 구하기
    public List<Double> selectAngryPhasePer(int memberId, String date) {
        int writeYear = Integer.parseInt(date.substring(0, 4));
        int writeMonth = Integer.parseInt(date.substring(5, 7));
        int n = 5;

        List<Double> apPerList = new ArrayList<>();

        double sum = statsRepository.selectAngryPhaseSum(memberId, writeYear, writeMonth);
        List<Integer> apList = this.selectAngryPhase(memberId, date);
        for(int i = 0; i < apList.size(); i++) {
            //apPerList.add(Math.round((apList.get(i) * n / sum) * 100) / 100);
            double m = ((apList.get(i) * n) / sum) * 100; // 퍼센트
            System.out.println("퍼센트 : " + m);
            m = Math.floor(m * 10) /  10.0;
            System.out.println("floor : " + m);
            n += 5;
        }

        return apPerList;

    }
    
    
    //몇 회 남았는지
    






}
