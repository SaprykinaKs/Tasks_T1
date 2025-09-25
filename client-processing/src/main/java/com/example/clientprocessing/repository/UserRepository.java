package com.example.clientprocessing.repository;

import com.example.clientprocessing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Repository interface for User entity
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    //Find user by login
    Optional<User> findByLogin(String login);
    
    //Find user by email
    Optional<User> findByEmail(String email);
    
    //Check if login exists
    boolean existsByLogin(String login);
    
    //Check if email exists
    boolean existsByEmail(String email);
    
    //Find user with clients
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.clients WHERE u.id = :id")
    Optional<User> findByIdWithClients(Long id);
}