package com.example.accountprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.example.accountprocessing.enums.PaymentSystem;
import com.example.accountprocessing.enums.CardStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Card entity representing bank cards

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @NotBlank(message = "Card ID is required")
    @Column(name = "card_id", unique = true, nullable = false)
    private String cardId;
    
    @NotNull(message = "Payment system is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_system", nullable = false)
    private PaymentSystem paymentSystem;
    
    @NotNull(message = "Status is required")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status = CardStatus.ACTIVE;
    
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    // One-to-Many relationship with Transaction
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    // Check if card is active
    public boolean isActive() {
        return status == CardStatus.ACTIVE;
    }
    
    // Check if card is expired
    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    // Block the card
    public void block() {
        this.status = CardStatus.BLOCKED;
    }

    // Cancel the card
    public void cancel() {
        this.status = CardStatus.CANCELLED;
    }
}
