package com.teamY.angryBox.error.customException;

import com.teamY.angryBox.error.ErrorCode;

public class PasswordNotMatchesException extends RuntimeException{
    public PasswordNotMatchesException(String message) {
        super(message);
    }
     public ErrorCode getErrorCode() {
        return ErrorCode.PASSWORD_NOT_MATCHES;
     }

}
