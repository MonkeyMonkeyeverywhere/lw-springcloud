package com.lw.cloud.service;

import com.lw.cloud.entity.TransactionMessage;

import java.util.List;

public interface TransactionMessageService {

    boolean sendMessage(TransactionMessage message);

    boolean sendMessage(List<TransactionMessage> messages);
}
