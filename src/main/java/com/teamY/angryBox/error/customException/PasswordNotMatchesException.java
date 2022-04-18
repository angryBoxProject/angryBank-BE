package com.teamY.angryBox.error.customException;

import com.teamY.angryBox.error.ErrorCode;

public class PasswordNotMatchesException extends CustomException{
    public PasswordNotMatchesException(String message) {
        super(message);
        setErrorCode(ErrorCode.PASSWORD_NOT_MATCHES);
    }
}
