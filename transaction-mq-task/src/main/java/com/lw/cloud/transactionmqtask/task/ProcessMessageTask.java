package com.lw.cloud.transactionmqtask.task;

import com.lw.cloud.dto.TransactionMessage;
import com.lw.cloud.feignclient.TransactionMessageClient;
import com.lw.cloud.transactionmqtask.rabbitmqclient.RabbitMqClient;
import com.lw.cloud.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class ProcessMessageTask {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TransactionMessageClient transactionMessageClient;

    @Autowired
    private RabbitMqClient rabbitMqClient;

    /**
     * 默认消息发送间隔10秒
     */
    private static final int DEFAULT_SLEEP_TIME = 10000;
    /**
     * 默认一次操作消息条数
     */
    private static final int DEFAULT_MESSAGE_SIZE = 5000;

    /**
     * 一分钟
     */
    private static final int DEFAULT_MESSAGE_RETRY_INTERVAL = 60000;

    /**
     * 开始消息发送任务
     */
    public void start(){
        new Thread(() -> {
            while (true) {
                final RLock lock = redissonClient.getLock("REDISSON:LOCK:TRANSACTION-MQ-TASK");
                try {
                    lock.lock();
                    int sleepTime = process();
                    if (sleepTime > 0 ) {
                        Thread.sleep(sleepTime);
                    }
                } catch (Exception e) {
                    log.error("ProcessMessageTask发送消息错误{}",e.getMessage());
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }

    private int process() {
        int sleepTime = DEFAULT_SLEEP_TIME;
        List<TransactionMessage> messages = transactionMessageClient.findByWatingMessage(DEFAULT_MESSAGE_SIZE);//todo 我的服务并未分页查询
        if(messages.size() > DEFAULT_MESSAGE_SIZE){
            sleepTime = 0;
        }
        messages.stream().forEach(message -> doProcess(message));
        return sleepTime;
    }

    private void doProcess(TransactionMessage message) {
        //校验消息是否死亡
        if(Optional.ofNullable(message.getSendCount()).orElse(0) > message.getDieCount()){
            transactionMessageClient.confirmDieMessage(message.getId());
            return;
        }
        long currentTime = System.currentTimeMillis();
        long sendTime = Objects.isNull(message.getSendDate())? 0 : message.getSendDate().getTime();
        if((currentTime - sendTime) > DEFAULT_MESSAGE_RETRY_INTERVAL){
            rabbitMqClient.send(message.getQueue(), JsonUtils.toJson(message));
            // 更新发送次数和发送时间
            transactionMessageClient.incrSendCount(message.getId(),DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
    }

}
