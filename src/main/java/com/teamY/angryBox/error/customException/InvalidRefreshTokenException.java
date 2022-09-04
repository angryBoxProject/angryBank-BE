package com.teamY.angryBox.error.customException;


import com.teamY.angryBox.error.ErrorCode;

public class InvalidRefreshTokenException extends CustomException{
    public InvalidRefreshTokenException(String message, ErrorCode error) {
        super(message);
        setErrorCode(error);
    }

    public InvalidRefreshTokenException(String message) {
        super(message);
        setErrorCode(ErrorCode.NO_SUCH_REFRESH_TOKEN);
    }
}
