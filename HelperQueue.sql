-- phpMyAdmin SQL Dump
-- version 4.0.10.18
-- https://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Nov 19, 2017 at 05:03 PM
-- Server version: 5.6.36-cll-lve
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `HelperQueue`
--

-- --------------------------------------------------------

--
-- Table structure for table `Class`
--

CREATE TABLE IF NOT EXISTS `Class` (
  `classID` int(40) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(7) NOT NULL,
  `num` int(3) NOT NULL,
  PRIMARY KEY (`classID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `Class`
--

INSERT INTO `Class` (`classID`, `prefix`, `num`) VALUES
(1, 'CSCI', 103),
(2, 'CSCI', 104),
(3, 'CSCI', 109),
(4, 'CSCI', 110),
(5, 'CSCI', 170),
(6, 'CSCI', 201),
(7, 'CSCI', 270),
(8, 'CSCI', 280),
(9, 'CSCI', 281),
(10, 'CSCI', 310),
(11, 'CSCI', 350),
(12, 'CSCI', 353),
(13, 'CSCI', 360);

-- --------------------------------------------------------

--
-- Table structure for table `CourseProducers`
--

CREATE TABLE IF NOT EXISTS `CourseProducers` (
  `CPID` int(40) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL,
  `classID` int(3) NOT NULL,
  `running` int(11) NOT NULL,
  `paused` int(11) NOT NULL,
  `timeLeft` int(11) NOT NULL,
  PRIMARY KEY (`CPID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=100 ;

--
-- Dumping data for table `CourseProducers`
--

INSERT INTO `CourseProducers` (`CPID`, `fname`, `lname`, `email`, `classID`, `running`, `paused`, `timeLeft`) VALUES
(1, 'Eric', 'Test', 'lungyouy@usc.edu', 6, 1, 0, 875),
(2, 'Ahmed', 'Test', 'aldulaim@usc.edu', 6, 1, 0, 875),
(3, 'Valerie', 'Test', 'wangvale@usc.edu', 6, 0, 0, 875),
(4, 'Mujahid', 'Test', 'mujahidn@usc.edu', 6, 0, 1, 890),
(5, 'Sahil', 'Test', 'sahilaga@usc.edu', 6, 1, 0, 0),
(6, 'Peter', 'Kaminsky', 'snoopdogg@usc.edu', 6, 1, 0, 875),
(7, 'Helena', 'Rhee', 'helenarh@usc.edu', 6, 0, 0, 875),
(8, 'Michelle', 'Yin', 'yinm@usc.edu', 6, 0, 0, 875),
(9, 'Natalie', 'Monger', 'monger@usc.edu', 201, 0, 0, 875),
(10, 'Janson', 'Lau', 'jansonla@usc.edu', 201, 0, 0, 875),
(11, 'Annie', 'Song', 'annieson@usc.edu', 201, 0, 0, 875),
(12, 'Ruth', 'Libowsky', 'libowsky@usc.edu', 201, 0, 0, 875),
(99, 'askjdsalkjd', 'iokl', 'ahdkfhds@asd.com', 9087, 0, 0, 875),
(13, 'Ahmedz', 'Al Dulaimy', 'aldulaim@usc.edu', 6, 0, 0, 875);

-- --------------------------------------------------------

--
-- Table structure for table `Queue`
--

CREATE TABLE IF NOT EXISTS `Queue` (
  `idQueue` int(11) NOT NULL AUTO_INCREMENT,
  `helperID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`idQueue`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Dumping data for table `Queue`
--

INSERT INTO `Queue` (`idQueue`, `helperID`, `studentID`, `position`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(15, 1, 1, 4),
(16, 2, 1, 3),
(23, 5, 1, 3),
(22, 5, 1, 2),
(21, 6, 1, 2),
(20, 6, 1, 1),
(19, 2, 1, 4),
(18, 5, 1, 1),
(17, 1, 1, 5),
(24, 5, 1, 4),
(25, 6, 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `Students`
--

CREATE TABLE IF NOT EXISTS `Students` (
  `stuID` int(40) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `email` varchar(256) NOT NULL,
  PRIMARY KEY (`stuID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `Students`
--

INSERT INTO `Students` (`stuID`, `fname`, `lname`, `email`) VALUES
(1, 'John', 'Doe', 'john@usc.edu'),
(2, 'Kevin', 'Doe', 'kevin@usc.edu'),
(3, 'Johnny', 'Doe', 'johnny@usc.edu'),
(4, 'Ben', 'Doe', 'ben@usc.edu'),
(5, 'Jeff', 'Doe', 'jeff@usc.edu'),
(6, 'Aaron', 'Doe', 'aaron@usc.edu'),
(8, 'Valerie', 'Doe', 'valerie@usc.edu'),
(9, 'Ahmed', 'Al Dulaimy', 'ahmed@usc.edu'),
(10, 'Eric', 'Doe', 'eric@usc.edu'),
(11, 'Sahl', 'Doe', 'sahl@usc.edu'),
(12, 'Jimmy', 'John', 'jimmy@usc.edu');

-- --------------------------------------------------------

--
-- Table structure for table `UnconfirmedCourseProducers`
--

CREATE TABLE IF NOT EXISTS `UnconfirmedCourseProducers` (
  `email` varchar(256) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `UnconfirmedCourseProducers`
--

INSERT INTO `UnconfirmedCourseProducers` (`email`) VALUES
('aldulaim@usc.edu');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
