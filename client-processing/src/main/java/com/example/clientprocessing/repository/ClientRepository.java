package com.example.clientprocessing.repository;

import com.example.clientprocessing.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Repository interface for Client entity
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    // Find client by clientId
    Optional<Client> findByClientId(String clientId);
    
    // Check if clientId exists
    boolean existsByClientId(String clientId);
    
    // Find clients by user ID
    List<Client> findByUserId(Long userId);
    
    // Find clients by date of birth
    List<Client> findByDateOfBirth(LocalDate dateOfBirth);
    
    // Find clients by first and last name
    List<Client> findByFirstNameAndLastName(String firstName, String lastName);
    
    // Find client with products
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.clientProducts WHERE c.id = :id")
    Optional<Client> findByIdWithProducts(Long id);
    
    // Find clients by user with all relationships   
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.user LEFT JOIN FETCH c.clientProducts WHERE c.user.id = :userId")
    List<Client> findByUserIdWithRelations(Long userId);
}