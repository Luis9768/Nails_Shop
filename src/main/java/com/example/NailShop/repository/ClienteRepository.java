package com.example.NailShop.repository;

import com.example.NailShop.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    boolean existsByEmail(String email);
    Optional<Cliente> findByEmail(String email);
}
