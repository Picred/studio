CREATE DATABASE  IF NOT EXISTS `University` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `University`;


DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `codice_corso` int NOT NULL AUTO_INCREMENT,
  `nome_corso` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descrizione` text COLLATE utf8mb4_unicode_ci,
  `crediti` int DEFAULT NULL,
  PRIMARY KEY (`codice_corso`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

LOCK TABLES `courses` WRITE;
INSERT INTO `courses` VALUES (1,'Informatica','Corso di laurea in Informatica',180),(2,'Matematica','Corso di laurea in Matematica',150),(3,'Fisica','Corso di laurea in Fisica',160);
UNLOCK TABLES;