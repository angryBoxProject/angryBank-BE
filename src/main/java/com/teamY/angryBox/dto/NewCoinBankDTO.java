package com.teamY.angryBox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@AllArgsConstructor
public class NewCoinBankDTO {

    private int id;

    @NotBlank(message = "저금통 이름을 입력해주세요.")
    private String name;

    private String memo;

    @Range(min = 100, max = 1000, message = "분노한계치는 100 이상 1000 이하로 설정해주세요.")
    private int angryLimit;


    private String reward;
}
