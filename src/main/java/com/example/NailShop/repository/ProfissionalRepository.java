package com.example.NailShop.repository;

import com.example.NailShop.entity.Profissionais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissionais, Integer> {
    Optional<Profissionais> findByEmail(String email);
}
