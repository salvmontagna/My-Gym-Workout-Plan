-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 11, 2017 alle 15:29
-- Versione del server: 10.1.21-MariaDB
-- Versione PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mygym`
--

-- --------------------------------------------------------

CREATE TABLE `exercise` (
  `id` int(11) NOT NULL,
  `exercise` varchar(40) NOT NULL,
  `Muscle_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `exercise` (`id`, `exercise`, `Muscle_id`) VALUES
(1, 'Panca piana', 0),
(2, 'Panca inclinata', 0),
(3, 'Distensioni panca piana', 0),
(4, 'Distensioni panca inclinata', 0),
(5, 'Croci panca piana', 0),
(6, 'Croci panca inclinata', 0),
(7, 'Chest Press', 0),
(8, 'Pectoral Machine', 0),
(9, 'Pull Over', 0),
(10, 'Piegamenti', 0),
(11, 'Deep alle parallele', 0),
(12, 'Curl coi manubri', 1),
(16, 'curl coi manubri su panca', 1),
(17, 'Curl col bilanciere', 1),
(18, 'Curl col bilanciere curvo', 1),
(19, 'Curl col bilanciere su panca scott', 1),
(20, 'Curl hammer', 1),
(21, 'Curl ai cavi', 1),
(22, 'Concentrati', 1),
(23, 'French press con bilanciere', 2),
(24, 'French press con manubri', 2),
(25, 'Estensione dietro la testa con manubrio', 2),
(26, 'Distensioni all\'ercolina', 2),
(27, 'Lat machine presa larga', 3),
(28, 'Lat machine presa inversa', 3),
(29, 'Trazioni alla sbarra presa larga', 3),
(30, 'Trazioni alla sbarra presa stretta', 3),
(31, 'Rematori con manubrio', 3),
(32, 'Rematori con bilanciere', 3),
(33, 'Rematori con bilanciere a T', 3),
(34, 'Tirate con manubri', 3),
(35, 'Pulley basso', 3),
(36, 'Pulley alto', 3),
(37, 'Pressa orizzontale', 4),
(38, 'Pressa verticale', 4),
(39, 'Squat su multi-power', 4),
(40, 'Squat con manubri', 4),
(41, 'Squat con bilanciere', 4),
(42, 'Leg Curl', 4),
(43, 'Leg Extension', 4),
(44, 'Calf machine', 4),
(45, 'Affondi con manubri', 4),
(46, 'Affondi con bilanciere', 4),
(47, 'Shoulder Press', 5),
(48, 'Alzate frontali', 5),
(49, 'Alzate laterali', 5),
(50, 'Alzate posteriori', 5),
(51, 'Distensioni con manubri', 5),
(52, 'Trazioni al mento', 5),
(53, 'Panca piana con presa inversa', 5),
(54, 'Scrollate con manubri', 5),
(55, 'Scrollate con bilanciere', 5),
(56, 'Incroci ai cavi bassi', 5),
(57, 'Incroci ai cavi alti', 5),
(58, 'Tirate ai cavi incrociati', 0),
(59, 'Distensioni al multi-power', 0),
(60, 'Estensione con manubrio', 2),
(61, 'Estensione cavo basso', 2),
(62, 'Estensione cavo alto', 2),
(63, 'Deep alle parallele', 2),
(65, 'Adduttor machine', 4),
(66, 'Adduttor disteso', 4),
(67, 'Step-Up', 4),
(68, 'Pull down', 3),
(69, 'Stacchi da terra', 3),
(70, 'Vertical machine', 3),
(71, 'Pulley con Tranzibar', 3),
(72, 'Lento avanti', 5),
(73, 'Lento dietro', 5),
(74, 'Lento avanti', 5),
(75, 'Alzate al cavo basso', 5),
(76, 'Crunch', 9),
(77, 'Crunch', 9),
(78, 'Gambe a raccolta su panca inclinata', 9),
(79, 'Gambe a raccolta sulle parallele', 9),
(80, 'Flessione laterale del busto', 9),
(81, 'Crunch ai cavi in ginocchio', 9),
(82, 'Obliqui al 3b crunch', 9),
(83, 'Sit-Up', 9);

-- --------------------------------------------------------

CREATE TABLE `muscle` (
  `id` int(11) NOT NULL,
  `muscle` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `muscle` (`id`, `muscle`) VALUES
(0, 'Petto'),
(1, 'Bicipiti'),
(2, 'Tricipiti'),
(3, 'Dorsali'),
(4, 'Gambe'),
(5, 'Spalle'),
(9, 'Addominali');

-- --------------------------------------------------------

CREATE TABLE `tab` (
  `id` int(11) NOT NULL,
  `expirationTab` date NOT NULL,
  `Utenti_id` int(11) NOT NULL,
  `monday` tinyint(4) NOT NULL DEFAULT '0',
  `tuesday` tinyint(4) NOT NULL DEFAULT '0',
  `wednesday` tinyint(4) NOT NULL DEFAULT '0',
  `thursday` tinyint(4) NOT NULL DEFAULT '0',
  `friday` tinyint(4) NOT NULL DEFAULT '0',
  `saturday` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


INSERT INTO `tab` (`id`, `expirationTab`, `Utenti_id`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`) VALUES
(2, '2017-06-15', 3, 1, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

CREATE TABLE `tabexercise` (
  `tab_ID` int(11) NOT NULL,
  `user_ID` int(11) NOT NULL,
  `exercise_ID` int(11) NOT NULL,
  `set` int(11) NOT NULL,
  `rep` int(11) NOT NULL,
  `time` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `password` varchar(12) NOT NULL,
  `email` varchar(100) NOT NULL,
  `token` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `password`, `email`, `token`) VALUES
(1, 'sonobello', 'pietruzzo', 'TMK0mgsq5VoknfDW6bNXBdYCIjJDMVceF3W7G84wH'),
(3, 'provola', 'utente', 'dyvmcDgRkQztkvUhotfSf53l7ciiI8rOhKtuvoPqC'),
(4, 'provola', 'montino97@gmail.com', '');

ALTER TABLE `exercise`
  ADD PRIMARY KEY (`id`,`Muscle_id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_Exercise_Muscle1_idx` (`Muscle_id`);

ALTER TABLE `muscle`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

ALTER TABLE `tab`
  ADD PRIMARY KEY (`id`,`Utenti_id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_Scheda_Utenti1_idx` (`Utenti_id`);

ALTER TABLE `tabexercise`
  ADD PRIMARY KEY (`tab_ID`,`user_ID`,`exercise_ID`),
  ADD KEY `fk_Scheda_has_Esercizi_Esercizi1_idx` (`exercise_ID`),
  ADD KEY `fk_Scheda_has_Esercizi_Scheda1_idx` (`tab_ID`,`user_ID`);


ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);


ALTER TABLE `exercise`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

ALTER TABLE `muscle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

ALTER TABLE `tab`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `exercise`
  ADD CONSTRAINT `fk_Exercise_Muscle1` FOREIGN KEY (`Muscle_id`) REFERENCES `muscle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `tab`
  ADD CONSTRAINT `fk_Scheda_Utenti1` FOREIGN KEY (`Utenti_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `tabexercise`
  ADD CONSTRAINT `fk_Scheda_has_Esercizi_Esercizi1` FOREIGN KEY (`exercise_ID`) REFERENCES `exercise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Scheda_has_Esercizi_Scheda1` FOREIGN KEY (`tab_ID`,`user_ID`) REFERENCES `tab` (`id`, `Utenti_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
