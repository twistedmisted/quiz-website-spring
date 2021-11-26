drop database quiz_system;
CREATE
    DATABASE IF NOT EXISTS quiz_system;
USE quiz_system;

CREATE TABLE IF NOT EXISTS categories
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,

    CONSTRAINT categories PRIMARY KEY (id),
    CONSTRAINT categories_name_uq UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS difficulty
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,

    CONSTRAINT difficulty PRIMARY KEY (id),
    CONSTRAINT difficulty_name_uq UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS quizzes
(
    id            INT AUTO_INCREMENT,
    name          VARCHAR(30) NOT NULL,
    time          INT         NOT NULL,
    difficulty_id INT         NOT NULL,
    category_id   INT         NOT NULL,

    CONSTRAINT quizzes_pk PRIMARY KEY (id),
    CONSTRAINT quizzes_categories_fk FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT quizzes_difficulty_fk FOREIGN KEY (difficulty_id) REFERENCES difficulty (id),
    CONSTRAINT quizzes_name_uq UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS questions
(
    id      INT AUTO_INCREMENT,
    prompt  VARCHAR(200) NOT NULL,
    quiz_id INT          NOT NULL,

    CONSTRAINT questions_pk PRIMARY KEY (id),
    CONSTRAINT questions_quizzes_fk FOREIGN KEY (quiz_id) REFERENCES quizzes (id)
);

CREATE TABLE IF NOT EXISTS answers
(
    id          INT AUTO_INCREMENT,
    answer      CHAR(1) NOT NULL,
    question_id INT     NOT NULL,

    CONSTRAINT answers_pk PRIMARY KEY (id),
    CONSTRAINT answers_questions_fk FOREIGN KEY (question_id) REFERENCES questions (id)
);

CREATE TABLE IF NOT EXISTS variants
(
    id          INT AUTO_INCREMENT,
    variant     VARCHAR(30) NOT NULL,
    question_id INT         NOT NULL,

    CONSTRAINT variants_pk PRIMARY KEY (id),
    CONSTRAINT variants_questions_fk FOREIGN KEY (question_id) REFERENCES questions (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       INT AUTO_INCREMENT,
    login    VARCHAR(30)  NOT NULL,
    email    VARCHAR(30)  NOT NULL,
    password VARCHAR(255) NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_login_uq UNIQUE (login),
    CONSTRAINT users_email_uq UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS roles
(
    id        INT AUTO_INCREMENT,
    role_type VARCHAR(30) NOT NULL,
    user_id   INT         NOT NULL,

    CONSTRAINT roles_pk PRIMARY KEY (id),
    CONSTRAINT roles_users_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS users_quizzes
(
    user_id INT,
    quiz_id INT,
    score   INT NOT NULL DEFAULT '0',

    CONSTRAINT users_quizzes_pk PRIMARY KEY (user_id, quiz_id),
    CONSTRAINT users_quizzes_users_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT users_quizzes_quizzes_fk FOREIGN KEY (quiz_id) REFERENCES quizzes (id)
);
