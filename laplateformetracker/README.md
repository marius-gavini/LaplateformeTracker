# 📚 La Plateforme Tracker - Gestion des Étudiants

Application JavaFX de gestion des étudiants avec accès à une base PostgreSQL.

Ce projet propose un écran de connexion, une liste d'étudiants, un formulaire de saisie/modification, de l'import/export CSV et des statistiques.

---

## 🚀 Le projet en bref

- Interface graphique créée avec **JavaFX**.
- Authentification gérée via **AuthService** et **UserDAO**.
- Modèle étudiant stocké via **StudentDAO**.
- Import/Export CSV avec **StudentCSVManager**.
- Statistiques calculées dans **StatisticsManager**.

---

## 📦 Prérequis

- Java 11+
- Maven 3.6+
- PostgreSQL 12+
- Connexion JDBC configurée dans `DatabaseUtil.java`

---

## ⚙️ Configuration de la base de données

1. Créez la base PostgreSQL :

```sql
CREATE DATABASE db_laplateforme_tracker;
```

2. Vérifiez les paramètres dans `src/main/java/com/example/DatabaseUtil.java` :

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_Laplateforme_tracker";
private static final String DB_USERNAME = "postgres";
private static final String DB_PASSWORD = "postgres";
```

3. Au premier démarrage, le projet tente de créer automatiquement le schéma.

---

## ▶️ Compilation et exécution

```bash
cd laplateformetracker
mvn clean compile
mvn javafx:run
```

---

## 🧩 Structure du projet

```
laplateformetracker/
├── pom.xml
├── README.md
├── src/main/java/com/example/
│   ├── App.java
│   ├── AuthService.java
│   ├── DatabaseUtil.java
│   ├── LoginController.java
│   ├── PrimaryController.java
│   ├── StudentFormController.java
│   ├── StudentListController.java
│   ├── StudentDAO.java
│   ├── StudentCSVManager.java
│   ├── StatisticsManager.java
│   ├── Student.java
│   ├── StudentStatistics.java
│   ├── User.java
│   ├── UserDAO.java
│   ├── HashGen.java
│   └── module-info.java
└── src/main/resources/com/example/
    ├── login.fxml
    ├── primary.fxml
    ├── secondary.fxml
    ├── student_form.fxml
    └── student_list.fxml
```

---

## ✅ Fonctionnalités observées

- Connexion utilisateur via `LoginController`.
- Authentification sécurisée avec **BCrypt**.
- CRUD étudiant : création, modification, suppression.
- Recherche par ID et par âge.
- Tri de la liste par colonnes.
- Affichage de statistiques via `StatisticsManager`.
- Export CSV et import CSV de la liste d'étudiants.

---

## 🔧 Architecture de données

Le code contient un schéma de base de données défini dans `DatabaseUtil` :

- `users`
- `students`
- `promotions`
- `grades`

### Tables principales

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('student', 'admin'))
);
```

```sql
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    promotion_id INT NOT NULL REFERENCES promotions(id)
);
```

```sql
CREATE TABLE promotions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    year INT NOT NULL
);
```

```sql
CREATE TABLE grades (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    subject VARCHAR(50) NOT NULL,
    grade NUMERIC(4,2) NOT NULL,
    promotion_id INT REFERENCES promotions(id) ON DELETE SET NULL
);
```

---

## ⚠️ Points à corriger

Le projet n'est pas totalement aligné actuellement. Voici les points principaux à vérifier :

- `DatabaseUtil` crée une table `students` tandis que `StudentDAO` interroge `student`.
- `Student` contient un champ `promotion_id`, mais l'interface continue de manipuler des colonnes/tri `grade`.
- Aucun utilisateur n'est inséré automatiquement à la création de la base : il faut ajouter un `admin` et des `promotions` manuellement.
- La recherche par plage de notes est présente dans l'interface mais partiellement désactivée dans le code.

---

## 💡 Recommandations pour développement

- Vérifier et unifier le nom de la table student(s).
- Harmoniser le modèle étudiant entre `promotion_id` et `grade`.
- Ajouter un script de seed ou des données d'exemple.
- Compléter les tests unitaires pour la DAO et les services.

---

## 🛠️ Notes techniques

- `App.java` lance l'application JavaFX et initialise la base.
- `StudentCSVManager` gère l'import/export CSV.
- `StatisticsManager` calcule des statistiques à partir de `StudentDAO`.
- `AuthService` utilise `BCrypt` pour vérifier le mot de passe.

---

## 📌 Conclusion

Ce projet est une application JavaFX de gestion étudiante avec une bonne base technique. Pour la rendre pleinement fonctionnelle, il faut corriger les incohérences de schéma et ajouter un jeu de données initial.
