CREATE DATABASE Library;
USE Library;

-- Người dùng
CREATE TABLE Users (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Phone VARCHAR(15) NOT NULL
);

-- Tài khoản người dùng
CREATE TABLE Accounts (
	AccountID INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE,
    Password VARCHAR(100),
    FOREIGN KEY (AccountID) REFERENCES Users(ID)
);

-- Tài khoản quản lý
CREATE TABLE ManagerAccount (
	AccountID INT PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Password VARCHAR(100)
);

insert into manageraccount(AccountID, Name, Email, Password) 
values (1, 'Ngô Anh Tú', '23021702@vnu.edu.vn', 'tprojectlbmm');

-- Tài liệu
CREATE TABLE Documents (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Author VARCHAR(100) NOT NULL,
    Title VARCHAR(150) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    Year INT,
    Quantity INT NOT NULL DEFAULT 1,
    ImagePath VARCHAR(255) NOT NULL
);

-- Hồ sơ mượn trả
CREATE TABLE BorrowRecord (
    BorrowID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    UserName VARCHAR(100),
    DocumentID INT,
    DocumentTitle VARCHAR(100),
    BorrowDate DATE,
    ReturnDate DATE,
    FOREIGN KEY (DocumentID) REFERENCES Documents(ID),
    FOREIGN KEY (UserID) REFERENCES Users(ID)
);

-- Trigger của BorrowRecord
DELIMITER $$
-- Trigger giảm số lượng khi tài liệu được mượn
CREATE TRIGGER before_insert_borrowrecord
BEFORE INSERT ON borrowrecord
FOR EACH ROW
BEGIN
    UPDATE documents
    SET Quantity = Quantity - 1
    WHERE ID = NEW.DocumentID;
END$$

-- Trigger tăng số lượng khi tài liệu được trả
CREATE TRIGGER after_update_borrowrecord
AFTER UPDATE ON borrowrecord
FOR EACH ROW
BEGIN
    IF NEW.ReturnDate IS NOT NULL AND OLD.ReturnDate IS NULL THEN
        UPDATE documents
        SET Quantity = Quantity + 1
        WHERE ID = NEW.DocumentID;
    END IF;
END$$

CREATE TRIGGER before_borrowrecord_insert
BEFORE INSERT ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Lấy UserName từ bảng Users dựa trên UserID
    SET NEW.UserName = (SELECT Name FROM Users WHERE ID = NEW.UserID LIMIT 1);
    IF NEW.UserName IS NULL THEN
        SET NEW.UserName = 'Unknown User';
    END IF;

    -- Lấy DocumentTitle từ bảng Documents dựa trên DocumentID
    SET NEW.DocumentTitle = (SELECT Title FROM Documents WHERE ID = NEW.DocumentID LIMIT 1);
    IF NEW.DocumentTitle IS NULL THEN
        SET NEW.DocumentTitle = 'Unknown Document';
    END IF;
END$$

CREATE TRIGGER before_borrowrecord_update
BEFORE UPDATE ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Cập nhật UserName nếu UserID thay đổi
    IF OLD.UserID != NEW.UserID THEN
        SET NEW.UserName = (SELECT Name FROM Users WHERE ID = NEW.UserID LIMIT 1);
        IF NEW.UserName IS NULL THEN
            SET NEW.UserName = 'Unknown User';
        END IF;
    END IF;

    -- Cập nhật DocumentTitle nếu DocumentID thay đổi
    IF OLD.DocumentID != NEW.DocumentID THEN
        SET NEW.DocumentTitle = (SELECT Title FROM Documents WHERE ID = NEW.DocumentID LIMIT 1);
        IF NEW.DocumentTitle IS NULL THEN
            SET NEW.DocumentTitle = 'Unknown Document';
        END IF;
    END IF;
END$$

CREATE TRIGGER sync_accounts_insert
AFTER INSERT ON users
FOR EACH ROW
BEGIN
    -- Thêm thông tin vào bảng accounts khi thêm bản ghi mới vào users
    INSERT INTO accounts (AccountID, Name, Email)
    VALUES (NEW.ID, NEW.Name, NEW.Email);
END$$

CREATE TRIGGER sync_accounts_update
AFTER UPDATE ON users
FOR EACH ROW
BEGIN
    -- Cập nhật thông tin trong bảng accounts khi có thay đổi trong bảng users
    UPDATE accounts
    SET Name = NEW.Name, Email = NEW.Email
    WHERE AccountID = NEW.ID;
END$$

CREATE TRIGGER delete_account_after_user_delete
AFTER DELETE ON users
FOR EACH ROW
BEGIN
    -- Xóa bản ghi trong bảng accounts khi xóa người dùng
    DELETE FROM accounts 
    WHERE AccountID = OLD.ID;
END$$
DELIMITER ;

-- Procedure với Users và Accounts
DELIMITER //
CREATE PROCEDURE Register (IN NameK VARCHAR(100), IN EmailK VARCHAR(100), IN AddressK VARCHAR(255), IN PhoneK VARCHAR(15), IN PasswordK VARCHAR(100))
BEGIN
	INSERT INTO users (name, email, address, phone)
	VALUES (NameK, EmailK, AddressK, PhoneK);
    
	SET sql_safe_updates = 0;
	UPDATE accounts
	SET password = PasswordK
	WHERE Email = EmailK;
	SET sql_safe_updates = 1;
END;

CREATE PROCEDURE FindUser(IN NameK VARCHAR(100), IN EmailK VARCHAR(100), IN PhoneK VARCHAR(15))
BEGIN
    SELECT * FROM users
    WHERE (Name LIKE CONCAT('%', NameK, '%') OR NameK IS NULL OR NameK = '') 
    AND (Email LIKE CONCAT('%', EmailK, '%') OR EmailK IS NULL OR EmailK = '')
    AND (Phone LIKE CONCAT('%', PhoneK, '%') OR PhoneK IS NULL OR PhoneK = '');
END; 

CREATE PROCEDURE EditUser(IN EmailKf VARCHAR(100), IN NameK VARCHAR(100), IN EmailKe VARCHAR(100), IN AddressK VARCHAR(255), IN PhoneK VARCHAR(15), IN PasswordK VARCHAR(100))
BEGIN
    SET sql_safe_updates = 0;
    UPDATE users
	SET Name = IFNULL(NULLIF(NameK, ''), Name),
    Email = IFNULL(NULLIF(EmailKe, ''), Email),
    Address = IFNULL(NULLIF(AddressK, ''), Address),
    Phone = IFNULL(NULLIF(PhoneK, ''), Phone)
	WHERE Email = EmailKf;
    
    UPDATE accounts
    SET Password = IFNULL(NULLIF(PasswordK, ''), Password)
    WHERE Email = EmailKf;
	SET sql_safe_updates = 1;
END;

CREATE PROCEDURE DeleteUser (IN EmailK VARCHAR(100))
BEGIN
	SET sql_safe_updates = 0;
    SET @deleted_id = (SELECT ID FROM users WHERE Email = EmailK);
	DELETE FROM users
    WHERE Email = EmailK;
    -- Đặt lại các ID > ID user đã xóa
    UPDATE users
    SET ID = ID - 1
    WHERE ID > @deleted_id;
    -- Đặt lại AUTO_INCREMENT
    SET @max_id = IFNULL((SELECT MAX(ID) FROM users), 0);
    SET @sql = CONCAT('ALTER TABLE users AUTO_INCREMENT = ', @max_id + 1);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    SET sql_safe_updates = 1;
END;
// DELIMITER ;

-- Procedure với Documents
DELIMITER //
CREATE PROCEDURE FindDocument(IN TitleK VARCHAR(150), IN TypeK VARCHAR(50), IN AuthorK VARCHAR(100), IN YearK INT)
BEGIN
    SELECT * FROM documents
    WHERE (Year LIKE CONCAT('%', YearK, '%') OR YearK IS NULL OR YearK = '') 
    AND (Title LIKE CONCAT('%', TitleK, '%') OR TitleK IS NULL OR TitleK = '')
    AND (Type LIKE CONCAT('%', TypeK, '%') OR TypeK IS NULL OR TypeK = '')
    AND (Author LIKE CONCAT('%', AuthorK, '%') OR AuthorK IS NULL OR AuthorK = '');
END;

CREATE PROCEDURE EditDocument(IN IDK INT, IN AuthorK VARCHAR(100), IN TitleK VARCHAR(150), IN TypeK VARCHAR(50), IN YearK INT, IN QuantityK INT, IN ImagePathK VARCHAR(255))
BEGIN
    SET sql_safe_updates = 0;
    UPDATE documents
	SET Title = IFNULL(NULLIF(TitleK, ''), Title),
    Type = IFNULL(NULLIF(TypeK, ''), Type),
    Author = IFNULL(NULLIF(AuthorK, ''), Author),
    Year = IFNULL(NULLIF(YearK, ''), Year),
    Quantity = IFNULL(NULLIF(QuantityK, ''), Quantity),
    ImagePath = IFNULL(NULLIF(ImagePathK, ''), ImagePathK)
	WHERE ID = IDK;
	SET sql_safe_updates = 1;
END;

CREATE PROCEDURE DeleteDocument(IN TitleK VARCHAR(150), IN AuthorK VARCHAR(100), IN YearK INT)
BEGIN
    SET sql_safe_updates = 0;
    SET @deleted_id = (SELECT ID FROM documents WHERE Title = TitleK AND Author = AuthorK AND Year = YearK);
	DELETE FROM documents
	WHERE Title = TitleK AND Author = AuthorK AND Year = YearK;
    -- Đặt lại các ID > ID documents đã xóa
    UPDATE documents
    SET ID = ID - 1
    WHERE ID > @deleted_id;
    -- Đặt lại AUTO_INCREMENT
    SET @max_id = IFNULL((SELECT MAX(ID) FROM documents), 0);
    SET @sql = CONCAT('ALTER TABLE documents AUTO_INCREMENT = ', @max_id + 1);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
	SET sql_safe_updates = 1;
END;

CREATE PROCEDURE AddDocument(IN AuthorK VARCHAR(150), IN TitleK VARCHAR(150), IN TypeK VARCHAR(50), IN YearK INT, IN QuantityK INT,  IN ImagePathK VARCHAR(255))
BEGIN
    INSERT INTO documents (Author, Title, Type, Year, Quantity, ImagePath)
    VALUES (AuthorK, TitleK, TypeK, YearK, QuantityK, ImagePathK);
END;
// DELIMITER ;

-- Procedure với BorrowRecord
DELIMITER //
CREATE PROCEDURE FindRecord(IN BorrowIDK INT, IN UserNameK VARCHAR(100), IN DocumentTitleK VARCHAR(150))
BEGIN
    SELECT * FROM borrowrecord
    WHERE (BorrowID LIKE CONCAT('%', BorrowIDK, '%') OR BorrowIDK IS NULL OR BorrowIDK = '') 
    AND (UserName LIKE CONCAT('%', UserNameK, '%') OR UserNameK IS NULL OR UserNameK = '')
    AND (DocumentTitle LIKE CONCAT('%', DocumentTitleK, '%') OR DocumentTitleK IS NULL OR DocumentTitleK = '');
END;

CREATE PROCEDURE EditRecord(IN BorrowIDK INT,IN UserIDK INT, IN DocumentIDK INT, IN BorrowDateK DATE, IN ReturnDateK DATE)
BEGIN
    SET sql_safe_updates = 0;
    UPDATE borrowrecord
	SET UserID = IFNULL(NULLIF(UserIDK, ''), UserID),
    DocumentID = IFNULL(NULLIF(DocumentIDK, ''), DocumentID),
    BorrowDate = IFNULL(BorrowDateK, BorrowDate),
    ReturnDate = IFNULL(ReturnDateK, ReturnDate)
	WHERE BorrowID = BorrowIDK;
	SET sql_safe_updates = 1;
END; 

CREATE PROCEDURE DeleteRecord(IN UserIDK INT, DocumentIDK INT)
BEGIN
    SET sql_safe_updates = 0;
    SET @deleted_id = (SELECT BorrowID FROM borrowrecord WHERE UserID = UserIDK AND DocumentID = DocumentIDK);
	DELETE FROM borrowrecord
	WHERE UserID = UserIDK AND DocumentID = DocumentIDK;
    -- Đặt lại các borrowID > borrowID đã xóa
    UPDATE borrowrecord
    SET BorrowID = BorrowID - 1
    WHERE BorrowID > @deleted_id;
    -- Đặt lại AUTO_INCREMENT
    SET @max_id = IFNULL((SELECT MAX(BorrowID) FROM borrowrecord), 0);
    SET @sql = CONCAT('ALTER TABLE borrowrecord AUTO_INCREMENT = ', @max_id + 1);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
	SET sql_safe_updates = 1;
END;

CREATE PROCEDURE AddRecord(IN UserIDK INT, IN DocumentIDK INT, IN BorrowDateK DATE)
BEGIN
    INSERT INTO borrowrecord (userID, documentID, borrowdate)
    VALUES (UserIDK, DocumentIDK, BorrowDateK);
END;
// DELIMITER ;

INSERT INTO Documents (Author, Title, Type, Year, Quantity, ImagePath) VALUES
('J.K.Rowling','Harry Potter và Chiếc Cốc Lửa','Fiction, Novel',2000,12,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7qQoZIq2I3VuHkOeuFHVBjbof1RlLp_OJQQ&s'),
('Tô Hoài','Dế Mèn phiêu lưu ký','Fiction, Novella',NULL,10,'https://product.hstatic.net/200000343865/product/-men-phieu-luu-ky-_13x19_bia_tb2019-1_1b338ee424b540bb8f4a86590e1fe0f9_bcd67146c93f426984f21f3bbbdd1d1e_master.jpg'),
('Antoine de Saint-Exupéry','Hoàng Tử Bé','Childrens literature, Novella, Fable',NULL,6,'https://images.tuyensinh247.com/picture/images_question/1664871886-pxlm.jpg'),
('Isabella Moon','Mysteries of the Ocean','Adventure',2022,12,'https://salt.tikicdn.com/cache/750x750/ts/product/d5/dc/3b/ae34fd77db389ee5c9d4c7a54505f9b3.jpg.webp'),
('Lucas Bright','The Forgotten Kingdom','Fantasy',2019,20,'https://m.media-amazon.com/images/I/611C7S4NcuL._SY445_SX342_.jpg'),
('Mia Reed','Journey Through History','History',2020,10,'https://m.media-amazon.com/images/I/41Aormur8dL.jpg'),
('Ethan Dreamer','The Edge of the World','Novel',2023,14,'https://salt.tikicdn.com/cache/750x750/ts/product/51/69/cb/3a1023ffd69019ae7d460cec01c5aa96.jpg.webp'),
('Sophia Green','Thoughts and Reflections','Philosophy',2021,9,'https://m.media-amazon.com/images/I/31hgu0riRuL._SY445_SX342_.jpg'),
('Charlotte King','Whispers of the Past','Fiction',2022,18,'https://m.media-amazon.com/images/I/414FuYe5p-L._SY445_SX342_.jpg'),
('Oliver Stone','Understanding the Universe','Science',2021,12,'https://m.media-amazon.com/images/I/51gJ-j8+AKL._SY466_.jpg'),
('Sophia Bright','Desert Winds','Adventure',2023,9,'https://m.media-amazon.com/images/I/51hE910QV+L._SY445_SX342_.jpg'),
('Daniel Black','Dreams of a Forgotten Land','Fantasy',2022,11,'https://salt.tikicdn.com/cache/750x750/ts/product/e6/0a/50/24c000317f515d84156e88d7f70c1be3.jpg.webp'),
('Grace Turner','The Rise of Nations','History',2021,5,'https://salt.tikicdn.com/cache/750x750/ts/product/49/61/b8/fbea54c199dfe1a32fdda17c6f1750c2.jpg.webp'),
('Henry White','The Power of Imagination','Novel',2023,16,'https://m.media-amazon.com/images/I/61lrNN-1ZwL._SY466_.jpg'),
('Isabella Green','The Meaning of Life','Philosophy',2020,13,'https://thebookland.vn/thumbnail_1200/9780995753549.jpg'),
('Jack Hunter','Echoes of the Wilderness','Adventure',2022,14,'https://m.media-amazon.com/images/I/81Ybv9a3gLL._SY466_.jpg'),
('Alice Rose','The Art of War','History',2021,8,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_6780.jpg'),
('David Foster','Light in the Darkness','Fiction',2023,12,'https://books.google.com/books/publisher/content/images/frontcover/fN1IBAAAQBAJ?fife=w240-h345'),
('Emma Grace','Chronicles of the Cosmos','Science',2020,20,'https://m.media-amazon.com/images/I/71ikWDP5kTL._SL1499_.jpg'),
('Lucas Storm','Into the Unknown','Adventure',2021,7,'https://img.sachtruyen.com.vn/images/astro-kittens-into-the-unknown/astro-kittens-into-the-unknown-0.jpg'),
('Olivia Winter','Tales of Old Legends','Fantasy',2023,10,'https://m.media-amazon.com/images/I/81xeE1ELbkL._SL1500_.jpg'),
('Benjamin Wood','Philosophy of Life','Philosophy',2022,6,'https://salt.tikicdn.com/cache/750x750/ts/product/21/21/c0/6fcdc3ad2b4b38d32c11faba358900ed.jpg.webp'),
('Charlotte Moon','Waves of the Ocean','Adventure',2023,9,'https://m.media-amazon.com/images/I/51S0CLtWHqL._SL1500_.jpg'),
('Henry Gold','The Art of Philosophy','Philosophy',2022,15,'https://m.media-amazon.com/images/I/A1z2YLtAS2L._SL1500_.jpg'),
('Lily Blake','The Power of Science','Science',2021,12,'https://cdn.openpublishing.com/thumbnail/products/1044001/large.webp'),
('Samuel Black','Journey Through Time','History',2020,11,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_195509_1_43272.jpg'),
('Sophia Gold','Whispers of the Past','Fiction',2021,20,'https://m.media-amazon.com/images/I/414FuYe5p-L._SY445_SX342_.jpg'),
('Elijah Green','Chronicles of the Lost World','Fantasy',2022,8,'https://down-vn.img.susercontent.com/file/vn-11134207-7r98o-lt9qv0hj7lp56d.webp'),
('Amelia Storm','Reflections in the Mist','Novel',2023,10,'https://m.media-amazon.com/images/I/41prkcGHsOL._SY445_SX342_.jpg'),
('Olivia White','Echoes of the Future','Sci-Fi',2023,14,'https://gestalten.com/cdn/shop/products/Echoes_of_the_Future_gestalten_design_book_06c9802e-f70c-4f49-995a-c01924d01c91_2000x.png?v=1645462642'),
('Ethan Red','The Silent Witness','Mystery',2022,6,'https://m.media-amazon.com/images/I/41Zi3VGN6yL._SY445_SX342_.jpg'),
('Charlotte Green','The Golden Path','Historical Fiction',2021,9,'https://m.media-amazon.com/images/I/81t+iInqSuL._CLa%7C3129,2560%7C81LWKkl-IfL.jpg,81Th0Y-sG2L.jpg%7C0,0,1422,2560+1707,0,1422,2560+711,0,1707,2560_._SY300_.jpg'),
('Lucas Sky','Stars Above Us','Fantasy',2023,7,'https://m.media-amazon.com/images/I/51dtNwzTgAL.jpg'),
('Harper Blue','The Ocean’s Secrets','Adventure',2020,11,'https://thebookland.vn/thumbnail_1200/9780241415252.jpg'),
('Mason Black','The Legend of the Lost City','Action',2022,5,'https://m.media-amazon.com/images/I/61ifzF6NvWL._SL1000_.jpg'),
('Ella Moon','Beneath the Surface','Thriller',2021,8,'https://m.media-amazon.com/images/I/513irYTIkoL._SY445_SX342_.jpg'),
('Lucas Green','The Midnight Sky','Sci-Fi',2023,8,'https://m.media-amazon.com/images/I/71IoLmblnDL._SL1360_.jpg'),
('Chloe Black','Echoes of the Past','Historical Fiction',2022,5,'https://muasachcu.com/wp-content/uploads/2024/09/Echoes-From-The-Past-World-History-To-The-16th.jpg'),
('Ethan Stone','The Hidden Depths','Mystery',2021,7,'https://cdn0.fahasa.com/media/catalog/product/i/m/image_41894.jpg'),
('Olivia Pierce','Dawn of a New Era','Fantasy',2022,6,'https://m.media-amazon.com/images/I/91acUsGvdDL._SL1500_.jpg'),
('Dylan Blue','Into the Abyss','Adventure',2020,10,'https://play-lh.googleusercontent.com/lEpGf1mgLWb9NQsfDUYtyooho6oIGtPnsh6E7I9KOZrbKQlI38Kpt2oxygEYUL0gBGpIPrwNk5hJ=w240-h345-rw'),
('Ava Harper','The Last Escape','Thriller',2023,9,'https://m.media-amazon.com/images/I/71jxvIC8TTL._SL1017_.jpg'),
('William Red','Chronicles of the Fallen','Fantasy',2019,4,'https://m.media-amazon.com/images/I/51tl6G48gyL._SL1360_.jpg'),
('Charlotte Blake','The Enchanted Forest','Fantasy',2023,6,'https://salt.tikicdn.com/cache/750x750/ts/product/1c/5f/4e/4bcdbffbddc6ccd7a590920e13541b28.jpg.webp'),
('Jack Silver','The Silent Witness','Crime',2022,7,'https://m.media-amazon.com/images/I/71yOCPJKYcL._SL1500_.jpg'),
('Mia Frost','The Midnight Call','Thriller',2021,9,'https://m.media-amazon.com/images/I/718R6PAyVyL._SL1360_.jpg'),
('James Blackwell','Beneath the Storm','Action',2020,8,'https://m.media-amazon.com/images/I/91OatW2iLcL._SL1500_.jpg'),
('Ella Matthews','The Forgotten Kingdom','Historical Fiction',2022,5,'https://m.media-amazon.com/images/I/A1TUUQGnU1L._SL1500_.jpg'),
('Michael Hunter','The Lost City','Adventure',2023,6,'https://cdn0.fahasa.com/media/catalog/product/9/7/9780194723633.jpg'),
('Isabella Rose','Whispers of the Deep','Mystery',2021,4,'https://m.media-amazon.com/images/I/81Mu-HDFwHL._SL1500_.jpg'),
('David Winters','Echoes of the Past','Mystery',2022,10,'https://m.media-amazon.com/images/I/71st48qJ18L._SL1360_.jpg'),
('Hannah Green','The Sunken City','Adventure',2023,8,'https://m.media-amazon.com/images/I/81a8wBCtpZL._SL1360_.jpg'),
('Oliver Stone','Rise of the Empire','Historical Fiction',2021,9,'https://m.media-amazon.com/images/I/91cz1aG5QEL._CLa%7C2016,1755%7C81QfY734utL.jpg,91n2qDfSgPL.jpg%7C0,0,916,1755+1100,0,916,1755+458,0,1100,1755_._SY300_.jpg'),
('Sophia Grey','Journey Through Time','Sci-Fi',2020,7,'https://m.media-amazon.com/images/I/61r9yqOsX7L._SL1360_.jpg'),
('Liam Fox','The Guardian Angel','Fantasy',2022,6,'https://m.media-amazon.com/images/I/71p39tYjhtL._SL1360_.jpg'),
('Amelia Reed','The Last Secret','Thriller',2023,5,'https://m.media-amazon.com/images/I/41WGBxZq2OL.jpg'),
('Samuel Horne','Shadows in the Night','Crime',2021,4,'https://m.media-amazon.com/images/I/51dXq+WoiTL._SL1360_.jpg');

CALL Register('Ngô Anh Tú', '23021702@vnu.edu.vn', '11 Ngách 44/70 Trần Thái Tông-Dịch Vọng Hậu-Cầu Giấy-Hà Nội', '0389054101', '@Anhtu123');
CALL Register('Nguyễn Thanh Tùng', '23021714@vnu.edu.vn', '12 Trung Kính-Trung Hòa-Cầu Giấy-Hà Nội', '0358771884', '@NTT0102');
CALL Register('Trần Anh Tuấn', '23021710@vnu.edu.vn', '63 Trần Bình-Mai Dịch-Cầu Giấy-Hà Nội', '0975221792', '@AnhT987');
CALL Register('Nguyễn Văn Anh', 'vana1112@gmail.com', '120 Trung Kính-Yên Hòa-Cầu Giấy-Hà Nội', '0385662101', '@Anhv2000');
CALL Register('Lê Anh Duy', 'anhduy2k@gmail.com', '94 Trần Tử Bình-Nghĩa Tân-Cầu Giấy-Hà Nội', '0975684972', '@Duyle2k');

CALL AddRecord(1, 2, '2024-11-20');
CALL EditRecord(1, NULL, NULL, NULL, '2024-12-01');
CALL AddRecord(2, 30, '2024-10-11');
CALL EditRecord(2, NULL, NULL, NULL, '2024-11-01');
CALL AddRecord(3, 1, '2024-11-16');
CALL EditRecord(3, NULL, NULL, NULL, '2024-12-02');
CALL AddRecord(4, 10, '2024-09-18');
CALL EditRecord(4, NULL, NULL, NULL, '2024-09-30');
CALL AddRecord(5, 5, '2024-10-20');
CALL EditRecord(5, NULL, NULL, NULL, '2024-11-05');