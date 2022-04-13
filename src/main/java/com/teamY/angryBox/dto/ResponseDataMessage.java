package com.teamY.angryBox.dto;

import io.swagger.models.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ResponseDataMessage extends ResponseMessage {
    Map<String, Object> data;
    public ResponseDataMessage(boolean success, String message, String error, Map<String, Object> data) {
        super(success, message, error);
        this.data = data;
    }
}
