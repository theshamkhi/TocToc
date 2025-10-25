package com.toctoc.service;

import com.toctoc.entity.Colis;
import com.toctoc.entity.StatutColis;
import com.toctoc.repository.ColisRepository;
import com.toctoc.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private LivreurRepository livreurRepository;

    public StatistiqueServiceImpl() {
        System.out.println("StatistiqueServiceImpl created - Field Injection (PROTOTYPE scope)");
    }

    @Override
    public long getTotalLivreurs() {
        return livreurRepository.count();
    }

    @Override
    public long getTotalColis() {
        return colisRepository.count();
    }

    @Override
    public Map<StatutColis, Long> getColisParStatut() {
        Map<StatutColis, Long> stats = new HashMap<>();

        for (StatutColis statut : StatutColis.values()) {
            long count = colisRepository.findByStatut(statut).size();
            stats.put(statut, count);
        }

        return stats;
    }

    @Override
    public double getPoidsTotal() {
        List<Colis> allColis = colisRepository.findAll();
        return allColis.stream()
                .mapToDouble(Colis::getPoids)
                .sum();
    }
}