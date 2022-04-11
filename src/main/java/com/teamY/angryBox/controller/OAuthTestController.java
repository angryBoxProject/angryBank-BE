package com.teamY.angryBox.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("login/oauth2/code/")
@RestController
public class OAuthTestController {

    @GetMapping("kakao")
    public void getKakaoAuthCode(@RequestParam String code){
        log.info("kakao code : " + code);
    }
}
