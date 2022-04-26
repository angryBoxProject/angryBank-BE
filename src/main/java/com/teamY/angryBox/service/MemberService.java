package com.teamY.angryBox.service;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.security.oauth.AuthToken;
import com.teamY.angryBox.config.security.oauth.AuthTokenProvider;
import com.teamY.angryBox.config.security.oauth.MemberPrincipal;
import com.teamY.angryBox.dto.LogInDTO;
import com.teamY.angryBox.error.customException.CustomException;
import com.teamY.angryBox.error.customException.InvalidRefreshTokenException;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.error.customException.PasswordNotMatchesException;
import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.vo.MemberVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenProvider authTokenProvider;
    private final AppProperties appProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Map<String, Object> login(LogInDTO loginDTO){

        String password = memberRepository.findPassword(loginDTO.getEmail());

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), password)) {
            throw new PasswordNotMatchesException("PasswordNotMatchesException");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        if (!((MemberPrincipal) authentication.getPrincipal()).getRegisterType().equals("basic"))
            throw new BadCredentialsException("BadCredentialsException");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> data = createToken();

        String refreshToken = (String) data.get("refresh_token");
        String accessToken = (String) data.get("access_token");
        memberRepository.setRefreshToken(refreshToken, accessToken, authTokenProvider.getTokenExpire(refreshToken));

        return data;
    }

    public Map<String, Object> OAuthLogin(LogInDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createToken();
    }

    public Map<String, Object> createToken() {
        Map<String, Object> data = null;

        MemberVO member = ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVO();

        data = new HashMap<>();
        data.put("nickname", member.getNickname());
        data.put("email", member.getEmail());
        data.put("memberId", member.getId());

        //AuthToken accessToken = authTokenProvider.createAuthToken(member.getEmail(), new Date(new Date().getTime() + appProperties.getAuth().getTokenExpiry()), data);

        AuthToken accessToken = createAccessToken(member.getEmail(), data);
        AuthToken refreshToken = authTokenProvider.createAuthToken(new Date(new Date().getTime() + appProperties.getAuth().getRefreshTokenExpiry()));

        data.put("access_token", accessToken.getToken());
        data.put("refresh_token", refreshToken.getToken());

        return data;
    }

    public AuthToken createAccessToken(String email, Map<String, Object> data) {
        return authTokenProvider.createAuthToken(email, new Date(new Date().getTime() + appProperties.getAuth().getTokenExpiry()), data);
    }

    public void logout(String accessToken, long expire, String refreshToken) {

        // 로그아웃 access 토큰 레디스에 저장
        memberRepository.setLogoutToken(accessToken, expire);


        // 레디스의 refresh 토큰 삭제
        memberRepository.deleteRefreshToken(refreshToken);

    }

    public Map<String, Object> refresh(String accessToken, String refreshToken) throws CustomException {

        AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);
        AuthToken authAccessToken = authTokenProvider.convertAuthToken(accessToken);

        if(authRefreshToken.validateRefresh()) {
            String dbAccessToken = memberRepository.getTokenSet(refreshToken);

            if(accessToken.equals(dbAccessToken)) {
                Map<String, Object> data = new HashMap<>();
                Claims claims = authAccessToken.getExpireTokenClaims();

                log.info("claims : " + claims.toString());
                String newAccessToken = createAccessToken((String) claims.get("email"), claims).getToken();

                data.put("access_token", newAccessToken);

                memberRepository.setRefreshToken(refreshToken, newAccessToken, authTokenProvider.getTokenExpire(refreshToken));

                return data;
            }
            throw new InvalidRefreshTokenException("Access token 불일치");
        }

        return null;

    }
    public void registerMember(MemberVO member) {

        if( memberRepository.findByEmail(member.getEmail()) != null)
            throw new InvalidRequestException("이미 가입 되어 있는 이메일");

        memberRepository.insertMember(member);
    }

    public void setLogoutToken(String key, long expire) {
        memberRepository.setLogoutToken(key, expire);
    }

    public boolean isLogout(String key) {
        return memberRepository.getIsLogout(key) != null;
    }

    public void updateMemberNickname(int id, String nickname) {
        memberRepository.updateMemberNickname(id, nickname);
    }

    public void changePassword(int id, String email, Map<String, String> passwords) {
        String password = memberRepository.findPassword(email);

        if(!bCryptPasswordEncoder.matches(passwords.get("password"), password)) {
            throw new PasswordNotMatchesException("현재 비밀번호가 일치하지 않음");
        }

        if(!passwords.get("newPassword").equals(passwords.get("checkNewPassword"))) {
            throw new PasswordNotMatchesException("새로운 비밀번호가 서로 일치하지 않음");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(passwords.get("newPassword"));

        memberRepository.updateMemberPassword(id, encodedPassword);
    }

    public MemberVO inquriyMember(int memberId) {
        return memberRepository.selectById(memberId);
    }
}
