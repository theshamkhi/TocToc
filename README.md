# 📦 TocToc - Smart Delivery Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring](https://img.shields.io/badge/Spring-6.0.11-green)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.1.2-green)
![Hibernate](https://img.shields.io/badge/Hibernate-6.2.7-59666C)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-316192)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36)

## 📋 Description

**TocToc** est un système de gestion centralisée des livraisons développé pour la société **SmartLogi**. Ce projet vise à moderniser et automatiser la gestion des livraisons en remplaçant les méthodes manuelles (fichiers Excel et registres papier) par une solution logicielle robuste et fiable.

### 🎯 Problématiques résolues

- ❌ **Erreurs de saisie** : informations incorrectes (adresse, poids, destinataire)
- ❌ **Retards dans les livraisons** : difficulté de planification et absence de suivi en temps réel
- ❌ **Double enregistrement ou perte de données** : colis enregistrés plusieurs fois ou non suivis
- ❌ **Visibilité limitée** : difficulté à obtenir des rapports précis sur l'état des livraisons

### ✅ Solutions apportées

- ✅ Gestion centralisée des informations sur les colis et les livreurs
- ✅ Prévention des erreurs de saisie et des doublons
- ✅ Amélioration de la planification et de la visibilité des livraisons
- ✅ Suivi en temps réel de l'état des colis

---

## 🏗️ Architecture & Technologies

### Stack Technique

| Technologie | Version | Utilisation |
|-------------|---------|-------------|
| **Java** | 17 | Langage de programmation |
| **Spring Core** | 6.0.11 | IoC, Dependency Injection, Bean Management |
| **Spring Data JPA** | 3.1.2 | Couche de persistance |
| **Hibernate** | 6.2.7.Final | ORM (Object-Relational Mapping) |
| **PostgreSQL** | 12+ | Base de données relationnelle |
| **HikariCP** | 5.0.1 | Connection pooling |
| **Querydsl** | 5.0.0 | Query DSL pour Spring Data JPA |
| **Lombok** | 1.18.30 | Réduction du code boilerplate |
| **Maven** | 3.6+ | Gestion des dépendances et build |

### Concepts Spring Démontrés

#### 🔹 Injection de Dépendances (3 types)

| Type | Service | Implémentation |
|------|---------|----------------|
| **Constructor Injection** | `LivreurServiceImpl` | Recommandée pour les dépendances obligatoires |
| **Setter Injection** | `ColisServiceImpl` | Pour les dépendances optionnelles |
| **Field Injection** | `StatistiqueServiceImpl` | Utilise `@Autowired` |

#### 🔹 Bean Scopes

| Scope | Bean | Comportement |
|-------|------|--------------|
| **Singleton** | `LivreurService`, `ColisService` | Une seule instance partagée |
| **Prototype** | `StatistiqueService` | Nouvelle instance à chaque requête |

---

## 📊 Modèle de Données

### Diagramme de Classes UML

```
┌─────────────────────────────┐
│        Livreur              │
├─────────────────────────────┤
│ - id: Long (PK)             │
│ - nom: String               │
│ - prenom: String            │
│ - vehicule: String          │
│ - telephone: String (UNIQUE)│
├─────────────────────────────┤
│ + saveLivreur()             │
│ + getAllLivreurs()          │
│ + updateLivreur()           │
│ + deleteLivreur()           │
└─────────────┬───────────────┘
              │ 1
              │
              │ OneToMany
              │
              │ *
┌─────────────┴───────────────┐
│         Colis               │
├─────────────────────────────┤
│ - id: Long (PK)             │
│ - destinataire: String      │
│ - adresse: String           │
│ - poids: Double             │
│ - statut: StatutColis       │
│ - livreur: Livreur (FK)     │
├─────────────────────────────┤
│ + saveColis()               │
│ + updateStatut()            │
│ + getColisByLivreur()       │
│ + deleteColis()             │
└─────────────────────────────┘

┌─────────────────────────────┐
│    <<enumeration>>          │
│      StatutColis            │
├─────────────────────────────┤
│ + PREPARATION               │
│ + EN_TRANSIT                │
│ + LIVRE                     │
└─────────────────────────────┘
```

### Relations

- **Livreur** `1` ──────< `*` **Colis** (One-to-Many / Many-to-One)
- Un livreur peut avoir plusieurs colis
- Un colis est assigné à un seul livreur (optionnel)

---

## 📁 Structure du Projet

```
TocToc/
├── src/
│   └── main/
│       ├── java/com/toctoc/
│       │   ├── entity/                    # Entités JPA
│       │   │   ├── Colis.java
│       │   │   ├── Livreur.java
│       │   │   └── StatutColis.java
│       │   │
│       │   ├── repository/                # Repositories Spring Data JPA
│       │   │   ├── ColisRepository.java
│       │   │   └── LivreurRepository.java
│       │   │
│       │   ├── service/                   # Couche métier
│       │   │   ├── LivreurService.java
│       │   │   ├── LivreurServiceImpl.java      (Constructor Injection)
│       │   │   ├── ColisService.java
│       │   │   ├── ColisServiceImpl.java        (Setter Injection)
│       │   │   ├── StatistiqueService.java
│       │   │   └── StatistiqueServiceImpl.java  (Field Injection)
│       │   │
│       │   └── Main.java                  # Point d'entrée et tests
│       │
│       └── resources/
│           ├── applicationContext.xml     # Configuration Spring
│           └── database.properties        # Configuration BDD
│
├── pom.xml                                # Configuration Maven
└── README.md
```

### Architecture en Couches

```
┌─────────────────────────────────────┐
│         Main.java                   │  ← Couche Présentation (Tests)
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Service Layer                  │  ← Couche Métier
│  - LivreurServiceImpl               │
│  - ColisServiceImpl                 │
│  - StatistiqueServiceImpl           │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│    Repository Layer                 │  ← Couche Accès Données
│  - LivreurRepository                │
│  - ColisRepository                  │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Entity Layer                   │  ← Couche Modèle
│  - Livreur                          │
│  - Colis                            │
│  - StatutColis                      │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│       PostgreSQL Database           │  ← Base de données
└─────────────────────────────────────┘
```

---

## 🚀 Installation et Configuration

### Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- ☑️ **Java JDK 17** ou supérieur
- ☑️ **Maven 3.6+**
- ☑️ **PostgreSQL 12+**
- ☑️ **Git** (pour cloner le projet)
- ☑️ Un IDE Java (IntelliJ IDEA, Eclipse, VS Code)

### 1️⃣ Cloner le Projet

```bash
git clone https://github.com/theshamkhi/TocToc.git
cd TocToc
```

### 2️⃣ Configurer PostgreSQL

#### Créer la base de données

```sql
-- Se connecter à PostgreSQL
psql -U postgres

-- Créer la base de données
CREATE DATABASE toctoc_db;

-- Vérifier la création
\l
```

#### Configurer les identifiants

Modifier le fichier `src/main/resources/database.properties` :

```properties
# Database Configuration
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/toctoc_db
db.username=postgres
db.password=VOTRE_MOT_DE_PASSE

# Hibernate Configuration
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
hibernate.format_sql=true
```

### 3️⃣ Compiler le Projet

```bash
mvn clean install
```

### 4️⃣ Exécuter l'Application

```bash
mvn exec:java
```

Ou avec la classe spécifiée :

```bash
mvn exec:java -Dexec.mainClass="com.toctoc.Main"
```

---

## 🧪 Tests et Démonstrations

Le fichier `Main.java` contient 14 tests complets démontrant toutes les fonctionnalités :

| Test | Description | Concept Démontré |
|------|-------------|------------------|
| Test 1 | Créer des livreurs | Constructor Injection |
| Test 2 | Créer des colis | Setter Injection |
| Test 3 | Statistiques globales | Field Injection |
| Test 4 | Lister tous les livreurs | Repository Pattern |
| Test 5 | Lister tous les colis | JPA Query Methods |
| Test 6 | Mettre à jour le statut | Transaction Management |
| Test 7 | Colis par livreur | Custom Query Methods |
| Test 8 | Récupérer un livreur par ID | Optional Handling |
| Test 9 | Récupérer un colis par ID | Entity Retrieval |
| Test 10 | Modifier un livreur | Update Operations |
| Test 11 | Test téléphone dupliqué | Business Rule Validation |
| Test 12 | Test livreur invalide | Data Integrity |
| Test 13 | Supprimer un colis | Delete Operations |
| Test 14 | Statistiques finales | Aggregate Functions |

### Exemple de Sortie Console

```
=== INITIALIZING SPRING CONTAINER ===
Bootstrapping Spring Data JPA repositories...
Found 2 JPA repository interfaces

=== SPRING CONTAINER INITIALIZED ===

=== DÉMONSTRATION DES SCOPES ===
LivreurService (Singleton):
  Instance 1: 1113942002
  Instance 2: 1113942002
  Même instance? true

StatistiqueService (Prototype):
  Instance 1: 191784281
  Instance 2: 1337666037
  Même instance? false

=== TEST 1: CREATE LIVREURS (Constructor Injection) ===
✓ Livreur 1: Alami Mohammed (ID: 1)
✓ Livreur 2: Bennani Fatima (ID: 2)

... [tous les tests]

============================================================
=== RÉCAPITULATIF DES CONCEPTS SPRING DÉMONTRÉS ===
============================================================

1️⃣  INJECTION DE DÉPENDANCES (3 types):
   ✓ Constructor Injection: LivreurServiceImpl
   ✓ Setter Injection: ColisServiceImpl
   ✓ Field Injection: StatistiqueServiceImpl (@Autowired)

2️⃣  BEAN SCOPES:
   ✓ Singleton: LivreurService, ColisService
   ✓ Prototype: StatistiqueService

✅ ALL TESTS COMPLETED SUCCESSFULLY
```

---

## 📚 User Stories Implémentées

### US1 : Gérer les livreurs (CRUD)
✅ Créer un nouveau livreur  
✅ Consulter un livreur par ID  
✅ Lister tous les livreurs  
✅ Modifier les informations d'un livreur  
✅ Supprimer un livreur

### US2 : Enregistrer et assigner un colis
✅ Créer un nouveau colis  
✅ Assigner un colis à un livreur  
✅ Validation de l'existence du livreur

### US3 : Mettre à jour le statut d'un colis
✅ PREPARATION → EN_TRANSIT → LIVRE  
✅ Suivi en temps réel

### US4 : Lister les colis par livreur
✅ Planification des tournées  
✅ Visualisation de la charge de travail

### US5 : Supprimer ou corriger une information
✅ Suppression de colis  
✅ Modification des informations  
✅ Garantie de l'intégrité des données

---

## 🔒 Règles Métier

- ✅ **Unicité du téléphone** : Un numéro de téléphone ne peut être associé qu'à un seul livreur
- ✅ **Validation du livreur** : Un colis ne peut être assigné qu'à un livreur existant
- ✅ **Statut par défaut** : Nouveau colis créé avec le statut PREPARATION
- ✅ **Cascade operations** : Suppression des colis lors de la suppression d'un livreur

---

<div align="center">

**⭐ N'oubliez pas de mettre une étoile si ce projet vous a aidé ! ⭐**

</div>