-- ============================================
--  Initialisation de la table users
--  Avec mots de passe hashés (BCrypt)
-- ============================================

-- Nettoyage (optionnel)
TRUNCATE TABLE promotions;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE student RESTART IDENTITY CASCADE; 
TRUNCATE TABLE grade RESTART IDENTITY CASCADE;

--Insertion des promotions
INSERT INTO promotions (name, year) VALUES
('Informatique', 2024),
('CyberSécurité', 2024),
('Développement Web', 2025);

-- Insertion des utilisateurs
INSERT INTO users (username, password, role) VALUES
('admin','$2a$10$Ln0eHmoUk/4mZm6LRXdcZeaBRVAbOfLS60yv6GPVfeZUJL35iGfVu', 'admin'),
('student1', '$2a$10$l4pt0RxfqinxdXmPUPgC9.GOA6Vdy927wCwra6Jw.ERdmBrbeOFTy', 'student');

-- Étudiants promotion Informatique
INSERT INTO users (username, password, role) VALUES
('jdupont', 'password123', 'student'),
('mbernard', 'password123', 'student'),
('acisse', 'password123', 'student');

INSERT INTO student (first_name, last_name, age, promotion_id) VALUES
('Jean', 'Dupont', 21, 1),
('Marie', 'Bernard', 22, 1),
('Ali', 'Cissé', 20, 1);

-- Étudiants promotion CyberSécurité
INSERT INTO users (username, password, role) VALUES
('lmartin', 'password123', 'student'),
('knguyen', 'password123', 'student');

INSERT INTO student (first_name, last_name, age, promotion_id) VALUES
('Lucas', 'Martin', 23, 2),
('Kim', 'Nguyen', 21, 2);

-- Étudiants promotion Développement Web
INSERT INTO users (username, password, role) VALUES
('sdiop', 'password123', 'student'),
('fmoreau', 'password123', 'student');

INSERT INTO student (first_name, last_name, age, promotion_id) VALUES
('Samba', 'Diop', 24, 3),
('Fanny', 'Moreau', 22, 3);

INSERT INTO grades (user_id, subject, grade, promotion_id) VALUES
(1, 'Maths', 14.5, 1),
(1, 'Java', 16.0, 1),
(1, 'BDD', 13.0, 1),

(2, 'Maths', 12.0, 1),
(2, 'Java', 15.5, 1),
(2, 'BDD', 14.0, 1),

(3, 'Maths', 10.0, 1),
(3, 'Java', 11.5, 1),
(3, 'BDD', 12.0, 1);
