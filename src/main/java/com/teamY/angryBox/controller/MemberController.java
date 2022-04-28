package com.teamY.angryBox.controller;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.security.oauth.AuthToken;
import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.LogInDTO;
import com.teamY.angryBox.dto.RegisterMemberDTO;
import com.teamY.angryBox.dto.ResponseDataMessage;
import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.service.MailService;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.service.ProfileService;
import com.teamY.angryBox.utils.CookieUtil;
import com.teamY.angryBox.utils.HeaderUtil;
import com.teamY.angryBox.vo.MemberVO;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.AddressException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private final MailService mailService;
    private final ProfileService profileService;



    //oauth 회원가입한 회원은 비번 변경 불가

    //private final SimpMessagingTemplate template; // 특정 broker 로 메세지 전달

    @GetMapping("/")
    public String helloworld(){
        //template.convertAndSend("/sub/topic/bamboo", "hellooooo");
        return "service!!!";
    }

    @GetMapping("hello")
    public String helloDayea(){
        //log.info("프린시폴 : " + ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
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
    @GetMapping("user")
    public ResponseEntity<ResponseDataMessage> getMemberInfo() {
        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        memberVO = memberService.inquriyMember(memberVO.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("memberId", memberVO.getId());
        data.put("email", memberVO.getEmail());
        data.put("nickname", memberVO.getNickname());

        return new ResponseEntity<>(new ResponseDataMessage(true, "회원 정보 조회 성공", "", data), HttpStatus.OK);
    }
    @PostMapping("users")
    //public ResponseEntity<ResponseMessage> memberRegister(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
    public ResponseEntity<ResponseMessage> memberRegister(@Valid @RequestBody RegisterMemberDTO newUser) {
        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        MemberVO member = new MemberVO(newUser.getEmail(), newUser.getNickname(), encodedPassword, "basic");
        memberService.registerMember(member);

        // 프로필 생성
        profileService.registerProfile(member, 1); // 1은 서버에 올라가있는 기본 프로필 이미지

        return new ResponseEntity<>(new ResponseMessage(true, "회원가입 성공", ""), HttpStatus.OK);
    }

    @PostMapping("auth/login")
    public ResponseEntity<ResponseDataMessage> login(@Valid @RequestBody LogInDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> data = memberService.login(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderUtil.HEADER_AUTHORIZATION, HeaderUtil.TOKEN_PREFIX + data.get("access_token"));

        CookieUtil.deleteCookie(request, response, CookieUtil.REFRESH_TOKEN_COOKIE);
        CookieUtil.addCookie(response, CookieUtil.REFRESH_TOKEN_COOKIE, (String) data.get("refresh_token"), (int) appProperties.getAuth().getRefreshTokenExpiry() / 1000);
        return new ResponseEntity<>(new ResponseDataMessage(true, "로그인 성공", "", data), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("auth/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestHeader Map<String, Object> requestHeader, HttpServletRequest request, HttpServletResponse response) {
        String accessToken = ((String) requestHeader.get(HeaderUtil.HEADER_AUTHORIZATION)).substring(7);

        String refreshToken = CookieUtil.getCookie(request, CookieUtil.REFRESH_TOKEN_COOKIE)
                .map(Cookie::getValue)
                .orElse((null));


        if(memberService.isLogout(accessToken)) {
            return new ResponseEntity<>(new ResponseMessage(false, "이미 로그아웃 된 사용자", "이미 로그아웃 된 사용자"), HttpStatus.BAD_REQUEST);
        } else {
            memberService.logout(accessToken, authTokenProvider.getTokenExpire(accessToken), refreshToken);
            CookieUtil.deleteCookie(request, response, CookieUtil.REFRESH_TOKEN_COOKIE);
            return new ResponseEntity<>(new ResponseMessage(true, "로그아웃 성공", ""), HttpStatus.OK);
        }
    }

    @PostMapping("auth/refresh")
    public ResponseEntity<ResponseDataMessage> refresh(@RequestHeader Map<String, Object> requestHeader, HttpServletRequest request, HttpServletResponse response) {

        String accessToken = ((String) requestHeader.get(HeaderUtil.HEADER_AUTHORIZATION)).substring(7);
        String refreshToken = CookieUtil.getCookie(request, CookieUtil.REFRESH_TOKEN_COOKIE)
                .map(Cookie::getValue)
                .orElse((null));
        Map<String, Object> data = memberService.refresh(accessToken, refreshToken);

        return new ResponseEntity<>(new ResponseDataMessage(true, "access 토큰 재발급 성공", "", data), HttpStatus.OK);

    }


    @PutMapping("users")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody Map<String, String> passwords) {
        //log.info(passwords.toString());

        MemberVO memberVO = ((MemberPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        memberService.changePassword(memberVO.getId(), memberVO.getEmail(), passwords);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, "비밀번호 변경 성공", ""), HttpStatus.OK);
    }

    @PostMapping("mail/{email}")
    public ResponseEntity<ResponseMessage> sendMail(@PathVariable String email) throws AddressException {
        log.info("컨트롤러 진입");
        mailService.sendMail(email);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, "메일 전송 성공", ""), HttpStatus.OK);
    }


}
