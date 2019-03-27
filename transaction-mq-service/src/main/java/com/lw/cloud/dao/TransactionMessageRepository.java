package com.lw.cloud.dao;

import com.lw.cloud.entity.TransactionMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lw
 */
public interface TransactionMessageRepository extends JpaRepository<TransactionMessage,Long> {
    List<TransactionMessage> findAllByStatus(int status);
    List<TransactionMessage> findAllByStatus(int status, Sort sort);
}
