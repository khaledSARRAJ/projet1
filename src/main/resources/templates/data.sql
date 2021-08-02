-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sitebbd
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sitebbd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sitebbd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `sitebbd` ;

-- -----------------------------------------------------
-- Table `sitebbd`.`droits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`droits` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `type_droit` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`sexe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`sexe` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `non_sexe` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`motif_resiliation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`motif_resiliation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `libelle_motift` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`ville`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`ville` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code_postale` VARCHAR(255) NULL DEFAULT NULL,
  `localite` VARCHAR(255) NULL DEFAULT NULL,
  `pays` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`utilisateur` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `complement` VARCHAR(255) NULL DEFAULT NULL,
  `date_inscription` DATE NULL DEFAULT NULL,
  `date_nais` DATE NULL DEFAULT NULL,
  `date_resiliation` DATE NULL DEFAULT NULL,
  `mail` VARCHAR(255) NULL DEFAULT NULL,
  `nom` VARCHAR(255) NULL DEFAULT NULL,
  `passe_word` VARCHAR(255) NULL DEFAULT NULL,
  `prenom` VARCHAR(255) NULL DEFAULT NULL,
  `profile` VARCHAR(255) NULL DEFAULT NULL,
  `rue` VARCHAR(255) NULL DEFAULT NULL,
  `telephone` VARCHAR(255) NULL DEFAULT NULL,
  `motif_resiliation_id` BIGINT(20) NULL DEFAULT NULL,
  `droits_id` BIGINT(20) NULL DEFAULT NULL,
  `sexe_id` BIGINT(20) NULL DEFAULT NULL,
  `ville_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_3b8egvpx5k4bp5h47su1mxdjx` (`mail` ASC) VISIBLE,
  UNIQUE INDEX `UK_dqkbjn3vw4csidjopuh8lw4sg` (`profile` ASC) VISIBLE,
  INDEX `FKkf6wu1gap7y5651nv1ks9f3lo` (`motif_resiliation_id` ASC) VISIBLE,
  INDEX `FK65t0j6svb238dgpjo69q9htls` (`droits_id` ASC) VISIBLE,
  INDEX `FK8ynn57yeyv8mdm5saxyg2xidn` (`sexe_id` ASC) VISIBLE,
  INDEX `FKlexbkgyw22p10gqcu1gxovu45` (`ville_id` ASC) VISIBLE,
  CONSTRAINT `FK65t0j6svb238dgpjo69q9htls`
    FOREIGN KEY (`droits_id`)
    REFERENCES `sitebbd`.`droits` (`id`),
  CONSTRAINT `FK8ynn57yeyv8mdm5saxyg2xidn`
    FOREIGN KEY (`sexe_id`)
    REFERENCES `sitebbd`.`sexe` (`id`),
  CONSTRAINT `FKkf6wu1gap7y5651nv1ks9f3lo`
    FOREIGN KEY (`motif_resiliation_id`)
    REFERENCES `sitebbd`.`motif_resiliation` (`id`),
  CONSTRAINT `FKlexbkgyw22p10gqcu1gxovu45`
    FOREIGN KEY (`ville_id`)
    REFERENCES `sitebbd`.`ville` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`abonnement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`abonnement` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `date_début` DATE NULL DEFAULT NULL,
  `date_fin` DATE NULL DEFAULT NULL,
  `follower_id` BIGINT(20) NULL DEFAULT NULL,
  `following_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKdxrhr4n6yan98su1iar7qgp87` (`follower_id` ASC) VISIBLE,
  INDEX `FK9fri9fcqgy62bsq8ri67mpmej` (`following_id` ASC) VISIBLE,
  CONSTRAINT `FK9fri9fcqgy62bsq8ri67mpmej`
    FOREIGN KEY (`following_id`)
    REFERENCES `sitebbd`.`utilisateur` (`id`),
  CONSTRAINT `FKdxrhr4n6yan98su1iar7qgp87`
    FOREIGN KEY (`follower_id`)
    REFERENCES `sitebbd`.`utilisateur` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`annonce`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`annonce` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `date_parution` DATE NULL DEFAULT NULL,
  `prix` DOUBLE NULL DEFAULT NULL,
  `texte` VARCHAR(255) NULL DEFAULT NULL,
  `titre` VARCHAR(255) NULL DEFAULT NULL,
  `utilisateur_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK7189vsun3jgblxqkayt825j95` (`utilisateur_id` ASC) VISIBLE,
  CONSTRAINT `FK7189vsun3jgblxqkayt825j95`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `sitebbd`.`utilisateur` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`theme` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `titre` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`annonce_theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`annonce_theme` (
  `theme_id` BIGINT(20) NOT NULL,
  `annonce_id` BIGINT(20) NOT NULL,
  INDEX `FK5782v7mkp8073wchkppsiu56y` (`annonce_id` ASC) VISIBLE,
  INDEX `FKfb1cmh9dcr56nf9ia06vg55vy` (`theme_id` ASC) VISIBLE,
  CONSTRAINT `FK5782v7mkp8073wchkppsiu56y`
    FOREIGN KEY (`annonce_id`)
    REFERENCES `sitebbd`.`annonce` (`id`),
  CONSTRAINT `FKfb1cmh9dcr56nf9ia06vg55vy`
    FOREIGN KEY (`theme_id`)
    REFERENCES `sitebbd`.`theme` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`publication`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`publication` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `date_debut` DATE NULL DEFAULT NULL,
  `nb_signalement` BIGINT(20) NULL DEFAULT NULL,
  `texte` VARCHAR(255) NULL DEFAULT NULL,
  `titre` VARCHAR(255) NULL DEFAULT NULL,
  `utilisateur_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKcjyqmjkbmqpn7xtk7i9tueu7c` (`utilisateur_id` ASC) VISIBLE,
  CONSTRAINT `FKcjyqmjkbmqpn7xtk7i9tueu7c`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `sitebbd`.`utilisateur` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`publication_theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`publication_theme` (
  `theme_id` BIGINT(20) NOT NULL,
  `publication_id` BIGINT(20) NOT NULL,
  INDEX `FKei0axevbtg77w3v62qg38tg5b` (`publication_id` ASC) VISIBLE,
  INDEX `FKb59xgtkv1m8uhmii1m37v9tpx` (`theme_id` ASC) VISIBLE,
  CONSTRAINT `FKb59xgtkv1m8uhmii1m37v9tpx`
    FOREIGN KEY (`theme_id`)
    REFERENCES `sitebbd`.`theme` (`id`),
  CONSTRAINT `FKei0axevbtg77w3v62qg38tg5b`
    FOREIGN KEY (`publication_id`)
    REFERENCES `sitebbd`.`publication` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `sitebbd`.`support`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sitebbd`.`support` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `chemin` VARCHAR(255) NULL DEFAULT NULL,
  `image` LONGBLOB NULL DEFAULT NULL,
  `type_support` VARCHAR(255) NULL DEFAULT NULL,
  `id_annonce_support` BIGINT(20) NULL DEFAULT NULL,
  `id_publication_support` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKlqqnmt8p2efny3rkrgxjo5lvj` (`id_annonce_support` ASC) VISIBLE,
  INDEX `FKdget6s30paa5xkb7l2ie8hmtf` (`id_publication_support` ASC) VISIBLE,
  CONSTRAINT `FKdget6s30paa5xkb7l2ie8hmtf`
    FOREIGN KEY (`id_publication_support`)
    REFERENCES `sitebbd`.`publication` (`id`),
  CONSTRAINT `FKlqqnmt8p2efny3rkrgxjo5lvj`
    FOREIGN KEY (`id_annonce_support`)
    REFERENCES `sitebbd`.`annonce` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
