package com.example.creditprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// PaymentRegistry entity representing credit payment registrations
@Entity
@Table(name = "payment_registry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRegistry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with ProductRegistry
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_registry_id", nullable = false)
    private ProductRegistry productRegistry;
    
    @NotNull(message = "Payment date is required")
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @DecimalMin(value = "0.0", message = "Interest rate amount cannot be negative")
    @Column(name = "interest_rate_amount", precision = 18, scale = 2)
    private BigDecimal interestRateAmount;
    
    @DecimalMin(value = "0.0", message = "Debt amount cannot be negative")
    @Column(name = "debt_amount", precision = 18, scale = 2)
    private BigDecimal debtAmount;
    
    @Builder.Default
    private Boolean expired = false;
    
    @Column(name = "payment_expiration_date")
    private LocalDateTime paymentExpirationDate;
    
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        // Auto-check if payment is expired
        checkExpiration();
    }
    
    @PrePersist
    public void prePersist() {
        checkExpiration();
    }
    // Check and update expiration status based on expiration date
    private void checkExpiration() {
        if (paymentExpirationDate != null && LocalDateTime.now().isAfter(paymentExpirationDate)) {
            this.expired = true;
        }
    }
    
    //Get expired status as boolean primitive
    public Boolean getExpired() {
        return expired != null ? expired : false;
    }
    
    //Check if payment is expired
    public boolean isExpired() {
        return Boolean.TRUE.equals(expired);
    }
    
    //Mark payment as expired
    public void markAsExpired() {
        this.expired = true;
    }
    
    //Check if payment is overdue (expired but not paid)
    public boolean isOverdue() {
        return isExpired() && (debtAmount == null || debtAmount.compareTo(BigDecimal.ZERO) > 0);
    }
    
    //Calculate total payment amount (principal + interest)
    public BigDecimal getTotalAmount() {
        BigDecimal total = amount != null ? amount : BigDecimal.ZERO;
        if (interestRateAmount != null) {
            total = total.add(interestRateAmount);
        }
        return total;
    }
}
