package com.teamY.angryBox.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ResponseMessage {

    boolean success;
    String message;
    String error;

    public ResponseMessage(boolean success, String message, String error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

}