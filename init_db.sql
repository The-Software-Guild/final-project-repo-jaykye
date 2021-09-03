-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema VenueExplorerDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `VenueExplorerDB` ;

-- -----------------------------------------------------
-- Schema VenueExplorerDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `VenueExplorerDB` DEFAULT CHARACTER SET utf8 ;
USE `VenueExplorerDB` ;

-- -----------------------------------------------------
-- Table `VenueExplorerDB`.`venue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `VenueExplorerDB`.`venue` ;

CREATE TABLE IF NOT EXISTS `VenueExplorerDB`.`venue` (
  `venueId` VARCHAR(25) NOT NULL,
  `venueName` VARCHAR(45) NULL,
  `isSavedFavorite` TINYINT NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `country` VARCHAR(45) NULL,
  `latitude` DOUBLE NULL,
  `longitude` DOUBLE NULL,
  PRIMARY KEY (`venueId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VenueExplorerDB`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `VenueExplorerDB`.`category` ;

CREATE TABLE IF NOT EXISTS `VenueExplorerDB`.`category` (
  `categoryId` VARCHAR(25) NOT NULL,
  `categoryName` VARCHAR(45) NULL,
  PRIMARY KEY (`categoryId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VenueExplorerDB`.`searchHistory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `VenueExplorerDB`.`searchHistory` ;

CREATE TABLE IF NOT EXISTS `VenueExplorerDB`.`searchHistory` (
  `searchHistoryId` INT NOT NULL AUTO_INCREMENT,
  `searchDatetime` DATETIME NULL,
  `venueId` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`searchHistoryId`),
  INDEX `fk_searchHistory_venue1_idx` (`venueId` ASC) VISIBLE,
  CONSTRAINT `fk_searchHistory_venue1`
    FOREIGN KEY (`venueId`)
    REFERENCES `VenueExplorerDB`.`venue` (`venueId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VenueExplorerDB`.`venue_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `VenueExplorerDB`.`venue_category` ;

CREATE TABLE IF NOT EXISTS `VenueExplorerDB`.`venue_category` (
  `venueId` VARCHAR(25) NOT NULL,
  `categoryId` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`venueId`, `categoryId`),
  INDEX `fk_venue_has_category_category1_idx` (`categoryId` ASC) VISIBLE,
  INDEX `fk_venue_has_category_venue1_idx` (`venueId` ASC) VISIBLE,
  CONSTRAINT `fk_venue_has_category_venue1`
    FOREIGN KEY (`venueId`)
    REFERENCES `VenueExplorerDB`.`venue` (`venueId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venue_has_category_category1`
    FOREIGN KEY (`categoryId`)
    REFERENCES `VenueExplorerDB`.`category` (`categoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
