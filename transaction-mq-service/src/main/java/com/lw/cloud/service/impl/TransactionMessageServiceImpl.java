package com.lw.cloud.service.impl;

import com.lw.cloud.dao.TransactionMessageRepository;
import com.lw.cloud.entity.TransactionMessage;
import com.lw.cloud.service.TransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TransactionMessageServiceImpl implements TransactionMessageService {

    @Autowired
    private TransactionMessageRepository transactionMessageRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendMessage(TransactionMessage message) {
        if(check(message)){
            transactionMessageRepository.save(message);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendMessage(List<TransactionMessage> messages) {
        for (TransactionMessage message : messages){
            if(!check(message)){
                return false;
            }
        }
        transactionMessageRepository.save(messages);
        return true;
    }

    private boolean check(TransactionMessage message) {
        if (!StringUtils.hasText(message.getMessage()) || !StringUtils.hasText(message.getQueue())
                || !StringUtils.hasText(message.getSendSystem())) {
            return false;
        }
        if (message.getCreateDate() == null) {
            return false;
        }
        return true;
    }
}
