package com.teamY.angryBox.controller;


import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.service.StatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class StatController {

    private final StatService statService;

    @GetMapping("bank/month/{date}")
    public ResponseEntity<ResponseDataMessage> monthStat(@PathVariable String date) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = statService.getAngryPhaseInMonth(memberId, date);
        if(data.containsKey("zero")) {
            data.remove("zero");
            return new ResponseEntity<>(new ResponseDataMessage(true, "월별 통계 조회 성공(해당 월에 작성한 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "월별 통계 조회 성공", "", data), HttpStatus.OK);
        }
    }

    @GetMapping("bank/coinBank/{coinBankId}")
    public ResponseEntity<ResponseDataMessage> coinBankStat(@PathVariable int coinBankId) {

        int memberId = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO().getId();

        Map<String, Object> data = statService.getAngryPhaseInCoinBank(memberId, coinBankId);
        if(data.containsKey("zero")) {
            data.remove("zero");
            return new ResponseEntity<>(new ResponseDataMessage(true, "적금별 통계 조회 성공(해당 적금에 작성한 다이어리 없음)", "", data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDataMessage(true, "적금별 통계 조회 성공", "", data), HttpStatus.OK);
        }
    }
}
