package com.toctoc.service;

import com.toctoc.entity.Colis;
import com.toctoc.entity.StatutColis;
import java.util.List;
import java.util.Optional;

public interface ColisService {
    Colis saveColis(Colis colis);
    Optional<Colis> getColisById(Long id);
    List<Colis> getAllColis();
    Colis updateStatut(Long id, StatutColis statut);
    List<Colis> getColisByLivreur(Long livreurId);
    void deleteColis(Long id);
}