package com.toctoc;

import com.toctoc.entity.Colis;
import com.toctoc.entity.Livreur;
import com.toctoc.entity.StatutColis;
import com.toctoc.service.ColisService;
import com.toctoc.service.LivreurService;
import com.toctoc.service.StatistiqueService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INITIALIZING SPRING CONTAINER ===\n");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n=== SPRING CONTAINER INITIALIZED ===\n");

        // Récupération des services avec différents types d'injection
        LivreurService livreurService = (LivreurService) context.getBean("livreurService");
        ColisService colisService = (ColisService) context.getBean("colisService");
        StatistiqueService statistiqueService = (StatistiqueService) context.getBean("statistiqueService");

        System.out.println("\n=== DÉMONSTRATION DES SCOPES ===");
        demonstrateScopes(context);

        try {
            System.out.println("\n=== TEST 1: CREATE LIVREURS (Constructor Injection) ===");

            // Create livreurs with unique phone numbers
            Livreur livreur1 = createOrGetLivreur(livreurService, "Alami", "Mohammed",
                    "Moto Honda", "0612345678");
            Livreur livreur2 = createOrGetLivreur(livreurService, "Bennani", "Fatima",
                    "Voiture Dacia", "0698765432");

            System.out.println("✓ Livreur 1: " + livreur1.getNom() + " " + livreur1.getPrenom() + " (ID: " + livreur1.getId() + ")");
            System.out.println("✓ Livreur 2: " + livreur2.getNom() + " " + livreur2.getPrenom() + " (ID: " + livreur2.getId() + ")");

            System.out.println("\n=== TEST 2: CREATE COLIS (Setter Injection) ===");
            Colis colis1 = Colis.builder()
                    .destinataire("Hassan Tazi")
                    .adresse("Rue Principale, Casablanca")
                    .poids(2.5)
                    .statut(StatutColis.PREPARATION)
                    .build();

            Colis colis2 = Colis.builder()
                    .destinataire("Amina Khalil")
                    .adresse("Avenue Mohammed V, Rabat")
                    .poids(5.0)
                    .statut(StatutColis.PREPARATION)
                    .livreur(livreur1)
                    .build();

            Colis colis3 = Colis.builder()
                    .destinataire("Youssef Idrissi")
                    .adresse("Quartier Maarif, Casablanca")
                    .poids(1.8)
                    .statut(StatutColis.PREPARATION)
                    .livreur(livreur1)
                    .build();

            colis1 = colisService.saveColis(colis1);
            colis2 = colisService.saveColis(colis2);
            colis3 = colisService.saveColis(colis3);
            System.out.println("✓ Colis 1 créé (ID: " + colis1.getId() + "): " + colis1.getDestinataire());
            System.out.println("✓ Colis 2 créé (ID: " + colis2.getId() + "): " + colis2.getDestinataire());
            System.out.println("✓ Colis 3 créé (ID: " + colis3.getId() + "): " + colis3.getDestinataire());

            System.out.println("\n=== TEST 3: STATISTIQUES (Field Injection) ===");
            System.out.println("Total Livreurs: " + statistiqueService.getTotalLivreurs());
            System.out.println("Total Colis: " + statistiqueService.getTotalColis());
            System.out.println("Poids Total: " + statistiqueService.getPoidsTotal() + " kg");

            Map<StatutColis, Long> statsParStatut = statistiqueService.getColisParStatut();
            System.out.println("\nColis par statut:");
            statsParStatut.forEach((statut, count) ->
                    System.out.println("  - " + statut + ": " + count + " colis"));

            System.out.println("\n=== TEST 4: GET ALL LIVREURS ===");
            List<Livreur> allLivreurs = livreurService.getAllLivreurs();
            System.out.println("Total livreurs: " + allLivreurs.size());
            allLivreurs.forEach(l -> System.out.println("  - " + l.getNom() + " " + l.getPrenom() +
                    " (Tel: " + l.getTelephone() + ")"));

            System.out.println("\n=== TEST 5: GET ALL COLIS ===");
            List<Colis> allColis = colisService.getAllColis();
            System.out.println("Total colis: " + allColis.size());
            allColis.forEach(c -> System.out.println("  - Colis ID: " + c.getId() +
                    ", Destinataire: " + c.getDestinataire() +
                    ", Statut: " + c.getStatut() +
                    ", Livreur: " + (c.getLivreur() != null ? c.getLivreur().getNom() : "Non assigné")));

            System.out.println("\n=== TEST 6: UPDATE COLIS STATUS ===");
            Colis updatedColis = colisService.updateStatut(colis2.getId(), StatutColis.EN_TRANSIT);
            System.out.println("✓ Colis " + updatedColis.getId() + " mis à jour: " + updatedColis.getStatut());

            updatedColis = colisService.updateStatut(colis3.getId(), StatutColis.LIVRE);
            System.out.println("✓ Colis " + updatedColis.getId() + " mis à jour: " + updatedColis.getStatut());

            System.out.println("\n=== TEST 7: GET COLIS BY LIVREUR ===");
            List<Colis> colisByLivreur = colisService.getColisByLivreur(livreur1.getId());
            System.out.println("Colis assignés au livreur " + livreur1.getNom() + ": " + colisByLivreur.size());
            colisByLivreur.forEach(c -> System.out.println("  - Colis ID: " + c.getId() +
                    ", Destinataire: " + c.getDestinataire() +
                    ", Statut: " + c.getStatut()));

            System.out.println("\n=== TEST 8: GET LIVREUR BY ID ===");
            Optional<Livreur> foundLivreur = livreurService.getLivreurById(livreur1.getId());
            foundLivreur.ifPresent(l -> System.out.println("✓ Livreur trouvé: " + l.getNom() + " " +
                    l.getPrenom() + " (Véhicule: " + l.getVehicule() + ")"));

            System.out.println("\n=== TEST 9: GET COLIS BY ID ===");
            Optional<Colis> foundColis = colisService.getColisById(colis1.getId());
            foundColis.ifPresent(c -> System.out.println("✓ Colis trouvé: Destinataire=" +
                    c.getDestinataire() + ", Adresse=" + c.getAdresse()));

            System.out.println("\n=== TEST 10: UPDATE LIVREUR ===");
            livreur2.setVehicule("Camionnette Renault");
            Livreur updatedLivreur = livreurService.updateLivreur(livreur2.getId(), livreur2);
            System.out.println("✓ Livreur mis à jour: " + updatedLivreur.getNom() +
                    " - Nouveau véhicule: " + updatedLivreur.getVehicule());

            System.out.println("\n=== TEST 11: DUPLICATE PHONE NUMBER ===");
            try {
                Livreur duplicateLivreur = Livreur.builder()
                        .nom("Test")
                        .prenom("Duplicate")
                        .telephone("0612345678") // Same as livreur1
                        .build();
                livreurService.saveLivreur(duplicateLivreur);
                System.out.println("✗ ERREUR: Le numéro dupliqué aurait dû être rejeté!");
            } catch (RuntimeException e) {
                System.out.println("✓ Exception attendue: " + e.getMessage());
            }

            System.out.println("\n=== TEST 12: INVALID LIVREUR REFERENCE ===");
            try {
                Colis invalidColis = Colis.builder()
                        .destinataire("Test Invalid")
                        .adresse("Test Address")
                        .poids(1.0)
                        .livreur(Livreur.builder().id(9999L).build()) // Non-existent ID
                        .build();
                colisService.saveColis(invalidColis);
                System.out.println("✗ ERREUR: Le livreur invalide aurait dû être rejeté!");
            } catch (RuntimeException e) {
                System.out.println("✓ Exception attendue: " + e.getMessage());
            }

            System.out.println("\n=== TEST 13: DELETE COLIS ===");
            colisService.deleteColis(colis1.getId());
            Optional<Colis> deletedColis = colisService.getColisById(colis1.getId());
            if (deletedColis.isEmpty()) {
                System.out.println("✓ Colis " + colis1.getId() + " supprimé avec succès");
            }

            System.out.println("\n=== TEST 14: STATISTIQUES FINALES (Field Injection) ===");
            System.out.println("Total Livreurs: " + statistiqueService.getTotalLivreurs());
            System.out.println("Total Colis restants: " + statistiqueService.getTotalColis());
            System.out.println("Poids Total: " + statistiqueService.getPoidsTotal() + " kg");

            Map<StatutColis, Long> finalStats = statistiqueService.getColisParStatut();
            System.out.println("\nColis par statut:");
            finalStats.forEach((statut, count) ->
                    System.out.println("  - " + statut + ": " + count + " colis"));

            System.out.println("\n" + "=".repeat(60));
            System.out.println("=== RÉCAPITULATIF DES CONCEPTS SPRING DÉMONTRÉS ===");
            System.out.println("=".repeat(60));
            System.out.println("\n1️⃣  INJECTION DE DÉPENDANCES (3 types):");
            System.out.println("   ✓ Constructor Injection: LivreurServiceImpl");
            System.out.println("   ✓ Setter Injection: ColisServiceImpl");
            System.out.println("   ✓ Field Injection: StatistiqueServiceImpl (@Autowired)");

            System.out.println("\n2️⃣  BEAN SCOPES:");
            System.out.println("   ✓ Singleton: LivreurService, ColisService (instance unique)");
            System.out.println("   ✓ Prototype: StatistiqueService (nouvelle instance à chaque appel)");

            System.out.println("\n3️⃣  SPRING DATA JPA:");
            System.out.println("   ✓ Repositories générés automatiquement");
            System.out.println("   ✓ Méthodes de requête personnalisées (findByStatut, findByLivreurId)");

            System.out.println("\n4️⃣  GESTION TRANSACTIONNELLE:");
            System.out.println("   ✓ Transactions automatiques sur les méthodes de service");

            System.out.println("\n5️⃣  VALIDATION MÉTIER:");
            System.out.println("   ✓ Unicité du téléphone vérifiée");
            System.out.println("   ✓ Vérification existence livreur avant assignation");

            System.out.println("\n" + "=".repeat(60));
            System.out.println("✅ ALL TESTS COMPLETED SUCCESSFULLY");
            System.out.println("=".repeat(60));

        } catch (Exception e) {
            System.err.println("\n❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ((ClassPathXmlApplicationContext) context).close();
            System.out.println("\n=== SPRING CONTAINER CLOSED ===");
        }
    }

    /**
     * Crée un nouveau livreur ou récupère un existant par téléphone
     */
    private static Livreur createOrGetLivreur(LivreurService service, String nom,
                                              String prenom, String vehicule, String telephone) {
        try {
            Livreur livreur = Livreur.builder()
                    .nom(nom)
                    .prenom(prenom)
                    .vehicule(vehicule)
                    .telephone(telephone)
                    .build();
            return service.saveLivreur(livreur);
        } catch (RuntimeException e) {
            // Livreur existe déjà, le récupérer
            System.out.println("  ℹ️  Livreur avec tel " + telephone + " existe déjà, récupération...");
            return service.getAllLivreurs().stream()
                    .filter(l -> l.getTelephone().equals(telephone))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Erreur de récupération du livreur"));
        }
    }

    /**
     * Démontre la différence entre les scopes SINGLETON et PROTOTYPE
     */
    private static void demonstrateScopes(ApplicationContext context) {
        // Test SINGLETON scope (LivreurService)
        LivreurService service1 = context.getBean("livreurService", LivreurService.class);
        LivreurService service2 = context.getBean("livreurService", LivreurService.class);

        System.out.println("LivreurService (Singleton):");
        System.out.println("  Instance 1: " + service1.hashCode());
        System.out.println("  Instance 2: " + service2.hashCode());
        System.out.println("  Même instance? " + (service1 == service2));

        // Test PROTOTYPE scope (StatistiqueService)
        StatistiqueService stats1 = context.getBean("statistiqueService", StatistiqueService.class);
        StatistiqueService stats2 = context.getBean("statistiqueService", StatistiqueService.class);

        System.out.println("\nStatistiqueService (Prototype):");
        System.out.println("  Instance 1: " + stats1.hashCode());
        System.out.println("  Instance 2: " + stats2.hashCode());
        System.out.println("  Même instance? " + (stats1 == stats2));
    }
}