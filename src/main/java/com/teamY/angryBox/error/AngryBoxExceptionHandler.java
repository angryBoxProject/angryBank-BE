package com.teamY.angryBox.error;

import com.teamY.angryBox.dto.ResponseMessage;
import com.teamY.angryBox.error.customException.InvalidRefreshTokenException;
import com.teamY.angryBox.error.customException.InvalidRequestException;
import com.teamY.angryBox.error.customException.PasswordNotMatchesException;
import com.teamY.angryBox.error.customException.SQLInquiryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class AngryBoxExceptionHandler {

    @ExceptionHandler(SQLInquiryException.class)
    public ResponseEntity<ResponseMessage> handleSQLInquiryException(SQLInquiryException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "", e.getMessage()), e.getStatusCode());
    }

    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<ResponseMessage> handlePasswordNotMatchesException(PasswordNotMatchesException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "", e.getMessage()), e.getStatusCode());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ResponseMessage> handleInvalidRequestException(InvalidRequestException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "", e.getMessage()), e.getStatusCode());
    }
    
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ResponseMessage> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "", e.getMessage()), e.getStatusCode());
    }


    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "유효성 검사 실패", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     RestController 에 @Validated 어노테이션을 사용하여 RequestParam에 직접 Valid 처리 할 때 발생
     **/
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ResponseMessage(false, "유효성 검사 실패", e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
