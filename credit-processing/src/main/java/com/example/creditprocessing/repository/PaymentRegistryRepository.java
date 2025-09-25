package com.example.creditprocessing.repository;

import com.example.creditprocessing.entity.PaymentRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//Repository interface for PaymentRegistry entity
@Repository
public interface PaymentRegistryRepository extends JpaRepository<PaymentRegistry, Long> {
    
    //Find payment registries by product registry ID
    List<PaymentRegistry> findByProductRegistryId(Long productRegistryId);
    
    //Find expired payment registries
    List<PaymentRegistry> findByExpiredTrue();
    
    //Find non-expired payment registries
    List<PaymentRegistry> findByExpiredFalse();
    
    //Find payment registries in date range
    List<PaymentRegistry> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
    
    //Find payment registries by expiration date range
    List<PaymentRegistry> findByPaymentExpirationDateBetween(LocalDateTime start, LocalDateTime end);
    
    //Find overdue payments (expired with debt)
    @Query("SELECT pr FROM PaymentRegistry pr WHERE pr.expired = true AND pr.debtAmount > 0")
    List<PaymentRegistry> findOverduePayments();
    
    //Find payment registries by product registry with debt
    @Query("SELECT pr FROM PaymentRegistry pr WHERE pr.productRegistry.id = :productRegistryId AND pr.debtAmount > 0")
    List<PaymentRegistry> findByProductRegistryIdWithDebt(Long productRegistryId);
    
    //Calculate total debt for product registry
    @Query("SELECT SUM(pr.debtAmount) FROM PaymentRegistry pr WHERE pr.productRegistry.id = :productRegistryId")
    BigDecimal getTotalDebtByProductRegistryId(Long productRegistryId);
    
    //Count expired payments for product registry
    long countByProductRegistryIdAndExpiredTrue(Long productRegistryId);
}