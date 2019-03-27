package com.lw.cloud.service.impl;

import com.lw.cloud.constant.TransactionMessageStatusEnum;
import com.lw.cloud.dao.TransactionMessageRepository;
import com.lw.cloud.entity.TransactionMessage;
import com.lw.cloud.service.TransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean confirmCustomerMessage(String customerSystem, Long messageId) {
        TransactionMessage message = transactionMessageRepository.getOne(messageId);
        if(Objects.isNull(message)){
            return false;
        }
        message.setCustomerSystem(customerSystem);
        message.setCreateDate(new Date());
        message.setStatus(TransactionMessageStatusEnum.OVER.getStatus());
        transactionMessageRepository.save(message);
        return true;
    }

    @Override
    public List<TransactionMessage> findByWatingMessage(int limit) {
        Sort orders = new Sort(Sort.Direction.ASC,"id");
        return transactionMessageRepository.findAllByStatus(TransactionMessageStatusEnum.WAITING.getStatus(),orders);
    }

    @Override
    public boolean confirmDieMessage(Long messageId) {
        TransactionMessage message = transactionMessageRepository.getOne(messageId);
        if(Objects.isNull(message)){
            return false;
        }
        message.setStatus(TransactionMessageStatusEnum.DIE.getStatus());
        message.setDieDate(new Date());
        transactionMessageRepository.save(message);
        return true;
    }

    @Override
    public boolean incrSendCount(Long messageId, Date sendDate) {
        TransactionMessage message = transactionMessageRepository.getOne(messageId);
        if(Objects.isNull(message)){
            return false;
        }
        message.setSendDate(sendDate);
        message.setSendCount(message.getSendCount()+1);
        transactionMessageRepository.save(message);
        return true;
    }

    @Override
    public boolean retrySendDieMessage() {
        List<TransactionMessage> messages = transactionMessageRepository.findAllByStatus(TransactionMessageStatusEnum.DIE.getStatus());
        if(CollectionUtils.isEmpty(messages)){
            return false;
        }
        messages.stream().forEach(message -> {
            message.setStatus(TransactionMessageStatusEnum.WAITING.getStatus());
            message.setSendCount(0);
        });
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
