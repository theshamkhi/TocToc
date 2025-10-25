package com.toctoc.service;

import com.toctoc.entity.StatutColis;
import java.util.Map;

public interface StatistiqueService {
    long getTotalLivreurs();
    long getTotalColis();
    Map<StatutColis, Long> getColisParStatut();
    double getPoidsTotal();
}