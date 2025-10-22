package com.toctoc.repository;

import com.toctoc.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivreurRepository extends JpaRepository<Livreur, Long> {
    Optional<Livreur> findByTelephone(String telephone);
    boolean existsByTelephone(String telephone);
}