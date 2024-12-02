-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	8.0.40

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
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `borrowrecord`
--

LOCK TABLES `borrowrecord` WRITE;
/*!40000 ALTER TABLE `borrowrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrowrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'Micheal','World Class','football',2018,1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7qQoZIq2I3VuHkOeuFHVBjbof1RlLp_OJQQ&s'),(2,'Ben B','GOAT','football',2007,1,'https://thanhnien.mediacdn.vn/uploaded/minhnguyet/2016_07_30/harrypotter_YLUP.jpg?width=500'),(3,'Peter','Sleepy','comedy',2015,3,'https://cafebiz.cafebizcdn.vn/2019/11/13/photo-1-1573613930390416410456.jpg'),(4,'Tuan Tran','Great of all time','football',2024,2,'https://thuviensach.vn/img/news/2022/11/larger/9451-doan-ho-nhan-1.jpg'),(5,'Tuan','M10 & CR7','football',2023,0,'https://intamphuc.vn/wp-content/uploads/2023/06/mau-bia-sach-dep-2.jpg'),(6,'Sophia Rain','Language of love','theory',2017,4,'https://thanhnien.mediacdn.vn/Uploaded/minhnguyet/2022_05_08/bia-sach2-9886.jpg'),(7,'AuthorX','Book Y','fiction',2021,5,'https://img.pikbest.com/templates/20211021/bg/7e16ef5a6a47da9c9e76bb6c1b28a15a_117562.png!w700wp'),(8,'AuthorY','Book Z','science',2019,6,'https://images.tuyensinh247.com/picture/images_question/1664871886-pxlm.jpg'),(14,'John Mystery','The Secret World','Fiction',2021,10,'https://bizweb.dktcdn.net/thumb/1024x1024/100/417/638/products/e9e04a73c6696aeed331dd55653ee9d4.jpg?v=1618630823407'),(26,'Emily Bright','Journey to the Stars','Science',2022,5,'https://bizweb.dktcdn.net/thumb/large/100/445/986/products/8935325020384.png?v=1719386100933'),(27,'Michael Brave','Tales of the Desert','Adventure',2020,7,'https://newshop.vn/public/uploads/products/7271/tay-du-hi-3.jpg'),(30,'Sophia Dreams','Whispers of Fantasy','Fantasy',2019,3,'https://img.kienthucchiase.com/images/bo-tay-du-hi-tap-1-tai-ban/bo-tay-du-hi-tap-1-tai-ban-0.jpg'),(31,'Thomas Ancient','Chronicles of History','History',2018,8,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRD0H8SMEQM7j4H4Rl_QKBDkP50h_Ai68eNgw&s'),(32,'Anna Voyager','Winds of Adventure','Novel',2023,6,'https://truyen.audio/_next/image?url=http%3A%2F%2Fimg.faloo.com%2FNovel%2F498x705%2F0%2F935%2F000935027.jpg&w=640&q=75'),(34,'Liam Thinker','Philosophical Reflections','Philosophy',2021,4,'https://images.spiderum.com/sp-images/192131d0c46c11eb87cbbd451b7e39a6.jpg'),(35,'Olivia Reed','The Great Adventure','Fiction',2020,15,'https://thebookland.vn/thumbnail_1200/9781838570606.jpg'),(36,'James Walker','The Mystery of Time','Science',2021,8,'https://www.worldscientific.com/cms/10.1142/13161/asset/18ee2b12-9818-e2b1-f981-ee2b12f9818e/13161.cover.jpg'),(37,'Isabella Moon','Mysteries of the Ocean','Adventure',2022,12,'https://salt.tikicdn.com/cache/750x750/ts/product/d5/dc/3b/ae34fd77db389ee5c9d4c7a54505f9b3.jpg.webp'),(38,'Lucas Bright','The Forgotten Kingdom','Fantasy',2019,20,'https://m.media-amazon.com/images/I/611C7S4NcuL._SY445_SX342_.jpg'),(39,'Mia Reed','Journey Through History','History',2020,10,'https://m.media-amazon.com/images/I/41Aormur8dL.jpg'),(40,'Ethan Dreamer','The Edge of the World','Novel',2023,14,'https://salt.tikicdn.com/cache/750x750/ts/product/51/69/cb/3a1023ffd69019ae7d460cec01c5aa96.jpg.webp'),(41,'Sophia Green','Thoughts and Reflections','Philosophy',2021,9,'https://m.media-amazon.com/images/I/31hgu0riRuL._SY445_SX342_.jpg'),(42,'Charlotte King','Whispers of the Past','Fiction',2022,18,'https://m.media-amazon.com/images/I/414FuYe5p-L._SY445_SX342_.jpg'),(43,'Oliver Stone','Understanding the Universe','Science',2021,22,'https://m.media-amazon.com/images/I/51gJ-j8+AKL._SY466_.jpg'),(44,'Sophia Bright','Desert Winds','Adventure',2023,9,'https://m.media-amazon.com/images/I/51hE910QV+L._SY445_SX342_.jpg'),(45,'Daniel Black','Dreams of a Forgotten Land','Fantasy',2022,11,'https://salt.tikicdn.com/cache/750x750/ts/product/e6/0a/50/24c000317f515d84156e88d7f70c1be3.jpg.webp'),(46,'Grace Turner','The Rise of Nations','History',2021,5,'https://salt.tikicdn.com/cache/750x750/ts/product/49/61/b8/fbea54c199dfe1a32fdda17c6f1750c2.jpg.webp'),(47,'Henry White','The Power of Imagination','Novel',2023,16,'https://m.media-amazon.com/images/I/61lrNN-1ZwL._SY466_.jpg'),(48,'Isabella Green','The Meaning of Life','Philosophy',2020,13,'https://thebookland.vn/thumbnail_1200/9780995753549.jpg'),(49,'Jack Hunter','Echoes of the Wilderness','Adventure',2022,14,'https://m.media-amazon.com/images/I/81Ybv9a3gLL._SY466_.jpg'),(50,'Alice Rose','The Art of War','History',2021,8,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_6780.jpg'),(51,'David Foster','Light in the Darkness','Fiction',2023,12,'https://books.google.com/books/publisher/content/images/frontcover/fN1IBAAAQBAJ?fife=w240-h345'),(52,'Emma Grace','Chronicles of the Cosmos','Science',2020,20,'https://m.media-amazon.com/images/I/71ikWDP5kTL._SL1499_.jpg'),(53,'Lucas Storm','Into the Unknown','Adventure',2021,7,'https://img.sachtruyen.com.vn/images/astro-kittens-into-the-unknown/astro-kittens-into-the-unknown-0.jpg'),(54,'Olivia Winter','Tales of Old Legends','Fantasy',2023,10,'https://m.media-amazon.com/images/I/81xeE1ELbkL._SL1500_.jpg'),(55,'Benjamin Wood','Philosophy of Life','Philosophy',2022,6,'https://salt.tikicdn.com/cache/750x750/ts/product/21/21/c0/6fcdc3ad2b4b38d32c11faba358900ed.jpg.webp'),(56,'Charlotte Moon','Waves of the Ocean','Adventure',2023,9,'https://m.media-amazon.com/images/I/51S0CLtWHqL._SL1500_.jpg'),(57,'Henry Gold','The Art of Philosophy','Philosophy',2022,15,'https://m.media-amazon.com/images/I/A1z2YLtAS2L._SL1500_.jpg'),(58,'Lily Blake','The Power of Science','Science',2021,12,'https://cdn.openpublishing.com/thumbnail/products/1044001/large.webp'),(59,'Samuel Black','Journey Through Time','History',2020,11,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_43272.jpg'),(60,'Sophia Gold','Whispers of the Past','Fiction',2021,20,'https://m.media-amazon.com/images/I/414FuYe5p-L._SY445_SX342_.jpg'),(61,'Elijah Green','Chronicles of the Lost World','Fantasy',2022,8,'https://down-vn.img.susercontent.com/file/vn-11134207-7r98o-lt9qv0hj7lp56d.webp'),(62,'Amelia Storm','Reflections in the Mist','Novel',2023,10,'https://m.media-amazon.com/images/I/41prkcGHsOL._SY445_SX342_.jpg'),(63,'Olivia White','Echoes of the Future','Sci-Fi',2023,14,'https://gestalten.com/cdn/shop/products/Echoes_of_the_Future_gestalten_design_book_06c9802e-f70c-4f49-995a-c01924d01c91_2000x.png?v=1645462642'),(64,'Ethan Red','The Silent Witness','Mystery',2022,6,'https://m.media-amazon.com/images/I/41Zi3VGN6yL._SY445_SX342_.jpg'),(65,'Charlotte Green','The Golden Path','Historical Fiction',2021,9,'https://m.media-amazon.com/images/I/81t+iInqSuL._CLa%7C3129,2560%7C81LWKkl-IfL.jpg,81Th0Y-sG2L.jpg%7C0,0,1422,2560+1707,0,1422,2560+711,0,1707,2560_._SY300_.jpg'),(66,'Lucas Sky','Stars Above Us','Fantasy',2023,7,'https://m.media-amazon.com/images/I/51dtNwzTgAL.jpg'),(67,'Harper Blue','The Ocean’s Secrets','Adventure',2020,11,'https://thebookland.vn/thumbnail_1200/9780241415252.jpg'),(68,'Mason Black','The Legend of the Lost City','Action',2022,5,'https://m.media-amazon.com/images/I/61ifzF6NvWL._SL1000_.jpg'),(69,'Ella Moon','Beneath the Surface','Thriller',2021,8,'https://m.media-amazon.com/images/I/513irYTIkoL._SY445_SX342_.jpg'),(77,'Lucas Green','The Midnight Sky','Sci-Fi',2023,8,'https://m.media-amazon.com/images/I/71IoLmblnDL._SL1360_.jpg'),(78,'Chloe Black','Echoes of the Past','Historical Fiction',2022,5,'https://muasachcu.com/wp-content/uploads/2024/09/Echoes-From-The-Past-World-History-To-The-16th.jpg'),(79,'Ethan Stone','The Hidden Depths','Mystery',2021,7,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_41894.jpg'),(80,'Olivia Pierce','Dawn of a New Era','Fantasy',2022,6,'https://m.media-amazon.com/images/I/91acUsGvdDL._SL1500_.jpg'),(81,'Dylan Blue','Into the Abyss','Adventure',2020,10,'https://play-lh.googleusercontent.com/lEpGf1mgLWb9NQsfDUYtyooho6oIGtPnsh6E7I9KOZrbKQlI38Kpt2oxygEYUL0gBGpIPrwNk5hJ=w240-h345-rw'),(82,'Ava Harper','The Last Escape','Thriller',2023,9,'https://m.media-amazon.com/images/I/71jxvIC8TTL._SL1017_.jpg'),(83,'William Red','Chronicles of the Fallen','Fantasy',2019,4,'https://m.media-amazon.com/images/I/51tl6G48gyL._SL1360_.jpg'),(84,'Charlotte Blake','The Enchanted Forest','Fantasy',2023,6,'https://salt.tikicdn.com/cache/750x750/ts/product/1c/5f/4e/4bcdbffbddc6ccd7a590920e13541b28.jpg.webp'),(85,'Jack Silver','The Silent Witness','Crime',2022,7,'https://m.media-amazon.com/images/I/71yOCPJKYcL._SL1500_.jpg'),(86,'Mia Frost','The Midnight Call','Thriller',2021,9,'https://m.media-amazon.com/images/I/718R6PAyVyL._SL1360_.jpg'),(87,'James Blackwell','Beneath the Storm','Action',2020,8,'https://m.media-amazon.com/images/I/91OatW2iLcL._SL1500_.jpg'),(88,'Ella Matthews','The Forgotten Kingdom','Historical Fiction',2022,5,'https://m.media-amazon.com/images/I/A1TUUQGnU1L._SL1500_.jpg'),(89,'Michael Hunter','The Lost City','Adventure',2023,6,'https://cdn0.fahasa.com/media/catalog/product/9/7/9780194723633.jpg'),(90,'Isabella Rose','Whispers of the Deep','Mystery',2021,4,'https://m.media-amazon.com/images/I/81Mu-HDFwHL._SL1500_.jpg'),(91,'David Winters','Echoes of the Past','Mystery',2022,10,'https://m.media-amazon.com/images/I/71st48qJ18L._SL1360_.jpg'),(92,'Hannah Green','The Sunken City','Adventure',2023,8,'https://m.media-amazon.com/images/I/81a8wBCtpZL._SL1360_.jpg'),(93,'Oliver Stone','Rise of the Empire','Historical Fiction',2021,9,'https://m.media-amazon.com/images/I/91cz1aG5QEL._CLa%7C2016,1755%7C81QfY734utL.jpg,91n2qDfSgPL.jpg%7C0,0,916,1755+1100,0,916,1755+458,0,1100,1755_._SY300_.jpg'),(94,'Sophia Grey','Journey Through Time','Sci-Fi',2020,7,'https://m.media-amazon.com/images/I/61r9yqOsX7L._SL1360_.jpg'),(95,'Liam Fox','The Guardian Angel','Fantasy',2022,6,'https://m.media-amazon.com/images/I/71p39tYjhtL._SL1360_.jpg'),(96,'Amelia Reed','The Last Secret','Thriller',2023,5,'https://m.media-amazon.com/images/I/41WGBxZq2OL.jpg'),(97,'Samuel Horne','Shadows in the Night','Crime',2021,4,'https://m.media-amazon.com/images/I/51dXq+WoiTL._SL1360_.jpg');
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
INSERT INTO `users` VALUES (1,'User1','user1@example.com','123 Main St','123-456-7890'),(2,'User2','user2@example.com','456 Main St','456-789-0123'),(3,'User3','user3@example.com','789 Main St','789-012-3456');
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

-- Dump completed on 2024-12-03  0:25:11
