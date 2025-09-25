package com.example.creditprocessing.repository;

import com.example.creditprocessing.entity.ProductRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//Repository interface for ProductRegistry entity
@Repository
public interface ProductRegistryRepository extends JpaRepository<ProductRegistry, Long> {
    
    //Find registries by client ID
    List<ProductRegistry> findByClientId(Long clientId);
    
    //Find registries by account ID
    List<ProductRegistry> findByAccountId(Long accountId);
    
    //Find registries by product ID
    List<ProductRegistry> findByProductId(Long productId);
    
    //Find active registries (not closed)
    List<ProductRegistry> findByCloseDateIsNull();
    
    //Find closed registries
    List<ProductRegistry> findByCloseDateIsNotNull();
    
    //Find active registries by client ID
    List<ProductRegistry> findByClientIdAndCloseDateIsNull(Long clientId);
    
    //Find registries opened in date range
    List<ProductRegistry> findByOpenDateBetween(LocalDateTime start, LocalDateTime end);
    
    //Find registry with payment registries
    @Query("SELECT pr FROM ProductRegistry pr LEFT JOIN FETCH pr.paymentRegistries WHERE pr.id = :id")
    Optional<ProductRegistry> findByIdWithPayments(@Param("id") Long id);
    
    //Calculate total debt for client across all active registries
    @Query("""
        SELECT SUM(p.debtAmount)
        FROM PaymentRegistry p
        JOIN p.productRegistry pr
        WHERE pr.clientId = :clientId
          AND pr.closeDate IS NULL
    """)
    BigDecimal getTotalDebtByClientId(@Param("clientId") Long clientId);
}
