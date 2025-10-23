package com.toctoc.service;

import com.toctoc.entity.Livreur;
import java.util.List;
import java.util.Optional;

public interface LivreurService {
    Livreur saveLivreur(Livreur livreur);
    Optional<Livreur> getLivreurById(Long id);
    List<Livreur> getAllLivreurs();
    Livreur updateLivreur(Long id, Livreur livreur);
    void deleteLivreur(Long id);
}