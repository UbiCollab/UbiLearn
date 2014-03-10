SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `espehel_ubilearn` ;
CREATE SCHEMA IF NOT EXISTS `espehel_ubilearn` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `espehel_ubilearn` ;

-- -----------------------------------------------------
-- Table `espehel_ubilearn`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `espehel_ubilearn`.`User` ;

CREATE  TABLE IF NOT EXISTS `espehel_ubilearn`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `timeCreated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `espehel_ubilearn`.`TrainingStats`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `espehel_ubilearn`.`TrainingStats` ;

CREATE  TABLE IF NOT EXISTS `espehel_ubilearn`.`TrainingStats` (
  `id` INT NOT NULL ,
  `userId` INT NOT NULL ,
  `progress` INT NOT NULL ,
  `score` INT NOT NULL ,
  `gold` INT NOT NULL ,
  `silver` INT NOT NULL ,
  `bronze` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  FOREIGN KEY (`userId`) REFERENCES User(id) )
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;