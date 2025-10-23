package com.toctoc.service;

import com.toctoc.entity.Colis;
import com.toctoc.entity.StatutColis;
import com.toctoc.repository.ColisRepository;
import com.toctoc.repository.LivreurRepository;

import java.util.List;
import java.util.Optional;

public class ColisServiceImpl implements ColisService {

    private ColisRepository colisRepository;

    private LivreurRepository livreurRepository;

    public ColisServiceImpl() {
        System.out.println("ColisServiceImpl created - dependencies will be injected");
    }

    public void setColisRepository(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
        System.out.println("ColisRepository injected via Setter");
    }

    public void setLivreurRepository(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
        System.out.println("LivreurRepository injected via Setter");
    }

    @Override
    public Colis saveColis(Colis colis) {
        if (colis.getLivreur() != null) {
            livreurRepository.findById(colis.getLivreur().getId())
                    .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));
        }
        return colisRepository.save(colis);
    }

    @Override
    public Optional<Colis> getColisById(Long id) {
        return colisRepository.findById(id);
    }

    @Override
    public List<Colis> getAllColis() {
        return colisRepository.findAll();
    }

    @Override
    public Colis updateStatut(Long id, StatutColis statut) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colis non trouvé"));
        colis.setStatut(statut);
        return colisRepository.save(colis);
    }

    @Override
    public List<Colis> getColisByLivreur(Long livreurId) {
        return colisRepository.findByLivreurId(livreurId);
    }

    @Override
    public void deleteColis(Long id) {
        colisRepository.deleteById(id);
    }
}