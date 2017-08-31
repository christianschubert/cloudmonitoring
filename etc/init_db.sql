CREATE DATABASE IF NOT EXISTS `monitoring` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `monitoring`;

GRANT ALL ON `monitoring`.* TO 'monitoring'@'localhost' IDENTIFIED BY '12345';

CREATE TABLE IF NOT EXISTS `violation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_ip_address` varchar(32) NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `violation_type` enum('CPU_LOAD','MEM_RESIDENT','MEM_TOTAL','RESPONSE_TIME', 'SUCCESSABILITY', 'THROUGHPUT') NOT NULL,
  `monitored_value` float NOT NULL,
  `required_desc` varchar(255) NOT NULL,
  `violation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;