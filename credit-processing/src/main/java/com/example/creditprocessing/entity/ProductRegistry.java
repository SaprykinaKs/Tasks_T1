package com.example.creditprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// ProductRegistry entity representing credit product registrations
@Entity
@Table(name = "product_registry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRegistry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Client ID is required")
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    @NotNull(message = "Account ID is required")
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    
    @NotNull(message = "Product ID is required")
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Interest rate cannot exceed 100%")
    @Column(name = "interest_rate", precision = 5, scale = 2)
    private java.math.BigDecimal interestRate;
    
    @Builder.Default
    @Column(name = "open_date")
    private LocalDateTime openDate = LocalDateTime.now();
    
    @Column(name = "close_date")
    private LocalDateTime closeDate;
    
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-Many relationship with PaymentRegistry
    @OneToMany(mappedBy = "productRegistry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PaymentRegistry> paymentRegistries = new ArrayList<>();
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    //Check if product registry is active (not closed)
    public boolean isActive() {
        return closeDate == null;
    }
    
    //Close the product registry
    public void close() {
        this.closeDate = LocalDateTime.now();
    }
}
