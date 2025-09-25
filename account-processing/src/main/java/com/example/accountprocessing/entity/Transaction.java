package com.example.accountprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.example.accountprocessing.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Transaction entity representing account transactions
 
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    // Many-to-One relationship with Card (optional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;
    
    @NotBlank(message = "Transaction type is required")
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @NotNull(message = "Status is required")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.ALLOWED;
    
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    // Check if transaction is allowed
    public boolean isAllowed() {
        return status == TransactionStatus.ALLOWED;
    }

    // Check if transaction is complete
    public boolean isComplete() {
        return status == TransactionStatus.COMPLETE;
    }

    // Check if transaction is blocked
    public boolean isBlocked() {
        return status == TransactionStatus.BLOCKED;
    }

    // Start processing transaction
    public void startProcessing() {
        this.status = TransactionStatus.PROCESSING;
    }
    
    // Complete the transaction
    public void complete() {
        this.status = TransactionStatus.COMPLETE;
    }
    
    // Block the transaction
    public void block() {
        this.status = TransactionStatus.BLOCKED;
    }

    // Cancel the transaction
    public void cancel() {
        this.status = TransactionStatus.CANCELLED;
    }
}
