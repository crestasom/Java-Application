-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 18, 2016 at 02:44 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `college_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `edges`
--

CREATE TABLE IF NOT EXISTS `edges` (
`id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `source_stop` varchar(45) DEFAULT NULL,
  `destination_stop` varchar(45) DEFAULT NULL,
  `distance` double(4,2) DEFAULT '0.00',
  `oneway` tinyint(1) DEFAULT NULL,
  `postDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `edges`
--

INSERT INTO `edges` (`id`, `name`, `source_stop`, `destination_stop`, `distance`, `oneway`, `postDate`) VALUES
(1, 'a-b', '1', '2', 5.00, 0, '2016-08-16 10:08:41'),
(2, 'b-d', '2', '3', 2.00, 0, '2016-08-16 10:09:07'),
(3, 'a-c', '1', '4', 3.00, 0, '2016-08-16 10:11:21'),
(4, 'b-e', '2', '5', 8.00, 0, '2016-08-16 10:14:31'),
(5, 'c-f', '4', '14', 7.00, 0, '2016-08-16 10:33:35'),
(6, 'd-g', '3', '15', 5.00, 0, '2016-08-16 10:34:01'),
(7, 'f-g', '14', '15', 4.00, 0, '2016-08-16 10:34:30'),
(8, 'e-h', '5', '16', 6.00, 0, '2016-08-16 10:34:56'),
(9, 'h-j', '16', '18', 4.00, 1, '2016-08-16 10:35:24'),
(10, 'i-j', '17', '18', 9.00, 0, '2016-08-16 10:35:58'),
(11, 'g-i', '15', '17', 3.00, 0, '2016-08-16 11:10:57'),
(12, 'Jorpati-boudha', '19', '20', 1.13, 0, '2016-08-18 03:58:14'),
(13, 'Boudha-Tusal', '20', '21', 0.64, 0, '2016-08-18 03:58:44');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(3);

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE IF NOT EXISTS `route` (
`id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `edges` varchar(255) DEFAULT NULL,
  `postDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='			';

-- --------------------------------------------------------

--
-- Table structure for table `stops`
--

CREATE TABLE IF NOT EXISTS `stops` (
`id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `latCode` double DEFAULT NULL,
  `longCode` double DEFAULT NULL,
  `postDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stops`
--

INSERT INTO `stops` (`id`, `name`, `latCode`, `longCode`, `postDate`) VALUES
(1, 'a', NULL, NULL, '2016-08-13 07:44:59'),
(2, 'b', NULL, NULL, '2016-08-13 07:44:59'),
(3, 'd', NULL, NULL, '2016-08-13 10:52:39'),
(4, 'c', 23.343, 23.3434, '2016-08-13 11:19:48'),
(5, 'e', 0, 0, '2016-08-13 11:20:03'),
(14, 'f', 0, 0, '2016-08-16 10:29:43'),
(15, 'g', 0, 0, '2016-08-16 10:29:54'),
(16, 'h', 0, 0, '2016-08-16 10:30:08'),
(17, 'i', 0, 0, '2016-08-16 10:30:20'),
(18, 'j', 0, 0, '2016-08-16 10:30:30'),
(19, 'Jorpati', 27.721612, 85.372459, '2016-08-18 03:52:31'),
(20, 'Boudha', 27.720729, 85.361784, '2016-08-18 03:53:43'),
(21, 'tusal', 27.720142, 85.3579, '2016-08-18 03:55:59');

-- --------------------------------------------------------

--
-- Table structure for table `vertex`
--

CREATE TABLE IF NOT EXISTS `vertex` (
  `id` int(11) NOT NULL,
  `latCode` double NOT NULL,
  `longCode` double NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vertex`
--

INSERT INTO `vertex` (`id`, `latCode`, `longCode`, `name`) VALUES
(1, 23.343, 75.343, 'hari'),
(2, 23.343, 75.343, 'hari');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `edges`
--
ALTER TABLE `edges`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `route`
--
ALTER TABLE `route`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stops`
--
ALTER TABLE `stops`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `name_UNIQUE` (`name`);

--
-- Indexes for table `vertex`
--
ALTER TABLE `vertex`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `edges`
--
ALTER TABLE `edges`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `route`
--
ALTER TABLE `route`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `stops`
--
ALTER TABLE `stops`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
