package com.teamY.angryBox.error;

import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.error.customException.PasswordNotMatchesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AngryBoxExceptionHandler {

    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<ResponseMessage> handlePasswordNotMatchesException(PasswordNotMatchesException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "비밀번호 불일치", e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
