package com.teamY.angryBox.error.customException;

import com.teamY.angryBox.error.ErrorCode;

public class InvalidRequestException extends CustomException {
    public InvalidRequestException(String message) {
        super(message);
        setErrorCode(ErrorCode.INVALID_REQUEST);
    }
}
