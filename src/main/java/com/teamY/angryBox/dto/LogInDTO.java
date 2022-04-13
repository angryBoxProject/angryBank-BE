package com.teamY.angryBox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LogInDTO {
    private String email;
    private String password;
}
