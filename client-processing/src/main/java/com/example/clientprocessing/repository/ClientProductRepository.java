package com.example.clientprocessing.repository;

import com.example.clientprocessing.entity.ClientProduct;
import com.example.clientprocessing.enums.ClientProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
// Repository interface for ClientProduct entity
@Repository
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
    // Find client products by client ID
    List<ClientProduct> findByClientId(Long clientId);

    // Find client products by product ID
    List<ClientProduct> findByProductId(Long productId);

    // Find client products by status
    List<ClientProduct> findByStatus(ClientProductStatus status);

    // Find active client products by client ID
    List<ClientProduct> findByClientIdAndStatus(Long clientId, ClientProductStatus status);

    // Find client products with relationships
    @Query("SELECT cp FROM ClientProduct cp LEFT JOIN FETCH cp.client LEFT JOIN FETCH cp.product WHERE cp.client.id = :clientId")
    List<ClientProduct> findByClientIdWithRelations(Long clientId);

    // Count active products for client
    long countByClientIdAndStatus(Long clientId, ClientProductStatus status);
}