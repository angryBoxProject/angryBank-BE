package com.teamY.angryBox.config.security.oauth;

import com.teamY.angryBox.error.ErrorCode;
import com.teamY.angryBox.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("유효한 자격증명 제공x -> 401");


        log.info("authException : {}", authException.getLocalizedMessage());
        log.info("authException : {}", authException.getMessage());

        if(authException instanceof BadCredentialsException) {
            log.info("BadCredentialsException");
            request.setAttribute("error", ErrorCode.LOGIN_INFO_NOT_FOUND);
        }

            ErrorCode errorCode = (ErrorCode) request.getAttribute("error");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            JSONObject responseJson = new JSONObject();
            responseJson.put("success", false);
            responseJson.put("message", errorCode.getMessage());
            responseJson.put("error", errorCode.getMessage());
            responseJson.put("data", null);

            response.setStatus(errorCode.getStatusCode());
            response.getWriter().print(responseJson);


//        ErrorCode errorCode = (ErrorCode) request.getAttribute("Exception");
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("success", false);
//        responseJson.put("message", errorCode.getMessage());
//        responseJson.put("error", errorCode.getMessage());
//        responseJson.put("data", null);
//
//        response.setStatus(errorCode.getStatusCode());
//        response.getWriter().print(responseJson);

    }
}
