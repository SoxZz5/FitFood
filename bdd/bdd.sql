-- MySQL Script generated by MySQL Workbench
-- 12/15/16 14:04:53
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema db_a1452a_fitfood
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_a1452a_fitfood
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_a1452a_fitfood` DEFAULT CHARACTER SET utf8 ;
USE `db_a1452a_fitfood` ;

-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`diet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`diet` (
  `iddiet` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`iddiet`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `surname` VARCHAR(20) NOT NULL,
  `pseudo` VARCHAR(15) NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `diet_iddiet` INT NULL,
  PRIMARY KEY (`iduser`, `diet_iddiet`),
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC),
  UNIQUE INDEX `pseudo_UNIQUE` (`pseudo` ASC),
  UNIQUE INDEX `iduser_UNIQUE` (`iduser` ASC),
  INDEX `fk_user_diet1_idx` (`diet_iddiet` ASC),
  CONSTRAINT `fk_user_diet1`
    FOREIGN KEY (`diet_iddiet`)
    REFERENCES `db_a1452a_fitfood`.`diet` (`iddiet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`allergen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`allergen` (
  `idallergen` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idallergen`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`user_allergen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`user_allergen` (
  `user_iduser` INT NOT NULL,
  `allergen_idallergen` INT NOT NULL,
  PRIMARY KEY (`user_iduser`, `allergen_idallergen`),
  INDEX `fk_user_allergen_allergen1_idx` (`allergen_idallergen` ASC),
  INDEX `fk_user_allergen_user1_idx` (`user_iduser` ASC),
  CONSTRAINT `fk_use_allergen_user`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `db_a1452a_fitfood`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_use_allergen_allergen1`
    FOREIGN KEY (`allergen_idallergen`)
    REFERENCES `db_a1452a_fitfood`.`allergen` (`idallergen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`recipe` (
  `idrecipe` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `recipe` LONGTEXT NOT NULL,
  `note` INT NULL,
  `time_cooking` DATETIME NOT NULL,
  `time_heat` DATETIME NOT NULL,
  `level` INT NOT NULL,
  `date` DATE NOT NULL,
  `diet_iddiet` INT NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idrecipe`, `diet_iddiet`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC),
  INDEX `fk_recipe_diet1_idx` (`diet_iddiet` ASC),
  CONSTRAINT `fk_recipe_diet1`
    FOREIGN KEY (`diet_iddiet`)
    REFERENCES `db_a1452a_fitfood`.`diet` (`iddiet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`categorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`categorie` (
  `idcategorie` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcategorie`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`subcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`subcategory` (
  `idsubcategory` INT NOT NULL AUTO_INCREMENT,
  `categorie_idcategorie` INT NOT NULL,
  PRIMARY KEY (`idsubcategory`, `categorie_idcategorie`),
  INDEX `fk_subcategory_categorie1_idx` (`categorie_idcategorie` ASC),
  CONSTRAINT `fk_subcategory_categorie1`
    FOREIGN KEY (`categorie_idcategorie`)
    REFERENCES `db_a1452a_fitfood`.`categorie` (`idcategorie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`ingredient` (
  `idingredient` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `categorie_idcategorie` INT NOT NULL,
  `subcategory_idsubcategory` INT NOT NULL,
  PRIMARY KEY (`idingredient`),
  INDEX `fk_ingredient_categorie1_idx` (`categorie_idcategorie` ASC),
  INDEX `fk_ingredient_subcategory1_idx` (`subcategory_idsubcategory` ASC),
  CONSTRAINT `fk_ingredient_categorie1`
    FOREIGN KEY (`categorie_idcategorie`)
    REFERENCES `db_a1452a_fitfood`.`categorie` (`idcategorie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingredient_subcategory1`
    FOREIGN KEY (`subcategory_idsubcategory`)
    REFERENCES `db_a1452a_fitfood`.`subcategory` (`idsubcategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`ingredient_allergen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`ingredient_allergen` (
  `allergen_idallergen` INT NOT NULL,
  `ingredient_idingredient` INT NOT NULL,
  PRIMARY KEY (`allergen_idallergen`, `ingredient_idingredient`),
  INDEX `fk_ingredient_allergen_ingredient1_idx` (`ingredient_idingredient` ASC),
  CONSTRAINT `fk_ingredient_allergen_allergen1`
    FOREIGN KEY (`allergen_idallergen`)
    REFERENCES `db_a1452a_fitfood`.`allergen` (`idallergen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingredient_allergen_ingredient1`
    FOREIGN KEY (`ingredient_idingredient`)
    REFERENCES `db_a1452a_fitfood`.`ingredient` (`idingredient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`recipe_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`recipe_ingredient` (
  `recipe_idrecipe` INT NOT NULL,
  `ingredient_idingredient` INT NOT NULL,
  `quantity` VARCHAR(45) NULL,
  PRIMARY KEY (`recipe_idrecipe`, `ingredient_idingredient`),
  INDEX `fk_recipe_ingredient_ingredient1_idx` (`ingredient_idingredient` ASC),
  CONSTRAINT `fk_recipe_ingredient_recipe1`
    FOREIGN KEY (`recipe_idrecipe`)
    REFERENCES `db_a1452a_fitfood`.`recipe` (`idrecipe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_ingredient_ingredient1`
    FOREIGN KEY (`ingredient_idingredient`)
    REFERENCES `db_a1452a_fitfood`.`ingredient` (`idingredient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`recipe_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`recipe_user` (
  `user_iduser` INT NOT NULL,
  `recipe_idrecipe` INT NOT NULL,
  PRIMARY KEY (`user_iduser`, `recipe_idrecipe`),
  INDEX `fk_recipe_user_user1_idx` (`user_iduser` ASC),
  INDEX `fk_recipe_user_recipe1_idx` (`recipe_idrecipe` ASC),
  CONSTRAINT `fk_recipe_user_user1`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `db_a1452a_fitfood`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_user_recipe1`
    FOREIGN KEY (`recipe_idrecipe`)
    REFERENCES `db_a1452a_fitfood`.`recipe` (`idrecipe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`recipe_categorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`recipe_categorie` (
  `recipe_idrecipe` INT NOT NULL,
  `categorie_idcategorie` INT NOT NULL,
  PRIMARY KEY (`recipe_idrecipe`),
  INDEX `fk_recipe_categorie_categorie1_idx` (`categorie_idcategorie` ASC),
  CONSTRAINT `fk_recipe_categorie_recipe1`
    FOREIGN KEY (`recipe_idrecipe`)
    REFERENCES `db_a1452a_fitfood`.`recipe` (`idrecipe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_categorie_categorie1`
    FOREIGN KEY (`categorie_idcategorie`)
    REFERENCES `db_a1452a_fitfood`.`categorie` (`idcategorie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_a1452a_fitfood`.`recipe_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_a1452a_fitfood`.`recipe_image` (
  `idrecipe_image` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `recipe_idrecipe` INT NOT NULL,
  PRIMARY KEY (`idrecipe_image`),
  INDEX `fk_recipe_image_recipe1_idx` (`recipe_idrecipe` ASC),
  CONSTRAINT `fk_recipe_image_recipe1`
    FOREIGN KEY (`recipe_idrecipe`)
    REFERENCES `db_a1452a_fitfood`.`recipe` (`idrecipe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
