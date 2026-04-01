package com.example.NailShop.repository;

import com.example.NailShop.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Integer, Usuario> {
    Optional<Usuario> existsByEmail(String email);
}
