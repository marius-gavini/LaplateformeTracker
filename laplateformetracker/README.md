# 📚 La Plateforme Tracker - Gestion des Étudiants

Une application de gestion des étudiants moderne et sécurisée, développée avec **JavaFX** et **PostgreSQL**. Avec authentification par rôles, CRUD complet, statistiques et import/export CSV.

---

## ✨ Fonctionnalités

### 🔐 Authentification et Sécurité
- Connexion avec **nom d'utilisateur et mot de passe**
- Mots de passe **hashés avec BCrypt** (sécurisé)
- Deux rôles : `admin` et `student`
- Comptes par défaut :
  - `admin` / `admin` → **Admin**
  - `student` / `student` → **Étudiant**

### 👥 Gestion des Étudiants (CRUD)
- ✅ **Ajouter** un nouvel étudiant (nom, prénom, âge, note)
- ✏️ **Modifier** les données d'un étudiant
- 🗑️ **Supprimer** un étudiant
- 👀 **Afficher** la liste complète
- 🔍 **Rechercher** un étudiant par ID

### 📊 Fonctionnalités Avancées
- **Tri avancé** : par nom, prénom, âge, ou moyenne
- **Recherche filtrée** :
  - Par âge exact
  - Par plage de notes (min-max)
- **Statistiques en temps réel** :
  - Moyenne des notes
  - Distribution par tranches d'âge
- **Import/Export CSV** :
  - Export complet des données
  - Import en masse depuis fichier CSV

---

## 🛠️ Prérequis

| Composant | Version | Statut |
|-----------|---------|--------|
| Java | 11+ | ✅ Requis |
| Maven | 3.6+ | ✅ Requis |
| PostgreSQL | 12+ | ✅ Requis |
| JavaFX | 13+ | ✅ Inclus dans pom.xml |

---

## 📦 Installation et Configuration

### 1. Préparer la Base de Données

```bash
# Se connecter à PostgreSQL
psql -U postgres

# Créer la base de données
CREATE DATABASE db_laplateforme_tracker;
```

La structure des tables sera créée automatiquement au premier lancement.

### 2. Configurer la Connexion (optionnel)

Modifier [DatabaseUtil.java](laplateformetracker/src/main/java/com/example/DatabaseUtil.java) si nécessaire :

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_laplateforme_tracker";
private static final String DB_USERNAME = "postgres";
private static final String DB_PASSWORD = "postgres";
```

### 3. Compiler le Projet

```bash
cd laplateformetracker
mvn clean compile
```

### 4. Lancer l'Application

```bash
mvn javafx:run
```

---

## 🏗️ Architecture

### Structure du Projet

```
laplateformetracker/
├── src/main/java/com/example/
│   ├── App.java                      # Point d'entrée
│   ├── AuthService.java              # Authentification
│   ├── LoginController.java           # Écran de connexion
│   ├── PrimaryController.java         # Tableau de bord
│   ├── StudentListController.java     # Gestion de la liste
│   ├── StudentFormController.java     # Formulaire CRUD
│   ├── StudentDAO.java               # Accès données (Students)
│   ├── UserDAO.java                  # Accès données (Users)
│   ├── DatabaseUtil.java             # Gestion DB
│   ├── Student.java                  # Modèle
│   ├── User.java                     # Modèle
│   ├── StudentCSVManager.java        # Import/Export CSV
│   ├── StatisticsManager.java        # Calculs stats
│   └── module-info.java              # Configuration modules
├── src/main/resources/
│   └── com/example/                  # Fichiers FXML
│       ├── login.fxml
│       ├── primary.fxml
│       ├── secondary.fxml
│       ├── student_form.fxml
│       └── student_list.fxml
├── pom.xml                           # Configuration Maven
└── README.md                         # Ce fichier
```

### Diagramme des Couches

```
┌─────────────────────────────┐
│   UI (Controllers/FXML)     │  ← Interaction utilisateur
├─────────────────────────────┤
│   Business Logic (Services) │  ← Authentification, Statistiques
├─────────────────────────────┤
│   Data Access (DAO)         │  ← StudentDAO, UserDAO
├─────────────────────────────┤
│   Database (PostgreSQL)     │  ← Persistence
└─────────────────────────────┘
```

---

## 📚 Dépendances

| Dépendance | Version | Usage |
|-----------|---------|-------|
| JavaFX Controls | 13 | Interface GUI |
| JavaFX FXML | 13 | Définition layouts |
| PostgreSQL Driver | 42.6.0 | Connexion DB |
| JBCrypt | 0.4 | Hash sécurisé mots de passe |

---

## 🔄 Flux d'Authentification

```
1. Utilisateur saisit username/password
                    ↓
2. LoginController → AuthService.authenticate()
                    ↓
3. AuthService → UserDAO.findByUsername()
                    ↓
4. Récupération User depuis DB
                    ↓
5. Vérification hash : BCrypt.checkpw(password, hash)
                    ↓
6. Si valide → Redirection tableau de bord
   Sinon → Message d'erreur
```

---

## 📋 Schéma Base de Données

### Table: `users`
```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('student', 'admin'))
);
```

### Table: `student`
```sql
CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    grade NUMERIC(4,2) NOT NULL
);
```

### Tables additionnelles
- `promotions` : Gestion des promotions
- `grades` : Notes par sujet et promotion

---

## 🚀 Guide d'Utilisation

### Première Connexion
1. Lancer l'application → Écran de login
2. Entrer : `admin` / `admin` (ou `student` / `student`)
3. Cliquer "Se Connecter"

### Ajouter un Étudiant
1. Aller dans **Gestion Étudiants** → **Ajouter**
2. Remplir le formulaire (nom, prénom, âge, note)
3. Cliquer **Enregistrer**

### Exporter en CSV
1. **Gestion Étudiants** → **Export CSV**
2. Sélectionner l'emplacement
3. Fichier créé : `students_export.csv`

### Importer depuis CSV
1. Préparer un fichier CSV avec colonnes : `first_name,last_name,age,grade`
2. **Gestion Étudiants** → **Import CSV**
3. Sélectionner le fichier → Valider

---

## 🧪 Compilation et Tests

```bash
# Nettoyer + Compiler
mvn clean compile

# Compiler avec tests
mvn clean test-compile

# Lancer les tests (si disponibles)
mvn test

# Build complet
mvn clean package
```

---

## 📝 Fichiers de Configuration

### `pom.xml`
- Configuration Maven
- Gestion des dépendances
- Plugins JavaFX et compiler

### `module-info.java`
- Système de modules Java
- Déclaration des dépendances :
  - `javafx.controls`, `javafx.fxml`, `javafx.graphics`
  - `java.sql`
  - `jbcrypt` (automatic module)

---

## ⚠️ Troubleshooting

| Problème | Solution |
|----------|----------|
| "Cannot connect to database" | Vérifier que PostgreSQL est démarré et accessible |
| "Table does not exist" | L'application crée les tables automatiquement au premier lancement |
| "BCrypt not found" | Vérifier que `jbcrypt` est dans `pom.xml` et `module-info.java` |
| "JavaFX not found" | Exécuter `mvn clean compile` pour télécharger les dépendances |

---

## 📄 Licence

Ce projet est fourni à titre d'exemple éducatif.

---

## 👤 Auteur

Développé avec **JavaFX**, **PostgreSQL** et **Maven**.

**Dernière mise à jour :** Avril 2026