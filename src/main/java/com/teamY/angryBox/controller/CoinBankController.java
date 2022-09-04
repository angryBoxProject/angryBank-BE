package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.NewCoinBankDTO;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.CoinBankService;
import com.teamY.angryBox.vo.CoinBankVO;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.ObjectArrayPool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CoinBankController {

    private final CoinBankService coinBankService;

    @GetMapping("bank")
    public ResponseEntity<ResponseDataMessage> inquiryCoinBank() {
        MemberVO memberVO = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        return new ResponseEntity<>(new ResponseDataMessage(true, "저금통 조회 성공", "", coinBankService.inquiryCoinBank(memberVO.getId())), HttpStatus.OK);
    }

    @PostMapping("bank")
    public ResponseEntity<ResponseMessage> createCoinBank(@Valid @RequestBody NewCoinBankDTO bank) {
        MemberVO memberVO = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        coinBankService.createCoinBank(bank, memberVO.getId());

        return new ResponseEntity<>(new ResponseMessage(true, "적금 생성 성공", ""), HttpStatus.OK);
    }

    @PutMapping("bank")
    public ResponseEntity<ResponseMessage> modifyCoinBank(@Valid @RequestBody NewCoinBankDTO bank) {

        MemberVO memberVO = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        coinBankService.modifyCoinBank(bank, memberVO.getId());

        return new ResponseEntity<>(new ResponseMessage(true, "적금 수정 성공", ""), HttpStatus.OK);
    }

    @PutMapping("expired-bank")
    public ResponseEntity<ResponseMessage> expireCoinBank(@RequestParam int id) {

        MemberVO memberVO = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        coinBankService.expireCoinBank(id, memberVO.getId());

        return new ResponseEntity<>(new ResponseMessage(true, "적금 깨기 성공", ""), HttpStatus.OK);
    }

    @GetMapping("banks")
    public ResponseEntity<ResponseDataMessage> getAllBanks() {
        MemberVO memberVO = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();


        return new ResponseEntity<>(new ResponseDataMessage(true, "모든 저금통 조회 성공", "", coinBankService.selectAllBank(memberVO.getId())), HttpStatus.OK);
    }
}
