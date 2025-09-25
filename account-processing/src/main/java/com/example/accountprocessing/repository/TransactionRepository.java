package com.example.accountprocessing.repository;

import com.example.accountprocessing.entity.Transaction;
import com.example.accountprocessing.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//Repository interface for Transaction entity
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //Find transactions by account ID
    List<Transaction> findByAccountId(Long accountId);
    
    //Find transactions by card ID
    List<Transaction> findByCardId(Long cardId);

    //Find transactions by status
    List<Transaction> findByStatus(TransactionStatus status);
    
    //Find transactions by type
    List<Transaction> findByType(String type);
    
    //Find transactions in date range
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    //Find transactions by account and date range
    List<Transaction> findByAccountIdAndTimestampBetween(Long accountId, LocalDateTime start, LocalDateTime end);

    //Find transactions by account and status
    List<Transaction> findByAccountIdAndStatus(Long accountId, TransactionStatus status);

    //Calculate total transaction amount for account
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.id = :accountId AND t.status = :status")
    BigDecimal getTotalAmountByAccountIdAndStatus(Long accountId, TransactionStatus status);

    //Count transactions by status
    long countByStatus(TransactionStatus status);
}