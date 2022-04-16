package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.dto.LogInDTO;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.service.ProfileService;
import com.teamY.angryBox.utils.HeaderUtil;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final AppProperties appProperties;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final AuthTokenProvider authTokenProvider;

    private final ProfileService profileService;

    //oauth 회원가입한 회원은 비번 변경 불가

    @GetMapping("/")
    public String helloworld(){
        return "service!!!";
    }

    @GetMapping("hello")
    public String helloDayea(){
        return "hello Yang Dayea";
    }

    //https://kauth.kakao.com/oauth/authorize?client_id=c23e69e54e1be94497eeed61a74f451e&redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code
    //https://kauth.kakao.com/oauth/authorize?client_id=c23e69e54e1be94497eeed61a74f451e&redirect_uri=http://localhost:8080/login/code/kakao&response_type=code
    //@ResponseBody
//    @GetMapping("login/code/kakao")
//    public void kakaoCallback(@RequestParam String code) {
//        log.info("kakao code : " + code);
//    }

    @GetMapping("test")
    public String test(@RequestHeader HttpHeaders headers){
        log.info("있냐고" + headers.get(HeaderUtil.HEADER_AUTHORIZATION));
        return "test";
    }
    @PostMapping("users")
    public ResponseEntity<ResponseMessage> memberRegister(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        MemberVO member = new MemberVO(email, nickname, encodedPassword, "basic");
        memberService.registerMember(member);

        // 프로필 생성
        profileService.registerProfile(member, 1); // 1은 서버에 올라가있는 기본 프로필 이미지

        return new ResponseEntity<>(new ResponseMessage(true, "회원가입 성공", ""), HttpStatus.OK);
    }

    @PostMapping("auth/login")
    public ResponseEntity<ResponseDataMessage> login(@RequestBody LogInDTO loginDTO) {

        Map<String, Object> data = memberService.login(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderUtil.HEADER_AUTHORIZATION, HeaderUtil.TOKEN_PREFIX + data.get("jwt"));

        return new ResponseEntity<>(new ResponseDataMessage(true, "로그인 성공", "", data), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("auth/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestHeader Map<String, Object> requestHeader) {
        String token = ((String) requestHeader.get(HeaderUtil.HEADER_AUTHORIZATION)).substring(7);

        if(memberService.isLogout(token)) {
            return new ResponseEntity<>(new ResponseMessage(false, "이미 로그아웃 된 사용자", "이미 로그아웃 된 사용자"), HttpStatus.BAD_REQUEST);
        } else {
            memberService.setLogoutToken(token, authTokenProvider.getTokenExpire(token));
            return new ResponseEntity<>(new ResponseMessage(true, "로그아웃 성공", ""), HttpStatus.OK);
        }
    }


}
