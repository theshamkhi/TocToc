package com.toctoc.repository;

import com.toctoc.entity.Colis;
import com.toctoc.entity.StatutColis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, Long> {
    List<Colis> findByLivreurId(Long livreurId);
    List<Colis> findByStatut(StatutColis statut);
}