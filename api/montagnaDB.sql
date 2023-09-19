-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: Giu 30, 2017 alle 19:39
-- Versione del server: 5.5.55-0ubuntu0.14.04.1
-- Versione PHP: 5.5.9-1ubuntu4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `montagnaDB`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `exercise`
--

CREATE TABLE IF NOT EXISTS `exercise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exercise` varchar(40) NOT NULL,
  `Muscle_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`Muscle_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_Exercise_Muscle1_idx` (`Muscle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=84 ;

--
-- Dump dei dati per la tabella `exercise`
--

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
(26, 'Distensioni all''ercolina', 2),
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

--
-- Struttura della tabella `muscle`
--

CREATE TABLE IF NOT EXISTS `muscle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `muscle` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dump dei dati per la tabella `muscle`
--

INSERT INTO `muscle` (`id`, `muscle`) VALUES
(0, 'Petto'),
(1, 'Bicipiti'),
(2, 'Tricipiti'),
(3, 'Dorsali'),
(4, 'Gambe'),
(5, 'Spalle'),
(9, 'Addominali');

-- --------------------------------------------------------

--
-- Struttura della tabella `tab`
--

CREATE TABLE IF NOT EXISTS `tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expirationTab` date NOT NULL,
  `Utenti_id` int(11) NOT NULL,
  `monday` tinyint(4) NOT NULL DEFAULT '0',
  `tuesday` tinyint(4) NOT NULL DEFAULT '0',
  `wednesday` tinyint(4) NOT NULL DEFAULT '0',
  `thursday` tinyint(4) NOT NULL DEFAULT '0',
  `friday` tinyint(4) NOT NULL DEFAULT '0',
  `saturday` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`Utenti_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_Scheda_Utenti1_idx` (`Utenti_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=15 ;

--
-- Dump dei dati per la tabella `tab`
--

INSERT INTO `tab` (`id`, `expirationTab`, `Utenti_id`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`) VALUES
(13, '0000-00-00', 41, 1, 0, 1, 0, 1, 0),
(14, '2017-10-04', 42, 1, 0, 1, 1, 0, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `tabexercise`
--

CREATE TABLE IF NOT EXISTS `tabexercise` (
  `tab_ID` int(11) NOT NULL,
  `user_ID` int(11) NOT NULL,
  `exercise_ID` int(11) NOT NULL,
  `set` int(11) NOT NULL,
  `rep` int(11) NOT NULL,
  PRIMARY KEY (`tab_ID`,`user_ID`,`exercise_ID`),
  KEY `fk_Scheda_has_Esercizi_Esercizi1_idx` (`exercise_ID`),
  KEY `fk_Scheda_has_Esercizi_Scheda1_idx` (`tab_ID`,`user_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `tabexercise`
--

INSERT INTO `tabexercise` (`tab_ID`, `user_ID`, `exercise_ID`, `set`, `rep`) VALUES
(13, 41, 2, 4, 5),
(13, 41, 3, 4, 5),
(13, 41, 5, 4, 5),
(13, 41, 12, 4, 5),
(13, 41, 16, 4, 5),
(13, 41, 23, 4, 5),
(13, 41, 26, 4, 5),
(13, 41, 27, 4, 5),
(13, 41, 34, 4, 5),
(13, 41, 40, 4, 5),
(13, 41, 41, 4, 5),
(13, 41, 54, 4, 5),
(13, 41, 56, 4, 5),
(14, 42, 1, 4, 5),
(14, 42, 2, 4, 5),
(14, 42, 4, 4, 5),
(14, 42, 7, 4, 5),
(14, 42, 8, 4, 5),
(14, 42, 12, 4, 5),
(14, 42, 16, 4, 5),
(14, 42, 20, 4, 5),
(14, 42, 21, 4, 5),
(14, 42, 58, 4, 5);

-- --------------------------------------------------------

--
-- Struttura della tabella `tabgroup`
--

CREATE TABLE IF NOT EXISTS `tabgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tabId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `dayNumber` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=451 ;

--
-- Dump dei dati per la tabella `tabgroup`
--

INSERT INTO `tabgroup` (`id`, `tabId`, `groupId`, `dayNumber`) VALUES
(425, 13, 0, 5),
(426, 13, 1, 5),
(431, 14, 0, 5),
(432, 14, 1, 5),
(435, 14, 4, 2),
(436, 14, 4, 2),
(445, 14, 4, 1),
(446, 14, 5, 1),
(447, 14, 2, 3),
(448, 14, 9, 3),
(449, 14, 0, 4),
(450, 14, 1, 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(12) NOT NULL,
  `email` varchar(100) NOT NULL,
  `token` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=46 ;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `password`, `email`, `token`) VALUES
(40, 'provola', 'utente', 'cArr7Iw5y2J4rUwEcmLpTh6T90CrDeOPOggWYM1xP'),
(41, 'provola', 'utentino', 'y7ePfrMdmZKIZ7ohQvlyyhgexT3CzIZ8QeY5FKj1J'),
(42, '1234', 'selene', 'ZN2wQRZPeP2Rvjusz0sStK5r8KtfGpYFd1b3SaT60'),
(43, '1234', 'mariapia', 'JKxgRuw8tBt53HsSvuaIsNAyoGmGPHfzrNPjimrLX');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `exercise`
--
ALTER TABLE `exercise`
  ADD CONSTRAINT `fk_Exercise_Muscle1` FOREIGN KEY (`Muscle_id`) REFERENCES `muscle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `tab`
--
ALTER TABLE `tab`
  ADD CONSTRAINT `fk_Scheda_Utenti1` FOREIGN KEY (`Utenti_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `tabexercise`
--
ALTER TABLE `tabexercise`
  ADD CONSTRAINT `fk_Scheda_has_Esercizi_Esercizi1` FOREIGN KEY (`exercise_ID`) REFERENCES `exercise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Scheda_has_Esercizi_Scheda1` FOREIGN KEY (`tab_ID`, `user_ID`) REFERENCES `tab` (`id`, `Utenti_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
