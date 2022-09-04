package com.teamY.angryBox.error.customException;

import com.teamY.angryBox.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(String message) {
        super(message);
    }

    protected void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.valueOf(errorCode.getStatusCode());
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }

    public String getError() {
        return super.getMessage();
    }
}
