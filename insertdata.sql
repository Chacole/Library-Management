-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'Ngô Anh Tú','23021702@vnu.edu.vn','@Anhtu123'),(2,'Nguyễn Thanh Tùng','23021714@vnu.edu.vn','@NTT0102'),(3,'Trần Anh Tuấn','23021710@vnu.edu.vn','@AnhT987'),(4,'Nguyễn Văn Anh','vana1112@gmail.com','@Anhv2000'),(5,'Lê Anh Duy','anhduy2k@gmail.com','@Duyle2k');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `borrowrecord`
--

LOCK TABLES `borrowrecord` WRITE;
/*!40000 ALTER TABLE `borrowrecord` DISABLE KEYS */;
INSERT INTO `borrowrecord` VALUES (1,1,'Ngô Anh Tú',2,'Dế Mèn phiêu lưu ký','2024-11-20','2024-12-01'),(2,2,'Nguyễn Thanh Tùng',30,'Echoes of the Future','2024-10-11','2024-11-01'),(3,3,'Trần Anh Tuấn',1,'Harry Potter và Chiếc Cốc Lửa','2024-11-16','2024-12-02'),(4,4,'Nguyễn Văn Anh',10,'Understanding the Universe','2024-09-18','2024-09-30'),(5,5,'Lê Anh Duy',5,'The Forgotten Kingdom','2024-10-20','2024-11-05');
/*!40000 ALTER TABLE `borrowrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'J.K.Rowling','Harry Potter và Chiếc Cốc Lửa','Fiction, Novel',2000,12,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7qQoZIq2I3VuHkOeuFHVBjbof1RlLp_OJQQ&s'),(2,'Tô Hoài','Dế Mèn phiêu lưu ký','Fiction, Novella',NULL,10,'https://product.hstatic.net/200000343865/product/-men-phieu-luu-ky-_13x19_bia_tb2019-1_1b338ee424b540bb8f4a86590e1fe0f9_bcd67146c93f426984f21f3bbbdd1d1e_master.jpg'),(3,'Antoine de Saint-Exupéry','Hoàng Tử Bé','Childrens literature, Novella, Fable',NULL,6,'https://images.tuyensinh247.com/picture/images_question/1664871886-pxlm.jpg'),(4,'Isabella Moon','Mysteries of the Ocean','Adventure',2022,12,'https://salt.tikicdn.com/cache/750x750/ts/product/d5/dc/3b/ae34fd77db389ee5c9d4c7a54505f9b3.jpg.webp'),(5,'Lucas Bright','The Forgotten Kingdom','Fantasy',2019,20,'https://m.media-amazon.com/images/I/611C7S4NcuL._SY445_SX342_.jpg'),(6,'Mia Reed','Journey Through History','History',2020,10,'https://m.media-amazon.com/images/I/41Aormur8dL.jpg'),(7,'Ethan Dreamer','The Edge of the World','Novel',2023,14,'https://salt.tikicdn.com/cache/750x750/ts/product/51/69/cb/3a1023ffd69019ae7d460cec01c5aa96.jpg.webp'),(8,'Sophia Green','Thoughts and Reflections','Philosophy',2021,9,'https://m.media-amazon.com/images/I/31hgu0riRuL._SY445_SX342_.jpg'),(9,'Charlotte King','Whispers of the Past','Fiction',2022,18,'https://m.media-amazon.com/images/I/414FuYe5p-L._SY445_SX342_.jpg'),(10,'Oliver Stone','Understanding the Universe','Science',2021,12,'https://m.media-amazon.com/images/I/51gJ-j8+AKL._SY466_.jpg'),(11,'Sophia Bright','Desert Winds','Adventure',2023,9,'https://m.media-amazon.com/images/I/51hE910QV+L._SY445_SX342_.jpg'),(12,'Daniel Black','Dreams of a Forgotten Land','Fantasy',2022,11,'https://salt.tikicdn.com/cache/750x750/ts/product/e6/0a/50/24c000317f515d84156e88d7f70c1be3.jpg.webp'),(13,'Grace Turner','The Rise of Nations','History',2021,5,'https://salt.tikicdn.com/cache/750x750/ts/product/49/61/b8/fbea54c199dfe1a32fdda17c6f1750c2.jpg.webp'),(14,'Henry White','The Power of Imagination','Novel',2023,16,'https://m.media-amazon.com/images/I/61lrNN-1ZwL._SY466_.jpg'),(15,'Isabella Green','The Meaning of Life','Philosophy',2020,13,'https://thebookland.vn/thumbnail_1200/9780995753549.jpg'),(16,'Jack Hunter','Echoes of the Wilderness','Adventure',2022,14,'https://m.media-amazon.com/images/I/81Ybv9a3gLL._SY466_.jpg'),(17,'Alice Rose','The Art of War','History',2021,8,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_6780.jpg'),(18,'David Foster','Light in the Darkness','Fiction',2023,12,'https://books.google.com/books/publisher/content/images/frontcover/fN1IBAAAQBAJ?fife=w240-h345'),(19,'Emma Grace','Chronicles of the Cosmos','Science',2020,20,'https://m.media-amazon.com/images/I/71ikWDP5kTL._SL1499_.jpg'),(20,'Lucas Storm','Into the Unknown','Adventure',2021,7,'https://img.sachtruyen.com.vn/images/astro-kittens-into-the-unknown/astro-kittens-into-the-unknown-0.jpg'),(21,'Olivia Winter','Tales of Old Legends','Fantasy',2023,10,'https://m.media-amazon.com/images/I/81xeE1ELbkL._SL1500_.jpg'),(22,'Benjamin Wood','Philosophy of Life','Philosophy',2022,6,'https://salt.tikicdn.com/cache/750x750/ts/product/21/21/c0/6fcdc3ad2b4b38d32c11faba358900ed.jpg.webp'),(23,'Charlotte Moon','Waves of the Ocean','Adventure',2023,9,'https://m.media-amazon.com/images/I/51S0CLtWHqL._SL1500_.jpg'),(24,'Henry Gold','The Art of Philosophy','Philosophy',2022,15,'https://m.media-amazon.com/images/I/A1z2YLtAS2L._SL1500_.jpg'),(25,'Lily Blake','The Power of Science','Science',2021,12,'https://cdn.openpublishing.com/thumbnail/products/1044001/large.webp'),(26,'Samuel Black','Journey Through Time','History',2020,11,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_43272.jpg'),(27,'Sophia Gold','Whispers of the Past','Fiction',2021,20,'https://m.media-amazon.com/images/I/414FuYe5p-L._SY445_SX342_.jpg'),(28,'Elijah Green','Chronicles of the Lost World','Fantasy',2022,8,'https://down-vn.img.susercontent.com/file/vn-11134207-7r98o-lt9qv0hj7lp56d.webp'),(29,'Amelia Storm','Reflections in the Mist','Novel',2023,10,'https://m.media-amazon.com/images/I/41prkcGHsOL._SY445_SX342_.jpg'),(30,'Olivia White','Echoes of the Future','Sci-Fi',2023,14,'https://gestalten.com/cdn/shop/products/Echoes_of_the_Future_gestalten_design_book_06c9802e-f70c-4f49-995a-c01924d01c91_2000x.png?v=1645462642'),(31,'Ethan Red','The Silent Witness','Mystery',2022,6,'https://m.media-amazon.com/images/I/41Zi3VGN6yL._SY445_SX342_.jpg'),(32,'Charlotte Green','The Golden Path','Historical Fiction',2021,9,'https://m.media-amazon.com/images/I/81t+iInqSuL._CLa%7C3129,2560%7C81LWKkl-IfL.jpg,81Th0Y-sG2L.jpg%7C0,0,1422,2560+1707,0,1422,2560+711,0,1707,2560_._SY300_.jpg'),(33,'Lucas Sky','Stars Above Us','Fantasy',2023,7,'https://m.media-amazon.com/images/I/51dtNwzTgAL.jpg'),(34,'Harper Blue','The Ocean’s Secrets','Adventure',2020,11,'https://thebookland.vn/thumbnail_1200/9780241415252.jpg'),(35,'Mason Black','The Legend of the Lost City','Action',2022,5,'https://m.media-amazon.com/images/I/61ifzF6NvWL._SL1000_.jpg'),(36,'Ella Moon','Beneath the Surface','Thriller',2021,8,'https://m.media-amazon.com/images/I/513irYTIkoL._SY445_SX342_.jpg'),(37,'Lucas Green','The Midnight Sky','Sci-Fi',2023,8,'https://m.media-amazon.com/images/I/71IoLmblnDL._SL1360_.jpg'),(38,'Chloe Black','Echoes of the Past','Historical Fiction',2022,5,'https://muasachcu.com/wp-content/uploads/2024/09/Echoes-From-The-Past-World-History-To-The-16th.jpg'),(39,'Ethan Stone','The Hidden Depths','Mystery',2021,7,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_41894.jpg'),(40,'Olivia Pierce','Dawn of a New Era','Fantasy',2022,6,'https://m.media-amazon.com/images/I/91acUsGvdDL._SL1500_.jpg'),(41,'Dylan Blue','Into the Abyss','Adventure',2020,10,'https://play-lh.googleusercontent.com/lEpGf1mgLWb9NQsfDUYtyooho6oIGtPnsh6E7I9KOZrbKQlI38Kpt2oxygEYUL0gBGpIPrwNk5hJ=w240-h345-rw'),(42,'Ava Harper','The Last Escape','Thriller',2023,9,'https://m.media-amazon.com/images/I/71jxvIC8TTL._SL1017_.jpg'),(43,'William Red','Chronicles of the Fallen','Fantasy',2019,4,'https://m.media-amazon.com/images/I/51tl6G48gyL._SL1360_.jpg'),(44,'Charlotte Blake','The Enchanted Forest','Fantasy',2023,6,'https://salt.tikicdn.com/cache/750x750/ts/product/1c/5f/4e/4bcdbffbddc6ccd7a590920e13541b28.jpg.webp'),(45,'Jack Silver','The Silent Witness','Crime',2022,7,'https://m.media-amazon.com/images/I/71yOCPJKYcL._SL1500_.jpg'),(46,'Mia Frost','The Midnight Call','Thriller',2021,9,'https://m.media-amazon.com/images/I/718R6PAyVyL._SL1360_.jpg'),(47,'James Blackwell','Beneath the Storm','Action',2020,8,'https://m.media-amazon.com/images/I/91OatW2iLcL._SL1500_.jpg'),(48,'Ella Matthews','The Forgotten Kingdom','Historical Fiction',2022,5,'https://m.media-amazon.com/images/I/A1TUUQGnU1L._SL1500_.jpg'),(49,'Michael Hunter','The Lost City','Adventure',2023,6,'https://cdn0.fahasa.com/media/catalog/product/9/7/9780194723633.jpg'),(50,'Isabella Rose','Whispers of the Deep','Mystery',2021,4,'https://m.media-amazon.com/images/I/81Mu-HDFwHL._SL1500_.jpg'),(51,'David Winters','Echoes of the Past','Mystery',2022,10,'https://m.media-amazon.com/images/I/71st48qJ18L._SL1360_.jpg'),(52,'Hannah Green','The Sunken City','Adventure',2023,8,'https://m.media-amazon.com/images/I/81a8wBCtpZL._SL1360_.jpg'),(53,'Oliver Stone','Rise of the Empire','Historical Fiction',2021,9,'https://m.media-amazon.com/images/I/91cz1aG5QEL._CLa%7C2016,1755%7C81QfY734utL.jpg,91n2qDfSgPL.jpg%7C0,0,916,1755+1100,0,916,1755+458,0,1100,1755_._SY300_.jpg'),(54,'Sophia Grey','Journey Through Time','Sci-Fi',2020,7,'https://m.media-amazon.com/images/I/61r9yqOsX7L._SL1360_.jpg'),(55,'Liam Fox','The Guardian Angel','Fantasy',2022,6,'https://m.media-amazon.com/images/I/71p39tYjhtL._SL1360_.jpg'),(56,'Amelia Reed','The Last Secret','Thriller',2023,5,'https://m.media-amazon.com/images/I/41WGBxZq2OL.jpg'),(57,'Samuel Horne','Shadows in the Night','Crime',2021,4,'https://m.media-amazon.com/images/I/51dXq+WoiTL._SL1360_.jpg');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `manageraccount`
--

LOCK TABLES `manageraccount` WRITE;
/*!40000 ALTER TABLE `manageraccount` DISABLE KEYS */;
INSERT INTO `manageraccount` VALUES (1,'Ngô Anh Tú','23021702@vnu.edu.vn','tprojectlbmm');
/*!40000 ALTER TABLE `manageraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Ngô Anh Tú','23021702@vnu.edu.vn','11 Ngách 44/70 Trần Thái Tông-Dịch Vọng Hậu-Cầu Giấy-Hà Nội','0389054101'),(2,'Nguyễn Thanh Tùng','23021714@vnu.edu.vn','12 Trung Kính-Trung Hòa-Cầu Giấy-Hà Nội','0358771884'),(3,'Trần Anh Tuấn','23021710@vnu.edu.vn','63 Trần Bình-Mai Dịch-Cầu Giấy-Hà Nội','0975221792'),(4,'Nguyễn Văn Anh','vana1112@gmail.com','120 Trung Kính-Yên Hòa-Cầu Giấy-Hà Nội','0385662101'),(5,'Lê Anh Duy','anhduy2k@gmail.com','94 Trần Tử Bình-Nghĩa Tân-Cầu Giấy-Hà Nội','0975684972');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-05 14:23:34
