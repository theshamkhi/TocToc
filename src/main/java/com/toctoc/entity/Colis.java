package com.toctoc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    @NotBlank(message = "Le destinataire est obligatoire")
    @Column(nullable = false)
    private String destinataire;

    @NotBlank(message = "L'adresse est obligatoire")
    @Column(nullable = false, length = 500)
    private String adresse;

    @Positive(message = "Le poids doit être positif")
    @Column(nullable = false)
    private Double poids;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutColis statut = StatutColis.PREPARATION;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livreur")
    private Livreur livreur;
}
