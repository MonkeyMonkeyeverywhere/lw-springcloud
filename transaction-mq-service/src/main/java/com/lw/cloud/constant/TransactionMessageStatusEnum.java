package com.lw.cloud.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TransactionMessageStatusEnum {
    /**
     * 等待
     */

    WAITING(0),

    /**
     * 已完成
     */
    over(1),

    /**
     * 死亡
     */
    die(2)
    ;

    private int status;

    public static TransactionMessageStatusEnum parse(int status){
        return Arrays.stream(TransactionMessageStatusEnum.values()).filter(e->e.getStatus() == status).findFirst().orElse(null);
    }

    TransactionMessageStatusEnum(int status) {
        this.status = status;
    }

}
