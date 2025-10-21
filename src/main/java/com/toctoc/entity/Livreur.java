package com.toctoc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livreurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false)
    private String prenom;

    @NotBlank(message = "Le véhicule est obligatoire")
    private String vehicule;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{10}$", message = "Format téléphone invalide")
    @Column(unique = true)
    private String telephone;

    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Colis> colisList = new ArrayList<>();
}