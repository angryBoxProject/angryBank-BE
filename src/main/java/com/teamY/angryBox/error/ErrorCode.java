package com.teamY.angryBox.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400,"Invalid Input Value"),
    PROPERTY_NULL(400, "null인 property가 있음"),
    INQUIRY_NO_RESULT(400,"There is No inquiry result"),
    METHOD_NOT_ALLOWED(405,  "Method Not Allowed"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),
    TOKEN_MALFORMED_JWT(401, "토큰 시그니쳐에 문제가 있음"),
    TOKEN_EXPIRED_JWT(401, "만료된 토큰"),
    TOKEN_UNSUPPORTED_JWT(401, "지원하지 않는 토큰"),
    TOKEN_ILLEGAL_JWT(401, "JWT 토큰이 잘못됨"),
    TOKEN_NOT_FOUND(401, "토큰이 없음"),
    TOKEN_IN_BLACKLIST(401, "이미 로그아웃 한 토큰"),
    LOGIN_INFO_NOT_FOUND(400, "유저 정보 없음"),
    SQL_ERROR(400, "SQL 조회 에러"),
    PASSWORD_NOT_MATCHES(400, "비밀번호 불일치");

    private int statusCode;
    private final String message;


    ErrorCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
