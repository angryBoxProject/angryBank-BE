package com.teamY.angryBox.config.security.oauth;

import com.teamY.angryBox.error.ErrorCode;
import com.teamY.angryBox.repository.MemberRepository;
import com.teamY.angryBox.service.MemberService;
import com.teamY.angryBox.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//Filter, GenericFilterBean : 매 서블릿마다 호출
//OncePerRequestFilter : 사용자의 요청 당 한 번씩만 실행
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);
        String key = String.valueOf(token).substring(7);

        try {
            if(memberRepository.getIsLogout(token.getToken()) != null){
                request.setAttribute("error", ErrorCode.TOKEN_IN_BLACKLIST);
                throw new RuntimeException(ErrorCode.TOKEN_IN_BLACKLIST.getMessage());
            }
            if (token.validate(request)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch(Exception e) {

            log.info("토큰이 유효하지 않음. URI : {}", request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }
}
