package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.security.oauth.AuthToken;
import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.LogInDTO;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.utils.HeaderUtil;
import com.teamY.angryBox.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
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
    public ResponseEntity<String> memberRegister(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        MemberVO member = new MemberVO(email, nickname, encodedPassword);
        memberService.registerMember(member);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("jwtlogin")
    public ResponseEntity<ResponseMessage> login(@RequestBody LogInDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        MemberVO member = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        Map<String, Object> data = new HashMap<>();
        data.put("nickname", member.getNickname());
        data.put("email", member.getEmail());
        data.put("id", member.getId());

        AuthToken authToken = authTokenProvider.createAuthToken(member.getEmail(), new Date(new Date().getTime() + appProperties.getAuth().getTokenExpiry()), data);

        data.put("jwt", authToken.getToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderUtil.HEADER_AUTHORIZATION, "Bearer " + authToken.getToken());

        return new ResponseEntity<>(new ResponseDataMessage(true, "로그인 성공", "", data), httpHeaders, HttpStatus.OK);
    }
}
