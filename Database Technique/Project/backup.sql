-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `applicant`
--

DROP TABLE IF EXISTS `applicant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applicant` (
  `Applicant_ID` int NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(45) NOT NULL,
  `Last_Name` varchar(45) NOT NULL,
  `Phone_Number` int NOT NULL,
  `Email_Address` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Applicant_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicant`
--

LOCK TABLES `applicant` WRITE;
/*!40000 ALTER TABLE `applicant` DISABLE KEYS */;
INSERT INTO `applicant` VALUES (1,'Kevin','Fin',73212,'kevin.fin@mail.com','password@123'),(2,'dummy1','dummy_lastname1',8262811,'dummy1@mail.com','strongpassword1'),(3,'dummy2','dummy_lastname2',8262812,'dummy1@mail.com','strongpassword2'),(4,'dummy3','dummy_lastname3',8262813,'dummy1@mail.com','strongpassword3'),(5,'dummy4','dummy_lastname4',8262814,'dummy1@mail.com','strongpassword4'),(6,'Kevin','Fin',73212,'kevin.fin@mail.com','password@123');
/*!40000 ALTER TABLE `applicant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `Application_ID` int NOT NULL AUTO_INCREMENT,
  `Applicant_ID` int NOT NULL,
  `Course_ID` int NOT NULL,
  `University_ID` int NOT NULL,
  `Date` varchar(45) NOT NULL,
  `Application_Name` varchar(45) NOT NULL,
  `Visa` enum('F1','F2','F3') DEFAULT NULL,
  PRIMARY KEY (`Application_ID`),
  KEY `applicant_id_idx` (`Applicant_ID`),
  KEY `course_id_idx` (`Course_ID`),
  KEY `university_id_idx` (`University_ID`),
  CONSTRAINT `applicants_id` FOREIGN KEY (`Applicant_ID`) REFERENCES `applicant` (`Applicant_ID`),
  CONSTRAINT `course_id` FOREIGN KEY (`Course_ID`) REFERENCES `course` (`Course_ID`),
  CONSTRAINT `university_id` FOREIGN KEY (`University_ID`) REFERENCES `university` (`University_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `Course_ID` int NOT NULL AUTO_INCREMENT,
  `University_ID` int NOT NULL,
  `Credits` int NOT NULL,
  `Duration` int NOT NULL,
  `Tuition` float NOT NULL,
  `Type` enum(' Bachelor','Master') NOT NULL,
  `Start_Date` varchar(45) NOT NULL,
  `Teaching_Speed` int NOT NULL,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Course_ID`),
  KEY `university_id_idx` (`University_ID`),
  CONSTRAINT `universitys_id` FOREIGN KEY (`University_ID`) REFERENCES `university` (`University_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `Payment_ID` int NOT NULL AUTO_INCREMENT,
  `Applicantion_ID` int NOT NULL,
  `Amount` double NOT NULL,
  `Card_number` int NOT NULL,
  `Type` enum('Card','Bank Transfer') NOT NULL,
  PRIMARY KEY (`Payment_ID`),
  KEY `applicant_id_idx` (`Applicantion_ID`),
  CONSTRAINT `applicant_id` FOREIGN KEY (`Applicantion_ID`) REFERENCES `applicant` (`Applicant_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prior_education`
--

DROP TABLE IF EXISTS `prior_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prior_education` (
  `Prior_Education_ID` int NOT NULL AUTO_INCREMENT,
  `School_Name` varchar(45) NOT NULL,
  `City` varchar(45) NOT NULL,
  `Country` varchar(45) NOT NULL,
  `Program` varchar(45) NOT NULL,
  `Grade` varchar(4) NOT NULL,
  `Applicant_ID` int NOT NULL,
  PRIMARY KEY (`Prior_Education_ID`),
  KEY `applicant_id_idx` (`Applicant_ID`),
  CONSTRAINT `applicante_id` FOREIGN KEY (`Applicant_ID`) REFERENCES `applicant` (`Applicant_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prior_education`
--

LOCK TABLES `prior_education` WRITE;
/*!40000 ALTER TABLE `prior_education` DISABLE KEYS */;
INSERT INTO `prior_education` VALUES (2,'HKR','Krinistand','Sweden','IB','A',2),(4,'Vaxjo School','Vaxjo','Sweden','IB','C',4),(5,'Stockholm School','Stockholm','Sweden','IB','D',5),(6,'Osby School','Osby','Sweden','IB','100',6),(7,'Almhult School','Almhult','Sweden','Swedish program','B',3),(8,'Good school','Stirling','Scotland','IB','AAA',9);
/*!40000 ALTER TABLE `prior_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `university`
--

DROP TABLE IF EXISTS `university`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `university` (
  `University_ID` int NOT NULL,
  `Name` varchar(45) NOT NULL,
  `City` varchar(45) NOT NULL,
  `Country` varchar(45) NOT NULL,
  PRIMARY KEY (`University_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `university`
--

LOCK TABLES `university` WRITE;
/*!40000 ALTER TABLE `university` DISABLE KEYS */;
/*!40000 ALTER TABLE `university` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-15 15:24:06
