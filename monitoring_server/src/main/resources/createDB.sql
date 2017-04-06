-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               10.1.22-MariaDB - mariadb.org binary distribution
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Exportiere Datenbank Struktur für monitoring
CREATE DATABASE IF NOT EXISTS `monitoring` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `monitoring`;

-- Exportiere Struktur von Tabelle monitoring.violation
CREATE TABLE IF NOT EXISTS `violation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_ip` varchar(32) NOT NULL,
  `service_name` varchar(32) NOT NULL,
  `violation_type` int(11) NOT NULL,
  `monitored_value` float NOT NULL,
  `required_value` float NOT NULL,
  `violation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_violation_violation_type` (`violation_type`),
  CONSTRAINT `FK_violation_violation_type` FOREIGN KEY (`violation_type`) REFERENCES `violation_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle monitoring.violation_type
CREATE TABLE IF NOT EXISTS `violation_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `violation_type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
