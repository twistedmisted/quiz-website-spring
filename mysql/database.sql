-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema webapp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema webapp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `webapp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `webapp` ;

-- -----------------------------------------------------
-- Table `webapp`.`quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapp`.`quiz` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `time` INT NOT NULL,
  `difficulty` VARCHAR(30) NOT NULL,
  `subject` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `webapp`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapp`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `prompt` VARCHAR(200) NULL DEFAULT NULL,
  `quiz_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `qus_quiz` (`prompt` ASC, `quiz_id` ASC) VISIBLE,
  INDEX `quiz_fkq` (`quiz_id` ASC) VISIBLE,
  CONSTRAINT `quiz_fkq`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `webapp`.`quiz` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `webapp`.`answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapp`.`answers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `answer` CHAR(1) NOT NULL,
  `question_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `answer` (`answer` ASC, `question_id` ASC) VISIBLE,
  UNIQUE INDEX `unique_question` (`answer` ASC, `question_id` ASC) VISIBLE,
  INDEX `question_id` (`question_id` ASC) VISIBLE,
  CONSTRAINT `answers_ibfk_1`
    FOREIGN KEY (`question_id`)
    REFERENCES `webapp`.`question` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `webapp`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapp`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(30) NOT NULL,
  `login` VARCHAR(30) NOT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `access_level` VARCHAR(30) NULL DEFAULT 'user',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE,
  UNIQUE INDEX `login` (`login` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `webapp`.`users_quizzes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapp`.`users_quizzes` (
  `user_id` INT NULL DEFAULT NULL,
  `quiz_id` INT NULL DEFAULT NULL,
  `score` INT NOT NULL DEFAULT '0',
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_id` (`user_id` ASC, `quiz_id` ASC) VISIBLE,
  INDEX `quiz_fk` (`quiz_id` ASC) VISIBLE,
  CONSTRAINT `quiz_fk`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `webapp`.`quiz` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `webapp`.`user` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 93
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `webapp`.`variants`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `webapp`.`variants` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `variant` VARCHAR(30) NOT NULL,
  `question_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `unique_pair` (`variant` ASC, `question_id` ASC) VISIBLE,
  INDEX `question_fkq` (`question_id` ASC) VISIBLE,
  CONSTRAINT `question_fkq`
    FOREIGN KEY (`question_id`)
    REFERENCES `webapp`.`question` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 74
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
