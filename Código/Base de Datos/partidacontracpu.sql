-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2016 a las 02:47:45
-- Versión del servidor: 10.1.19-MariaDB
-- Versión de PHP: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `othellogamebd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidacontracpu`
--

CREATE TABLE `partidacontracpu` (
  `usuario` varchar(20) COLLATE latin1_spanish_ci NOT NULL,
  `juego` varchar(800) COLLATE latin1_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

--
-- Volcado de datos para la tabla `partidacontracpu`
--

INSERT INTO `partidacontracpu` (`usuario`, `juego`) VALUES
('XDrake', '[[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,2,2,3,0,0,0],[0,0,2,2,2,3,0,0],[0,0,3,2,1,0,0,0],[0,0,0,3,3,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0]]');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `partidacontracpu`
--
ALTER TABLE `partidacontracpu`
  ADD PRIMARY KEY (`usuario`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
