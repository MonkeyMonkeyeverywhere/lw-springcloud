package com.lw.cloud.dao;

import com.lw.cloud.entity.TransactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lw
 */
public interface TransactionMessageRepository extends JpaRepository<TransactionMessage,Long> {
}
