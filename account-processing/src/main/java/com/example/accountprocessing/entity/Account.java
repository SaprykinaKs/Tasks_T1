package com.example.accountprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.example.accountprocessing.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Account entity representing bank accounts

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Client ID is required")
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    @NotNull(message = "Product ID is required")
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    @Builder.Default
    @Column(precision = 18, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Interest rate cannot exceed 100%")
    @Column(name = "interest_rate", precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @Builder.Default
    @Column(name = "is_recalc")
    private Boolean isRecalc = false;
    
    @Builder.Default
    @Column(name = "card_exist")
    private Boolean cardExist = false;
    
    @NotNull(message = "Status is required")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;
    
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-Many relationship with Card
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Card> cards = new ArrayList<>();
    
    // One-to-Many relationship with Payment
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();
    
    // One-to-Many relationship with Transaction
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Add amount to balance
    public void credit(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(amount);
        }
    }
    
    // Subtract amount from balance
    public void debit(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.subtract(amount);
        }
    }

    // Check if account is active
    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }

    // Close the account
    public void close() {
        this.status = AccountStatus.CLOSED;
    }

    // Block the account
    public void block() {
        this.status = AccountStatus.BLOCKED;
    }
}
