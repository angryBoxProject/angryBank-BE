package com.teamY.angryBox.error.customException;

import com.teamY.angryBox.error.ErrorCode;

public class SQLInquiryException extends CustomException {
    public SQLInquiryException(String message) {
        super(message);
        setErrorCode(ErrorCode.SQL_ERROR);
    }
}
