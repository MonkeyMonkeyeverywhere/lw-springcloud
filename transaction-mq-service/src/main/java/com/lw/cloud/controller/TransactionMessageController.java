package com.lw.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.lw.cloud.entity.TransactionMessage;
import com.lw.cloud.service.TransactionMessageService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
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

    /**
     * 确认消息被消费
     * @param customerSystem  消费的系统
     * @param messageId	消息ID
     * @return
     */
    @PostMapping("/confirm/customer")
    public boolean confirmCustomerMessage(@RequestParam("customerSystem")String customerSystem,
                                          @RequestParam("messageId")Long messageId) {
        return transactionMessageService.confirmCustomerMessage(customerSystem, messageId);
    }

    /**
     * 查询最早没有被消费的消息
     * @param limit	查询条数
     * @return
     */
    @GetMapping("/wating")
    public List<TransactionMessage> findByWatingMessage(@RequestParam("limit")int limit) {
        return transactionMessageService.findByWatingMessage(limit);
    }

    /**
     * 确认消息死亡
     * @param messageId 消息ID
     * @return
     */
    @PostMapping("/confirm/die")
    public boolean confirmDieMessage(@RequestParam("messageId")Long messageId) {
        return transactionMessageService.confirmDieMessage(messageId);
    }

    /**
     * 累加发送次数
     * @param messageId 消息ID
     * @param sendDate  发送时间（task服务中的时间，防止服务器之间时间不同步问题）
     * @return
     */
    @PostMapping("/incrSendCount")
    public boolean incrSendCount(@RequestParam("messageId")Long messageId, @RequestParam("sendDate")String sendDate) {
        if (StringUtils.isBlank(sendDate)) {
            return transactionMessageService.incrSendCount(messageId, new Date());
        } else {
            return transactionMessageService.incrSendCount(messageId, DateUtil.parse(sendDate,"yyyy-MM-dd hh:mm:ss"));
        }
    }

    /**
     * 重新发送当前已死亡的消息
     * @return
     */
    @GetMapping("/send/retry")
    public boolean retrySendDieMessage() {
        return transactionMessageService.retrySendDieMessage();
    }


}
