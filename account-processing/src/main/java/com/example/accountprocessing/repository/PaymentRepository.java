package com.example.accountprocessing.repository;

import com.example.accountprocessing.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//Repository interface for Payment entity
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    //Find payments by account ID
    List<Payment> findByAccountId(Long accountId);
    
    //Find payments by type
    List<Payment> findByType(String type);
    
    //Find credit payments
    List<Payment> findByIsCreditTrue();

    //Find paid payments
    List<Payment> findByPayedAtIsNotNull();

    //Find unpaid payments
    List<Payment> findByPayedAtIsNull();

    //Find payments in date range
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);

    //Find payments by account and date range
    List<Payment> findByAccountIdAndPaymentDateBetween(Long accountId, LocalDateTime start, LocalDateTime end);

    //Calculate total payments for account
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.account.id = :accountId AND p.payedAt IS NOT NULL")
    BigDecimal getTotalPaidAmountByAccountId(Long accountId);
}