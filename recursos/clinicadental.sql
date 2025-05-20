-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2025 a las 19:06:21
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `clinicadental`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dentista`
--

CREATE TABLE `dentista` (
  `idDentista` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `nColegiado` varchar(11) NOT NULL,
  `especialidad` varchar(100) NOT NULL,
  `telefono` int(9) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `edad` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dentista`
--

INSERT INTO `dentista` (`idDentista`, `nombre`, `dni`, `nColegiado`, `especialidad`, `telefono`, `fechaNacimiento`, `edad`) VALUES
(1, 'Lucía Fernández López', '47283915K', '', 'Blanqueamientos dentales.', 612345678, '1998-03-12', 27),
(2, 'Carlos Martínez García', '30847261R', '', 'Consultas, carillas dentales de porcelana, ortodoncia con brackets metálicos.', 693214587, '1985-11-07', 39),
(3, 'Marina González Pérez', '59147302B', '', 'Ortodoncias invisibles', 622981340, '2001-06-21', 23),
(4, 'Álvaro Sánchez Rodríguez', '20475839L', '', 'Endodoncias y coronas sobre metal.', 634110876, '1994-01-30', 31),
(5, 'Elena Romero Morales', '31728450Y', '', 'Limpiezas dentales.', 645239781, '1990-09-17', 34),
(6, 'Javier Navarro Torres', '68029347F', '65432-C', 'Extracciones de muelas de juicio.', 677483992, '1979-02-04', 46),
(7, 'Claudia Ortega Jiménez', '15309748Z', '', 'Implantes dentales', 699128473, '2004-12-25', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `idPaciente` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `telefono` int(9) NOT NULL,
  `alergias` varchar(200) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `edad` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`idPaciente`, `nombre`, `dni`, `telefono`, `alergias`, `fechaNacimiento`, `edad`) VALUES
(1, 'Nuria Delgado Varela', '45192367T', 693748120, 'Ninguna', '1995-07-18', 29),
(2, 'Iván Torres Muñoz', '30987456L', 611235498, 'Asma', '1989-02-11', 36),
(3, 'Beatriz Ramos Cordero', '72819305X', 645789203, 'Ninguna', '2000-09-03', 24),
(4, 'Pablo Cano Jiménez', '17482095Z', 688103459, 'Polen', '1992-06-25', 32),
(5, 'Alba Vidal López', '50298413F', 677320884, 'Penicilina', '1987-04-09', 38),
(6, 'Hugo Romero Salas', '21839405B', 622984301, 'Ninguna', '1999-11-28', 25),
(7, 'Paula Márquez Ruiz', '36750128D', 634710269, 'Ninguna', '1996-08-14', 28),
(8, 'Mario Ortega Carrasco', '48920137N', 655184729, 'Ninguna', '1983-05-02', 42),
(9, 'Carmen Peña Aguado', '59438210Y', 699347510, 'Ninguna', '1991-12-19', 33),
(10, 'Alejandro Gil Herrera', '62038571C', 612840973, 'Ninguna', '2002-03-07', 23),
(24, 'Juan Vaquero Perez', '23956923A', 638088251, 'Ninguna', '1990-01-01', 35),
(30, 'Pedro Alcantara Valenzuela', '23497323L', 678732987, 'Ninguna', '2005-03-21', 20),
(31, 'Alfonso Jimenez Ruiz', '26594569P', 708099211, 'Ninguna', '2011-08-11', 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamiento`
--

CREATE TABLE `tratamiento` (
  `idTratamiento` int(10) UNSIGNED NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `precio` decimal(7,2) NOT NULL,
  `idDentista` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tratamiento`
--

INSERT INTO `tratamiento` (`idTratamiento`, `descripcion`, `precio`, `idDentista`) VALUES
(1, 'Ortodoncia invisible (tipo Invisalign)', 3000.00, 3),
(2, 'Implante dental (unidad completa con corona)', 1000.00, 7),
(3, 'Consulta odontológica inicial', 25.00, 2),
(4, 'Limpieza dental profesional', 40.00, 5),
(5, 'Blanqueamiento dental (láser o LED)', 150.00, 1),
(6, 'Obturación (empaste) resina estética', 50.00, 2),
(7, 'Tratamiento de conducto (endodoncia)', 150.00, 4),
(9, 'Coronas de porcelana sobre metal', 300.00, 4),
(10, 'Coronas totalmente cerámicas', 400.00, 2),
(12, 'Carillas dentales de porcelana', 500.00, 2),
(14, 'Ortodoncia con brackets metálicos (tratamiento completo)', 1500.00, 2),
(15, 'Extracción de muela del juicio (quirúrgica)', 120.00, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamientopaciente`
--

CREATE TABLE `tratamientopaciente` (
  `idPaciente` int(10) UNSIGNED NOT NULL,
  `idTratamiento` int(10) UNSIGNED NOT NULL,
  `fechaTratamiento` date NOT NULL DEFAULT current_timestamp(),
  `detalles` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tratamientopaciente`
--

INSERT INTO `tratamientopaciente` (`idPaciente`, `idTratamiento`, `fechaTratamiento`, `detalles`) VALUES
(1, 4, '2025-05-06', 'Limpieza dental profesional - El paciente no mostraba mucha suciedad y fue un proceso rapido.'),
(2, 1, '2025-05-06', 'Ortodoncia invisible (tipo Invisaling)'),
(3, 3, '2025-05-06', 'Consulta odontológica inicial - El paciente volverá'),
(4, 2, '2025-05-06', 'Implante dental (Unidad completa con corona) - El tratamiento fue largo pero se completo con éxito.'),
(5, 9, '2025-05-06', 'Corona de porcelana sobre metal - Buen resultado, sin problemas, a el paciente le gusto el resultado.'),
(6, 10, '2025-05-06', 'Coronas totalmente cerámicas - Se implantaron correctamente, coronas cerámicas de buena calidad, el paciente no se siente molesto con ellas.'),
(7, 15, '2025-05-06', 'Extracción de muela del juicio (quirúrjica) - Se extrajeron los dos molares perfectamente.'),
(8, 7, '2025-05-06', 'Tratamiento de conducto (endodoncia) - Se trato el nervio dañado correctamente, buen resultado'),
(8, 14, '2025-05-15', 'Ortodoncia con brackets metálicos (tratamiento completo) - Buenos resultados.'),
(9, 5, '2025-05-06', 'Blanqueamiento dental (láser) - Tratamiento aplicado con éxito y buen resultado'),
(10, 6, '2025-05-06', 'Obturacion (empaste) resina estética - Se realizo el tratamiento con buenos resultados y sin ser doloroso para el paciente'),
(24, 14, '2025-05-09', 'Ortodoncia con brackets metálicos (tratamiento completo) - El paciente quedo maravillado con los resultados.'),
(30, 6, '2025-05-15', 'Empaste muela picada, el tratamiento se aplico sin ningun problema y el paciente no siente molestia alguna.');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dentista`
--
ALTER TABLE `dentista`
  ADD PRIMARY KEY (`idDentista`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`idPaciente`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  ADD PRIMARY KEY (`idTratamiento`),
  ADD KEY `idDentista` (`idDentista`);

--
-- Indices de la tabla `tratamientopaciente`
--
ALTER TABLE `tratamientopaciente`
  ADD PRIMARY KEY (`idPaciente`,`idTratamiento`,`fechaTratamiento`),
  ADD KEY `idTratamiento` (`idTratamiento`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `dentista`
--
ALTER TABLE `dentista`
  MODIFY `idDentista` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `paciente`
--
ALTER TABLE `paciente`
  MODIFY `idPaciente` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  MODIFY `idTratamiento` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  ADD CONSTRAINT `tratamiento_ibfk_1` FOREIGN KEY (`idDentista`) REFERENCES `dentista` (`idDentista`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `tratamientopaciente`
--
ALTER TABLE `tratamientopaciente`
  ADD CONSTRAINT `tratamientopaciente_ibfk_1` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tratamientopaciente_ibfk_2` FOREIGN KEY (`idTratamiento`) REFERENCES `tratamiento` (`idTratamiento`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
