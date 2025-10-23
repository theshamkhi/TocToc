package com.toctoc.service;

import com.toctoc.entity.Livreur;
import com.toctoc.repository.LivreurRepository;
import java.util.List;
import java.util.Optional;

public class LivreurServiceImpl implements LivreurService {

    private LivreurRepository livreurRepository;

    public LivreurServiceImpl(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
        System.out.println("LivreurServiceImpl created via Constructor Injection");
    }

    @Override
    public Livreur saveLivreur(Livreur livreur) {
        if (livreurRepository.existsByTelephone(livreur.getTelephone())) {
            throw new RuntimeException("Ce numéro de téléphone existe déjà");
        }
        return livreurRepository.save(livreur);
    }

    @Override
    public Optional<Livreur> getLivreurById(Long id) {
        return livreurRepository.findById(id);
    }

    @Override
    public List<Livreur> getAllLivreurs() {
        return livreurRepository.findAll();
    }

    @Override
    public Livreur updateLivreur(Long id, Livreur livreur) {
        Livreur existing = livreurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));

        existing.setNom(livreur.getNom());
        existing.setPrenom(livreur.getPrenom());
        existing.setVehicule(livreur.getVehicule());
        existing.setTelephone(livreur.getTelephone());

        return livreurRepository.save(existing);
    }

    @Override
    public void deleteLivreur(Long id) {
        livreurRepository.deleteById(id);
    }
}