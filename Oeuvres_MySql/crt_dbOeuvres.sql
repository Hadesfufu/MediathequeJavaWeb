-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Sam 23 Janvier 2016 à 15:53
-- Version du serveur :  5.6.21
-- Version de PHP :  5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `oeuvres`
--
CREATE DATABASE IF NOT EXISTS `oeuvres` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `oeuvres`;
grant all privileges on oeuvres.* to 'oeuvres'@'localhost' identified by 'secret';

DELIMITER $$
--
-- Fonctions
--
CREATE DEFINER=`oeuvres`@`localhost` FUNCTION `generer_pk`(nom_cle char(15)) RETURNS int(11)
BEGIN
   declare valeur int;
   select val_cle into valeur from cles
   where id_cle = nom_cle for update;
    set valeur := valeur + 1;
   update cles set val_cle = valeur where id_cle = nom_cle;
   return(valeur);
END$$

DELIMITER ;
grant select on mysql.proc to 'oeuvres'@'localhost';
-- --------------------------------------------------------

--
-- Structure de la table `adherent`
--

CREATE TABLE IF NOT EXISTS `adherent` (
  `id_adherent` int(11) NOT NULL,
  `nom_adherent` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prenom_adherent` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `adherent`
--

INSERT INTO `adherent` (`id_adherent`, `nom_adherent`, `prenom_adherent`) VALUES
(1, 'Hugo', 'Victor'),
(2, 'Claudel', 'Camille'),
(3, 'Matisse', 'Henri'),
(4, 'Sand', 'Georges'),
(5, 'Balzac', 'Honoré');

-- --------------------------------------------------------

--
-- Structure de la table `oeuvre`
--

CREATE TABLE IF NOT EXISTS `oeuvre` (
  `id_oeuvre` int(11) NOT NULL,
  `id_proprietaire` int(11) NOT NULL,
  `titre` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prix` decimal(11,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `oeuvre`
--

INSERT INTO `oeuvre` (`id_oeuvre`, `id_proprietaire`, `titre`, `prix`) VALUES
(1, 2, 'Le chapeau à plumes', '557.00'),
(2, 3, 'La balançoire', '800.00'),
(3, 4, 'Les oreilles du lapin', '350.00'),
(4, 2, 'Le penseur debout', '1250.00'),
(5, 3, 'Les buveurs de café', '450.00'),
(6, 5, 'La petite liseuse', '500.00'),
(7, 2, 'En avant toutes', '750.00');

--
-- Déclencheurs `oeuvre`
--
DELIMITER //
CREATE TRIGGER `tbi_oeuvre` BEFORE INSERT ON `oeuvre`
 FOR EACH ROW begin
	declare ck_oeuvre_prix condition for sqlstate '45000';
	if (new.prix <= 0) then
		signal ck_oeuvre_prix set message_text = 'ck_oeuvre_prix : prix négatif.';
	end if;
end
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER `tbu_oeuvre` BEFORE UPDATE ON `oeuvre`
 FOR EACH ROW begin
	declare ck_oeuvre_prix condition for sqlstate '45000';
	if (new.prix <= 0) then
		signal ck_oeuvre_prix set message_text = 'ck_oeuvre_prix : prix négatif.';
	end if;
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `cles`
--

CREATE TABLE IF NOT EXISTS `cles` (
  `id_cle` char(15) COLLATE latin1_general_ci NOT NULL,
  `val_cle` int(11) DEFAULT NULL,
  `lib_cle` varchar(80) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Contenu de la table `cles`
--

INSERT INTO `cles` (`id_cle`, `val_cle`, `lib_cle`) VALUES
('OEUVRE', 7, 'Dernier id Oeuvre'),
('PROPRIETAIRE', 6, 'Dernier id Propriétaire');


-- --------------------------------------------------------

--
-- Structure de la table `proprietaire`
--

CREATE TABLE IF NOT EXISTS `proprietaire` (
  `id_proprietaire` int(11) NOT NULL,
  `nom_proprietaire` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prenom_proprietaire` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pwd` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `proprietaire`
--

INSERT INTO `proprietaire` (`id_proprietaire`, `nom_proprietaire`, `prenom_proprietaire`, `login`, `pwd`) VALUES
(1, 'Administrateur', '', 'admin', 'mdp'),
(2, 'Gator', 'Ali', 'gator', 'ali'),
(3, 'Zhette', 'Annie', 'zhette', 'annie'),
(4, 'Auchon', 'Paul', 'auchon', 'paul'),
(5, 'Thimaitre', 'Vincent', 'thimaitre', 'vincent'),
(6, 'Iffique', 'Eléonore', 'iffique', 'eléonore');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE IF NOT EXISTS `reservation` (
  `date_reservation` datetime NOT NULL,
  `id_oeuvre` int(11) NOT NULL,
  `id_adherent` int(11) NOT NULL,
  `statut` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déclencheurs `reservation`
--
DELIMITER //
CREATE TRIGGER `tbi_reservation` BEFORE INSERT ON `reservation`
 FOR EACH ROW begin
	declare ck_reservation_statut condition for sqlstate '45000';
	if (new.statut not in ('Attente','Confirmée')) then
		signal ck_reservation_statut set message_text = 'ck_reservation_statut : la colonne statut doit contenir Attente ou Confirmée.';
	end if;
end
//
DELIMITER ;
DELIMITER //
CREATE TRIGGER `tbu_reservation` BEFORE UPDATE ON `reservation`
 FOR EACH ROW begin
	declare ck_reservation_statut condition for sqlstate '45000';
	if (new.statut not in ('Attente','Confirmée')) then
		signal ck_reservation_statut set message_text = 'ck_reservation_statut : la colonne statut doit contenir Attente ou Confirmée.';
	end if;
end
//
DELIMITER ;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `adherent`
--
ALTER TABLE `adherent`
 ADD PRIMARY KEY (`id_adherent`);

--
-- Index pour la table `oeuvre`
--
ALTER TABLE `oeuvre`
 ADD PRIMARY KEY (`id_oeuvre`), ADD KEY `FK_OEUVRE_PROPRIETAIRE` (`id_proprietaire`);

--
-- Index pour la table `cles`
--
ALTER TABLE `cles`
 ADD PRIMARY KEY (`id_cle`);

--
-- Index pour la table `proprietaire`
--
ALTER TABLE `proprietaire`
 ADD PRIMARY KEY (`id_proprietaire`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
 ADD PRIMARY KEY (`date_reservation`,`id_oeuvre`), ADD KEY `FK_RESERVATION_ADHERENT` (`id_adherent`), ADD KEY `FK_RESERVATION_OEUVRE` (`id_oeuvre`);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `oeuvre`
--
ALTER TABLE `oeuvre`
ADD CONSTRAINT `FK_OEUVRE_PROPRIETAIRE` FOREIGN KEY (`id_proprietaire`) REFERENCES `proprietaire` (`id_proprietaire`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
ADD CONSTRAINT `FK_RESERVATION_ADHERENT` FOREIGN KEY (`id_adherent`) REFERENCES `adherent` (`id_adherent`),
ADD CONSTRAINT `FK_RESERVATION_OEUVRE` FOREIGN KEY (`id_oeuvre`) REFERENCES `oeuvre` (`id_oeuvre`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
