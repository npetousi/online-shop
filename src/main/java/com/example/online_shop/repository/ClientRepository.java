package com.example.online_shop.repository;

import com.example.online_shop.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    Optional<ClientEntity> findByEmailAndPhoneNumber(String email, String phoneNumber);
}
