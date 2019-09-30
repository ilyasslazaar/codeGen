-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 27 sep. 2019 à 15:16
-- Version du serveur :  5.7.24
-- Version de PHP :  7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `convertapp`
--

-- --------------------------------------------------------

--
-- Structure de la table `argumts`
--

DROP TABLE IF EXISTS `argumts`;
CREATE TABLE IF NOT EXISTS `argumts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `fonctions_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_argumts_fonctions_id` (`fonctions_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `base_class`
--

DROP TABLE IF EXISTS `base_class`;
CREATE TABLE IF NOT EXISTS `base_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `imports` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `builder_plate`
--

DROP TABLE IF EXISTS `builder_plate`;
CREATE TABLE IF NOT EXISTS `builder_plate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `default_code` longtext,
  `base_class_id` bigint(20) DEFAULT NULL,
  `ref_code_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_builder_plate_base_class_id` (`base_class_id`),
  KEY `fk_builder_plate_ref_code_id` (`ref_code_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
CREATE TABLE IF NOT EXISTS `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2019-09-23 17:51:31', 1, 'EXECUTED', '7:9d88ecd533d5a3530e304f778b9dc982', 'createTable tableName=jhi_persistent_audit_event; createTable tableName=jhi_persistent_audit_evt_data; addPrimaryKey tableName=jhi_persistent_audit_evt_data; createIndex indexName=idx_persistent_audit_event, tableName=jhi_persistent_audit_event; c...', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133516-1', 'jhipster', 'config/liquibase/changelog/20190923133516_added_entity_Proprietes.xml', '2019-09-23 17:51:31', 2, 'EXECUTED', '7:13198ee443c8c58bcc173c3f1806b5fd', 'createTable tableName=proprietes', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133517-1', 'jhipster', 'config/liquibase/changelog/20190923133517_added_entity_Langages.xml', '2019-09-23 17:51:31', 3, 'EXECUTED', '7:56e21b8572521a615fa3d8d0cf42c5e2', 'createTable tableName=langages', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133518-1', 'jhipster', 'config/liquibase/changelog/20190923133518_added_entity_Argumts.xml', '2019-09-23 17:51:32', 4, 'EXECUTED', '7:b2a681e346d80bd1828c190fc34fe141', 'createTable tableName=argumts', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133519-1', 'jhipster', 'config/liquibase/changelog/20190923133519_added_entity_Fonctions.xml', '2019-09-23 17:51:32', 5, 'EXECUTED', '7:ebf33af0e5401cb22e4486515e0aeefe', 'createTable tableName=fonctions', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133520-1', 'jhipster', 'config/liquibase/changelog/20190923133520_added_entity_BaseClass.xml', '2019-09-23 17:51:32', 6, 'EXECUTED', '7:8443924045fb8e724df791df6c2a35c6', 'createTable tableName=base_class', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133521-1', 'jhipster', 'config/liquibase/changelog/20190923133521_added_entity_RefCode.xml', '2019-09-23 17:51:32', 7, 'EXECUTED', '7:0ff3cf0b337b4c8d1dd976b850319a1d', 'createTable tableName=ref_code', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133522-1', 'jhipster', 'config/liquibase/changelog/20190923133522_added_entity_BuilderPlate.xml', '2019-09-23 17:51:33', 8, 'EXECUTED', '7:afd68eccbff4325286c92b85c165367c', 'createTable tableName=builder_plate', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133516-2', 'jhipster', 'config/liquibase/changelog/20190923133516_added_entity_constraints_Proprietes.xml', '2019-09-23 17:51:33', 9, 'EXECUTED', '7:e8283b935eea4095d1aa4a8c866acd3e', 'addForeignKeyConstraint baseTableName=proprietes, constraintName=fk_proprietes_base_class_id, referencedTableName=base_class', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133517-2', 'jhipster', 'config/liquibase/changelog/20190923133517_added_entity_constraints_Langages.xml', '2019-09-23 17:51:33', 10, 'EXECUTED', '7:9ffb75000146b4e21bd23af0e06a0f81', 'addForeignKeyConstraint baseTableName=langages, constraintName=fk_langages_base_class_id, referencedTableName=base_class', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133518-2', 'jhipster', 'config/liquibase/changelog/20190923133518_added_entity_constraints_Argumts.xml', '2019-09-23 17:51:33', 11, 'EXECUTED', '7:6de8923be4d0aded76b26714c31f9eaf', 'addForeignKeyConstraint baseTableName=argumts, constraintName=fk_argumts_fonctions_id, referencedTableName=fonctions', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133519-2', 'jhipster', 'config/liquibase/changelog/20190923133519_added_entity_constraints_Fonctions.xml', '2019-09-23 17:51:33', 12, 'EXECUTED', '7:40e4e6a979375a5c1f0e7c5debb360ea', 'addForeignKeyConstraint baseTableName=fonctions, constraintName=fk_fonctions_base_class_id, referencedTableName=base_class', '', NULL, '3.5.4', NULL, NULL, '9257490672'),
('20190923133522-2', 'jhipster', 'config/liquibase/changelog/20190923133522_added_entity_constraints_BuilderPlate.xml', '2019-09-23 17:51:33', 13, 'EXECUTED', '7:2093d31ae96de946fc0f3ca7b9b7d861', 'addForeignKeyConstraint baseTableName=builder_plate, constraintName=fk_builder_plate_base_class_id, referencedTableName=base_class; addForeignKeyConstraint baseTableName=builder_plate, constraintName=fk_builder_plate_ref_code_id, referencedTableNa...', '', NULL, '3.5.4', NULL, NULL, '9257490672');

-- --------------------------------------------------------

--
-- Structure de la table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
CREATE TABLE IF NOT EXISTS `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `fonctions`
--

DROP TABLE IF EXISTS `fonctions`;
CREATE TABLE IF NOT EXISTS `fonctions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `base_class_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fonctions_base_class_id` (`base_class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_audit_event`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
CREATE TABLE IF NOT EXISTS `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
CREATE TABLE IF NOT EXISTS `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `langages`
--

DROP TABLE IF EXISTS `langages`;
CREATE TABLE IF NOT EXISTS `langages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `base_class_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_langages_base_class_id` (`base_class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `proprietes`
--

DROP TABLE IF EXISTS `proprietes`;
CREATE TABLE IF NOT EXISTS `proprietes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `base_class_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_proprietes_base_class_id` (`base_class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `ref_code`
--

DROP TABLE IF EXISTS `ref_code`;
CREATE TABLE IF NOT EXISTS `ref_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `langage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `ref_code`
--

INSERT INTO `ref_code` (`id`, `langage`) VALUES
(1, 'PHP'),
(2, 'JS'),
(3, 'JAVA');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
