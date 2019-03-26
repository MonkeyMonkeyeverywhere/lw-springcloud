package com.lw.cloud.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TransactionMessage {
    private Long id;
    private String message; //消息内容，以json格式存储,
    private String queue; //队列名称
    private String sendSystem ; //发送的系统
    private String sendCount ; //重复发送的消息次数
    private Date createDate ; //创建时间
    private Date sendDate; //最近发送消息时间
    private Integer status = 0 ; //状态：0等待消费 1已消费 2已死亡
    private Integer dieCount = 10 ; //死亡次数，由使用方决定，默认发送10次还没消费则标记死亡
    private Date customerDate; // 消费时间
    private String customerSystem; //消费系统
    private Date dieDate; //死亡时间

}
