-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.6.10-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para admin_cargo
CREATE DATABASE IF NOT EXISTS `admin_cargo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `admin_cargo`;

-- Volcando estructura para tabla admin_cargo.admin_cargo_crontabs
CREATE TABLE IF NOT EXISTS `admin_cargo_crontabs` (
  `acc_config_nombre` varchar(100) DEFAULT NULL,
  `acc_bandera_activo` char(3) DEFAULT NULL,
  `acc_meta_descripcion` varchar(500) DEFAULT NULL,
  `acc_meta_fecha_ult_ejecucion` datetime DEFAULT NULL,
  `acc_tiempo_minuto` varchar(200) DEFAULT NULL,
  `acc_tiempo_hora` varchar(100) DEFAULT NULL,
  `acc_tiempo_diaMes` varchar(100) DEFAULT NULL,
  `acc_tiempo_mes` varchar(50) DEFAULT NULL,
  `acc_tiempo_diaSemana` varchar(25) DEFAULT NULL,
  `acc_ejecucion_servidor` varchar(100) DEFAULT NULL,
  `acc_ejecucion_puerto` varchar(10) DEFAULT NULL,
  `acc_ejecucion_endpoint` varchar(200) DEFAULT NULL,
  `acc_ejecucion_proceso` varchar(50) NOT NULL,
  `acc_ejecucion_timeout` varchar(10) DEFAULT NULL,
  `acc_variable_1` varchar(500) NOT NULL DEFAULT 'N/A',
  `acc_variable_2` varchar(500) NOT NULL DEFAULT 'N/A',
  `acc_variable_3` varchar(500) NOT NULL DEFAULT 'N/A',
  `acc_variable_4` varchar(500) NOT NULL DEFAULT 'N/A',
  `acc_variable_5` varchar(500) NOT NULL DEFAULT 'N/A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla admin_cargo.admin_cargo_crontabs: ~0 rows (aproximadamente)
DELETE FROM `admin_cargo_crontabs`;
INSERT INTO `admin_cargo_crontabs` (`acc_config_nombre`, `acc_bandera_activo`, `acc_meta_descripcion`, `acc_meta_fecha_ult_ejecucion`, `acc_tiempo_minuto`, `acc_tiempo_hora`, `acc_tiempo_diaMes`, `acc_tiempo_mes`, `acc_tiempo_diaSemana`, `acc_ejecucion_servidor`, `acc_ejecucion_puerto`, `acc_ejecucion_endpoint`, `acc_ejecucion_proceso`, `acc_ejecucion_timeout`, `acc_variable_1`, `acc_variable_2`, `acc_variable_3`, `acc_variable_4`, `acc_variable_5`) VALUES
	('prueba', 'V', 'Prueba 1', NULL, '*', '*', '*', '*', '*', 'localhost', '8191', '/cargo/crontabs/ejecutador', 'prueba', '150', 'N/A', 'N/A', 'N/A', 'N/A', 'N/A');

-- Volcando estructura para procedimiento admin_cargo.sp_admin_cargo_crontabs_consultas
DELIMITER //
CREATE PROCEDURE `sp_admin_cargo_crontabs_consultas`(
	IN `i_tipo` CHAR(5),
	IN `i_operacion` CHAR(5),
	IN `i_variable` VARCHAR(50)
)
BEGIN

	IF(i_tipo = 'Q')THEN 
	
		IF(i_operacion = 'QCCL') THEN
			
			SELECT
			acc_config_nombre,
			acc_bandera_activo,
			acc_meta_descripcion,
			acc_meta_fecha_ult_ejecucion,
			acc_tiempo_minuto,
			acc_tiempo_hora,
			acc_tiempo_diaMes,
			acc_tiempo_mes,
			acc_tiempo_diaSemana,
			acc_ejecucion_servidor,
			acc_ejecucion_puerto,
			acc_ejecucion_endpoint,
			acc_ejecucion_proceso,
			acc_ejecucion_timeout
			FROM
			admin_cargo.admin_cargo_crontabs
			WHERE acc_bandera_activo = 'V';
			
		END IF;
		
		IF (i_operacion = 'QCVL') THEN
		
			SELECT
				acc_variable_1,
				acc_variable_2,
				acc_variable_3,
				acc_variable_4,
				acc_variable_5
			FROM
				admin_cargo.admin_cargo_crontabs
			WHERE 
				acc_config_nombre = i_variable;
		
		END IF;
	
	END IF;
	
	


END//
DELIMITER ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
