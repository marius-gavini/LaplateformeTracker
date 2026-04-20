-- ============================================
--  Initialisation de la table users
--  Avec mots de passe hashés (BCrypt)
-- ============================================

-- CLEAN 
TRUNCATE TABLE promotions;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE student RESTART IDENTITY CASCADE; 
TRUNCATE TABLE grade RESTART IDENTITY CASCADE;

-- PROMOTIONS
INSERT INTO promotions (name, year) VALUES
('Informatique', 2024),
('CyberSécurité', 2024),
('Développement Web', 2025);

-- USERS TEST
--INSERT INTO users (username, password, role) VALUES
--('student1', '$2a$10$l4pt0RxfqinxdXmPUPgC9.GOA6Vdy927wCwra6Jw.ERdmBrbeOFTy', 'student');

-- PROMOTION INFO STUDENT
INSERT INTO users (username, password, role) VALUES
('jdupont', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student'),
('mbernard', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student'),
('acisse', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student');

INSERT INTO student (first_name, last_name, age, promotion_id) VALUES
('Jean', 'Dupont', 21, 1),
('Marie', 'Bernard', 22, 1),
('Ali', 'Cissé', 20, 1);

-- PROMOTION CYBER STUDENT
INSERT INTO users (username, password, role) VALUES
('lmartin', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student'),
('knguyen', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student');

INSERT INTO student (first_name, last_name, age, promotion_id) VALUES
('Lucas', 'Martin', 23, 2),
('Kim', 'Nguyen', 21, 2);

-- PROMOTION WEB DEV STUDENT
INSERT INTO users (username, password, role) VALUES
('sdiop', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student'),
('fmoreau', '$2a$10$4tRnXfO1f4PNgtOWVqQQTuz.n08XdS5zzRXV2dIYUOL4rTLzXfbNq', 'student');

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

INSERT INTO users(username, password, role) VALUES
('admin','$2a$10$Ln0eHmoUk/4mZm6LRXdcZeaBRVAbOfLS60yv6GPVfeZUJL35iGfVu', 'admin');