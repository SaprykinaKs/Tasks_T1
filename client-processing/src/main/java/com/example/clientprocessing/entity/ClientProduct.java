package com.example.clientprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.example.clientprocessing.enums.ClientProductStatus;

import java.time.LocalDateTime;

//ClientProduct entity representing the relationship between clients and products
@Entity
@Table(name = "client_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with Client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    // Many-to-One relationship with Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Builder.Default
    @Column(name = "open_date")
    private LocalDateTime openDate = LocalDateTime.now();
    
    @Column(name = "close_date")
    private LocalDateTime closeDate;
    
    @NotNull(message = "Status is required")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientProductStatus status = ClientProductStatus.ACTIVE;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Check if the client product is active
    public boolean isActive() {
        return status == ClientProductStatus.ACTIVE;
    }
    
    //Close the client product
    public void close() {
        this.status = ClientProductStatus.CLOSED;
        this.closeDate = LocalDateTime.now();
    }
    
    //Block the client product
    public void block() {
        this.status = ClientProductStatus.BLOCKED;
    }

    //Arrest the client product
    public void arrest() {
        this.status = ClientProductStatus.ARRESTED;
    }
}
