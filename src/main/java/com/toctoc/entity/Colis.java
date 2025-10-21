package com.toctoc.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinataire;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private Double poids;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutColis statut = StatutColis.PREPARATION;

    @ManyToOne
    @JoinColumn(name = "id_livreur")
    private Livreur livreur;
}
