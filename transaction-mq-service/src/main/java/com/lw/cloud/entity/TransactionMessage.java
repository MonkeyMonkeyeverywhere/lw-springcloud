package com.lw.cloud.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class TransactionMessage {
    @Id
    @GeneratedValue
    private Long id;
    private String message; //消息内容，以json格式存储,
    private String queue; //队列名称
    @Column(name = "send_system")
    private String sendSystem ; //发送的系统
    @Column(name = "send_count")
    private String sendCount ; //重复发送的消息次数
    @Column(name = "c_date")
    private Date createDate ; //创建时间
    @Column(name = "send_date")
    private Date sendDate; //最近发送消息时间
    private Integer status = 0 ; //状态：0等待消费 1已消费 2已死亡
    @Column(name = "die_count")
    private Integer dieCount = 10 ; //死亡次数，由使用方决定，默认发送10次还没消费则标记死亡
    @Column(name = "customer_date")
    private Date customerDate; // 消费时间
    @Column(name = "customer_system")
    private String customerSystem; //消费系统
    @Column(name = "die_date")
    private Date dieDate; //死亡时间

}
