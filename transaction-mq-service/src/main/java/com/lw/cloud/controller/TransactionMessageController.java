package com.lw.cloud.controller;

import com.lw.cloud.entity.TransactionMessage;
import com.lw.cloud.service.TransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 可靠性消息接口
 */
@RestController
@RequestMapping("message")
public class TransactionMessageController {

    @Autowired
    private TransactionMessageService transactionMessageService;


    /**
     * 发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
     *
     * @param message
     * @return
     */
    @PostMapping("send")
    public boolean sendMessage(@RequestBody TransactionMessage message){
        return transactionMessageService.sendMessage(message);
    }

    /**
     * 批量发送消息
     *
     * @param messages
     * @return
     */
    @PostMapping("sends")
    public boolean sendMessage(@RequestBody List<TransactionMessage> messages){
        return transactionMessageService.sendMessage(messages);
    }

}
