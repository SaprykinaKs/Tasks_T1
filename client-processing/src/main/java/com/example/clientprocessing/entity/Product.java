package com.example.clientprocessing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.example.clientprocessing.enums.ProductKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Product entity representing banking products
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    @Column(nullable = false)
    private String name;
    
    @NotNull(message = "Product key is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "key", nullable = false)
    private ProductKey key;
    
    @Builder.Default
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();
    
    @Column(name = "product_id", unique = true)
    private String productId;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-Many relationship with ClientProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ClientProduct> clientProducts = new ArrayList<>();
    
    @PrePersist
    public void prePersist() {
        if (productId == null && key != null && id != null) {
            productId = key.name() + id;
        }
    }
    
    @PostPersist
    public void postPersist() {
        // Generate productId after entity is persisted (id is available)
        if (productId == null && key != null) {
            productId = key.name() + id;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        // Ensure productId is always consistent
        if (key != null && id != null) {
            productId = key.name() + id;
        }
    }
}
