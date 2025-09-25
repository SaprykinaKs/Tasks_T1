package com.example.accountprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Payment entity representing account payments
    
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @NotNull(message = "Payment date is required")
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Builder.Default
    @Column(name = "is_credit")
    private Boolean isCredit = false;
    
    @Column(name = "payed_at")
    private LocalDateTime payedAt;
    
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;
    
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Mark payment as paid
    public void markAsPaid() {
        this.payedAt = LocalDateTime.now();
    }

    // Check if payment is paid
    public boolean isPaid() {
        return payedAt != null;
    }

    // Check if payment is credit
    public boolean isCreditPayment() {
        return Boolean.TRUE.equals(isCredit);
    }
}
