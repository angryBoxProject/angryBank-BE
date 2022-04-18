package com.teamY.angryBox.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@Alias("CoinBankVO")
public class CoinBankVO {

    private int id;
    private int memberId;
    private String name;
    private String memo;
    private int angryLimit;
    private String reward;
    private boolean expired;

    public CoinBankVO(int id, int memberId, String name, String memo, int angryLimit, String reward, boolean expired) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.memo = memo;
        this.angryLimit = angryLimit;
        this.reward = reward;
        this.expired = expired;
    }

    public CoinBankVO(String name, String memo, int angryLimit, String reward) {
        this.name = name;
        this.memo = memo;
        this.angryLimit = angryLimit;
        this.reward = reward;
    }

    public CoinBankVO(int id, int memberId, String name, String memo, int angryLimit, String reward) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.memo = memo;
        this.angryLimit = angryLimit;
        this.reward = reward;
    }

    public CoinBankVO(int memberId, String name, String memo, int angryLimit, String reward) {
        this.memberId = memberId;
        this.name = name;
        this.memo = memo;
        this.angryLimit = angryLimit;
        this.reward = reward;
    }

    public CoinBankVO(int memberId, boolean expired){
        this.memberId = memberId;
        this.expired = expired;
    }
}
