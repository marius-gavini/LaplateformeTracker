# Gestion d'Étudiants - Application JavaFX

Une application complète de gestion des étudiants avec base de données PostgreSQL, authentification et interface graphique moderne.

## Fonctionnalités

### Authentification
- Connexion simple avec nom d'utilisateur et mot de passe
- Deux rôles : `admin` et `eleve`
- Comptes par défaut :
  - admin / admin (ADMIN)
  - eleve / eleve (STUDENT)

### Gestion des Étudiants (CRUD)
- **Ajouter** un nouvel étudiant (nom, prénom, âge, note)
- **Modifier** un étudiant existant par ID
- **Supprimer** un étudiant par ID
- **Afficher** tous les étudiants
- **Rechercher** un étudiant par ID

### Fonctionnalités Avancées
- **Tri** des étudiants par nom, prénom, âge ou moyenne des notes
- **Recherche avancée** :
  - Par âge exact
  - Par plage de notes (min-max)
- **Statistiques** :
  - Moyenne des notes de la classe
  - Nombre d'étudiants par tranche d'âge (<18, 18-25, >25)
- **Import/Export CSV** :
  - Export de tous les étudiants vers CSV
  - Import d'étudiants depuis CSV

## Configuration de la Base de Données

### Prérequis
- PostgreSQL installé et démarré
- Créer une base de données nommée `etudidb`
- Utilisateur par défaut : `postgres` / `postgres`

### Script SQL de création
```sql
CREATE DATABASE etudidb;

-- Se connecter à etudidb
CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    grade DOUBLE PRECISION NOT NULL
);
```

### Configuration personnalisée
Modifier `DatabaseUtil.java` si nécessaire :
```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/etudidb";
private static final String DB_USERNAME = "votre_utilisateur";
private static final String DB_PASSWORD = "votre_mot_de_passe";
```

## Installation et Exécution

### Prérequis système
- Java 11 ou supérieur
- Maven 3.6+
- PostgreSQL

### Compilation
```bash
cd demo
mvn clean compile
```

### Exécution
```bash
mvn javafx:run
```

### Construction du JAR
```bash
mvn clean package
java -jar target/demo-1.0.jar
```

## Architecture

### Classes principales
- `App.java` : Point d'entrée et gestion des scènes
- `LoginController.java` : Gestion de l'authentification
- `PrimaryController.java` : Tableau de bord principal
- `StudentListController.java` : Gestion de la liste des étudiants
- `StudentFormController.java` : Formulaire d'ajout/édition
- `StudentDAO.java` : Couche d'accès aux données
- `DatabaseUtil.java` : Gestion de la connexion DB
- `StudentCSVManager.java` : Import/Export CSV
- `StatisticsManager.java` : Calcul des statistiques

### Technologies utilisées
- **JavaFX 13** : Interface utilisateur
- **PostgreSQL** : Base de données
- **JDBC** : Connexion à la base de données
- **Maven** : Gestion des dépendances et build

## Utilisation

1. **Démarrer l'application**
2. **Se connecter** avec `admin`/`admin` ou `eleve`/`eleve`
3. **Naviguer dans l'application** :
   - "Afficher tous les étudiants" : Voir la liste complète
   - "Ajouter un nouvel étudiant" : Créer un étudiant
   - Utiliser les fonctions de recherche et tri
   - Consulter les statistiques
   - Importer/Exporter des données CSV

## Format CSV

### Export
```
ID,Prénom,Nom,Âge,Note
1,Jean,Dupont,20,15.5
2,Marie,Martin,19,17.0
```

### Import
- Même format que l'export
- Les champs vides ou mal formatés sont ignorés
- L'ID est auto-généré par la base de données

## Sécurité

- Utilisation de `PreparedStatement` pour éviter l'injection SQL
- Validation des données côté client
- Gestion d'erreurs appropriée

## Développement

### Structure du projet
```
demo/
├── pom.xml
├── src/main/java/com/example/
│   ├── App.java
│   ├── LoginController.java
│   ├── PrimaryController.java
│   ├── StudentListController.java
│   ├── StudentFormController.java
│   ├── StudentDAO.java
│   ├── DatabaseUtil.java
│   ├── StudentCSVManager.java
│   ├── StatisticsManager.java
│   ├── Student.java
│   ├── StudentStatistics.java
│   ├── User.java
│   ├── AuthService.java
│   └── module-info.java
└── src/main/resources/com/example/
    ├── login.fxml
    ├── primary.fxml
    ├── student_list.fxml
    └── student_form.fxml
```

### Tests
```bash
mvn test
```

## Améliorations futures

- Pagination pour les grandes listes
- Export PDF/HTML des statistiques
- Sauvegarde automatique
- Interface plus moderne avec CSS
- Gestion des rôles plus fine
- Historique des modifications

## Auteur

Application développée dans le cadre d'un projet d'apprentissage Java/PostgreSQL.