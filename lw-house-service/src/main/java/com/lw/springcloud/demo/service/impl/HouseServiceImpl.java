package com.lw.springcloud.demo.service.impl;

import com.lw.cloud.dto.TransactionMessage;
import com.lw.cloud.feignclient.TransactionMessageClient;
import com.lw.cloud.util.JsonUtils;
import com.lw.springcloud.demo.entity.HouseInfo;
import com.lw.springcloud.demo.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 房屋service
 */
@Service
public class HouseServiceImpl implements HouseService {

    private Logger logger = LoggerFactory.getLogger(HouseServiceImpl.class);

    @Autowired
    private TransactionMessageClient transactionMessageClient;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean update(HouseInfo houseInfo) {
        logger.info("修改房源信息！");
        TransactionMessage message = new TransactionMessage();
        message.setQueue("house_queue");
        message.setMessage(JsonUtils.toJson(houseInfo));
        message.setCreateDate(new Date());
        message.setSendSystem("lw-house-service");
        boolean result = transactionMessageClient.sendMessage(message);
        if(!result){
            throw new RuntimeException("回滚事务");
        }
        return result;
    }
}
