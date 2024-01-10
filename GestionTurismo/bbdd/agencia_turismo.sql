-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-01-2024 a las 06:59:49
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agencia_turismo`
--
CREATE DATABASE IF NOT EXISTS `agencia_turismo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `agencia_turismo`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `flight_number` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_full` bit(1) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `status_change_dates` varbinary(255) DEFAULT NULL,
  `total_seats` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`id`, `date`, `destination`, `flight_number`, `is_active`, `is_full`, `origin`, `status_change_dates`, `total_seats`) VALUES
(1, '2024-01-26', 'Marsella', 'SEMA-2601000001', b'1', b'0', 'Sevilla', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000017704000000017372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007e8010a7878, 3),
(2, '2024-01-27', 'Sevilla', 'MASE-2701000002', b'1', b'0', 'Marsella', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000017704000000017372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007e8010a7878, 4),
(3, '2024-01-25', 'Buenos Aires', 'MABA-2501000003', b'1', b'0', 'Madrid', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000017704000000017372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007e8010a7878, 8),
(4, '2024-01-26', 'Madrid', 'BAMA-2601000004', b'1', b'0', 'Buenos Aires', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000017704000000017372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007e8010a7878, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_reservation`
--

DROP TABLE IF EXISTS `flight_reservation`;
CREATE TABLE `flight_reservation` (
  `id` bigint(20) NOT NULL,
  `date_flight_back` date DEFAULT NULL,
  `date_flight_to` date DEFAULT NULL,
  `flight_back_code` varchar(255) DEFAULT NULL,
  `flight_to_code` varchar(255) DEFAULT NULL,
  `passengers_number` int(11) DEFAULT NULL,
  `seat_type_flight_back` tinyint(4) DEFAULT NULL,
  `seat_type_flight_to` tinyint(4) DEFAULT NULL,
  `total_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight_reservation`
--

INSERT INTO `flight_reservation` (`id`, `date_flight_back`, `date_flight_to`, `flight_back_code`, `flight_to_code`, `passengers_number`, `seat_type_flight_back`, `seat_type_flight_to`, `total_price`) VALUES
(1, NULL, '2024-01-26', '', 'SEMA-2601000001', 1, NULL, 2, 500),
(2, '2024-01-26', '2024-01-25', 'BAMA-2601000004', 'MABA-2501000003', 2, 0, 2, 1400);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_reservation_flights`
--

DROP TABLE IF EXISTS `flight_reservation_flights`;
CREATE TABLE `flight_reservation_flights` (
  `flight_reservation_id` bigint(20) NOT NULL,
  `flight_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_reservation_passengers`
--

DROP TABLE IF EXISTS `flight_reservation_passengers`;
CREATE TABLE `flight_reservation_passengers` (
  `flight_reservation_id` bigint(20) NOT NULL,
  `person_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight_reservation_passengers`
--

INSERT INTO `flight_reservation_passengers` (`flight_reservation_id`, `person_id`) VALUES
(1, 3),
(2, 4),
(2, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status_change_dates` varbinary(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`id`, `city`, `hotel_code`, `is_active`, `name`, `status_change_dates`) VALUES
(1, 'Barcelona', 'ME-0000001', b'1', 'Melia', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000017704000000017372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007e8010a7878),
(2, 'La Coruña', 'TM-0000002', b'1', 'Trip Maria Pita', 0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000017704000000017372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007e8010a7878);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_reservation`
--

DROP TABLE IF EXISTS `hotel_reservation`;
CREATE TABLE `hotel_reservation` (
  `id` bigint(20) NOT NULL,
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `guests_number` int(11) DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `nights` int(11) DEFAULT NULL,
  `room_type` tinyint(4) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel_reservation`
--

INSERT INTO `hotel_reservation` (`id`, `check_in_date`, `check_out_date`, `guests_number`, `hotel_code`, `nights`, `room_type`, `total_price`, `room_id`) VALUES
(1, '2024-07-01', '2024-08-01', 2, 'TM-0000002', 31, 1, 10850, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_reservation_guests`
--

DROP TABLE IF EXISTS `hotel_reservation_guests`;
CREATE TABLE `hotel_reservation_guests` (
  `hotel_reservation_id` bigint(20) NOT NULL,
  `person_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel_reservation_guests`
--

INSERT INTO `hotel_reservation_guests` (`hotel_reservation_id`, `person_id`) VALUES
(1, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `person`
--

INSERT INTO `person` (`id`, `dni`, `email`, `last_name`, `name`, `phone`) VALUES
(1, '12345698G', 'john.doe@example.com', 'Doe', 'John', '123-456-7890'),
(2, '98563214K', 'jane.doe@example.com', 'Doe', 'Jane', '098-765-4321'),
(3, '12542364R', 'mark.roque@example.com', 'Roque', 'Mark', '324321563'),
(4, '26942378F', 'maria.depaula@example.com', 'De Paula', 'Maria', '324321563'),
(5, '56342364T', 'luis.sanchez@example.com', 'Sanchez', 'Luis', '324321563');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `available_from` date DEFAULT NULL,
  `available_to` date DEFAULT NULL,
  `price_per_night` double DEFAULT NULL,
  `room_type` enum('SINGLE','DOUBLE','TRIPLE','MULTIPLE') DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`id`, `available_from`, `available_to`, `price_per_night`, `room_type`, `hotel_id`) VALUES
(1, '2024-01-01', '2024-12-31', 350, 'TRIPLE', 2),
(2, '2024-01-01', '2024-12-31', 350, 'TRIPLE', 2),
(3, '2024-06-01', '2024-12-31', 350, 'DOUBLE', 2),
(4, '2024-06-01', '2024-12-31', 350, 'DOUBLE', 2),
(5, '2025-06-01', '2025-12-31', 500, 'TRIPLE', 2),
(6, '2025-06-01', '2025-12-31', 500, 'TRIPLE', 2),
(7, '2024-01-01', '2024-12-31', 450, 'TRIPLE', 2),
(8, '2024-01-01', '2024-12-31', 450, 'TRIPLE', 2),
(9, '2024-01-01', '2024-12-31', 450, 'TRIPLE', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seat_prices`
--

DROP TABLE IF EXISTS `seat_prices`;
CREATE TABLE `seat_prices` (
  `flight_id` bigint(20) NOT NULL,
  `price` double DEFAULT NULL,
  `seat_type` enum('TOURIST','PREMIUM_TOURIST','BUSINESS') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `seat_prices`
--

INSERT INTO `seat_prices` (`flight_id`, `price`, `seat_type`) VALUES
(1, 200, 'TOURIST'),
(1, 350, 'PREMIUM_TOURIST'),
(1, 500, 'BUSINESS'),
(2, 200, 'TOURIST'),
(2, 350, 'PREMIUM_TOURIST'),
(2, 500, 'BUSINESS'),
(3, 200, 'TOURIST'),
(3, 350, 'PREMIUM_TOURIST'),
(3, 500, 'BUSINESS'),
(4, 200, 'TOURIST'),
(4, 350, 'PREMIUM_TOURIST'),
(4, 500, 'BUSINESS');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `flight_reservation`
--
ALTER TABLE `flight_reservation`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `flight_reservation_flights`
--
ALTER TABLE `flight_reservation_flights`
  ADD KEY `FKipv7wg8pwuo9bnk949df9pdjh` (`flight_id`),
  ADD KEY `FKgqcgu2vsf4foib99a7kx1hayj` (`flight_reservation_id`);

--
-- Indices de la tabla `flight_reservation_passengers`
--
ALTER TABLE `flight_reservation_passengers`
  ADD KEY `FKgk7hnjl6gck2kvmcd8auv6ca3` (`person_id`),
  ADD KEY `FKcvtt62e3w9fkwmmfv1fa2hub1` (`flight_reservation_id`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `hotel_reservation`
--
ALTER TABLE `hotel_reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbnkdnwiybemajygwdyon4kv2n` (`room_id`);

--
-- Indices de la tabla `hotel_reservation_guests`
--
ALTER TABLE `hotel_reservation_guests`
  ADD KEY `FK69tcoehduduihivwsetbbqqdf` (`person_id`),
  ADD KEY `FKms8eyrlvmaxduhs6fi49c766j` (`hotel_reservation_id`);

--
-- Indices de la tabla `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`);

--
-- Indices de la tabla `seat_prices`
--
ALTER TABLE `seat_prices`
  ADD PRIMARY KEY (`flight_id`,`seat_type`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `flight`
--
ALTER TABLE `flight`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `flight_reservation`
--
ALTER TABLE `flight_reservation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `hotel_reservation`
--
ALTER TABLE `hotel_reservation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `person`
--
ALTER TABLE `person`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `flight_reservation_flights`
--
ALTER TABLE `flight_reservation_flights`
  ADD CONSTRAINT `FKgqcgu2vsf4foib99a7kx1hayj` FOREIGN KEY (`flight_reservation_id`) REFERENCES `flight_reservation` (`id`),
  ADD CONSTRAINT `FKipv7wg8pwuo9bnk949df9pdjh` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`);

--
-- Filtros para la tabla `flight_reservation_passengers`
--
ALTER TABLE `flight_reservation_passengers`
  ADD CONSTRAINT `FKcvtt62e3w9fkwmmfv1fa2hub1` FOREIGN KEY (`flight_reservation_id`) REFERENCES `flight_reservation` (`id`),
  ADD CONSTRAINT `FKgk7hnjl6gck2kvmcd8auv6ca3` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`);

--
-- Filtros para la tabla `hotel_reservation`
--
ALTER TABLE `hotel_reservation`
  ADD CONSTRAINT `FKbnkdnwiybemajygwdyon4kv2n` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Filtros para la tabla `hotel_reservation_guests`
--
ALTER TABLE `hotel_reservation_guests`
  ADD CONSTRAINT `FK69tcoehduduihivwsetbbqqdf` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKms8eyrlvmaxduhs6fi49c766j` FOREIGN KEY (`hotel_reservation_id`) REFERENCES `hotel_reservation` (`id`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Filtros para la tabla `seat_prices`
--
ALTER TABLE `seat_prices`
  ADD CONSTRAINT `FKimtcb7xb861tt4s1nmf9i0ay0` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
