package com.example.clientprocessing.repository;

import com.example.clientprocessing.entity.Product;
import com.example.clientprocessing.enums.ProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository interface for Product entity
 
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    //Find product by productId
    Optional<Product> findByProductId(String productId);

    //Find products by key
    List<Product> findByKey(ProductKey key);
    
    //Find products by name containing
    List<Product> findByNameContainingIgnoreCase(String name);
    
    //Check if productId exists
    boolean existsByProductId(String productId);
}