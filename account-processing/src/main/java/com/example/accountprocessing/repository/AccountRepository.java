package com.example.accountprocessing.repository;

import com.example.accountprocessing.entity.Account;
import com.example.accountprocessing.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//Repository interface for Account entity
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    //Find accounts by client ID
    List<Account> findByClientId(Long clientId);
    
    //Find accounts by product ID
    List<Account> findByProductId(Long productId);
    
    //Find accounts by status
    List<Account> findByStatus(AccountStatus status);

    //Find active accounts by client ID
    List<Account> findByClientIdAndStatus(Long clientId, AccountStatus status);
    
    //Find accounts with balance greater than
    List<Account> findByBalanceGreaterThan(BigDecimal balance);
    
    //Find accounts with cards
    List<Account> findByCardExistTrue();
    
    //Find account with all relationships
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.cards LEFT JOIN FETCH a.payments LEFT JOIN FETCH a.transactions WHERE a.id = :id")
    Optional<Account> findByIdWithRelations(Long id);
    
    //Calculate total balance for client
    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.clientId = :clientId AND a.status = :status")
    BigDecimal getTotalBalanceByClientIdAndStatus(Long clientId, AccountStatus status);
}