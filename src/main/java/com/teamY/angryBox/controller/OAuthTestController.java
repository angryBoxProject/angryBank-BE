package com.teamY.angryBox.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping("login/oauth2/code/")
@AllArgsConstructor
@RestController
public class OAuthTestController {

    private final OAuthController oAuthController;

    @GetMapping("kakao")
    public void getKakaoAuthCode(@RequestParam String code, HttpServletRequest request, HttpServletResponse response){
        log.info("kakao code : " + code);
        oAuthController.kakaoLogin(code, request, response);
    }

    @GetMapping("google")
    public void getGoogleAuthCode(@RequestParam String code){
        log.info("google code : " + code);
        //oAuthController.googleLogin(code);
    }
}
