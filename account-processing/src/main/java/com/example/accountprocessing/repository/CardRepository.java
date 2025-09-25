package com.example.accountprocessing.repository;

import com.example.accountprocessing.entity.Card;
import com.example.accountprocessing.enums.CardStatus;
import com.example.accountprocessing.enums.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Card entity
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    /**
     * Find card by cardId
     */
    Optional<Card> findByCardId(String cardId);
    
    /**
     * Find cards by account ID
     */
    List<Card> findByAccountId(Long accountId);
    
    /**
     * Find cards by payment system
     */
    List<Card> findByPaymentSystem(PaymentSystem paymentSystem);
    
    /**
     * Find cards by status
     */
    List<Card> findByStatus(CardStatus status);
    
    /**
     * Find active cards by account ID
     */
    List<Card> findByAccountIdAndStatus(Long accountId, CardStatus status);
    
    /**
     * Check if cardId exists
     */
    boolean existsByCardId(String cardId);
}