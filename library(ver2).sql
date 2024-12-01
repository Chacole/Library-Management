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

INSERT INTO Users (Name, Email, Address, Phone) VALUES
('User1', 'user1@example.com', '123 Main St', '123-456-7890'),
('User2', 'user2@example.com', '456 Main St', '456-789-0123'),
('User3', 'user3@example.com', '789 Main St', '789-012-3456');

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

insert into manageraccount(accountID, fullname, email, password) 
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

INSERT INTO Documents (Author, Title, Type, Year, Quantity, ImagePath) VALUES
('Micheal', 'World Class', 'football', 2018, 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7qQoZIq2I3VuHkOeuFHVBjbof1RlLp_OJQQ&s'),
('Ben B', 'GOAT', 'football', 2007, 1, 'https://thanhnien.mediacdn.vn/uploaded/minhnguyet/2016_07_30/harrypotter_YLUP.jpg?width=500'),
('Peter', 'Sleepy', 'comedy', 2015, 3, 'https://cafebiz.cafebizcdn.vn/2019/11/13/photo-1-1573613930390416410456.jpg'),
('Tuan Tran', 'Great of all time', 'football', 2024, 2, 'https://thuviensach.vn/img/news/2022/11/larger/9451-doan-ho-nhan-1.jpg'),
('Tuan', 'M10 & CR7', 'football', 2023, 0, 'https://intamphuc.vn/wp-content/uploads/2023/06/mau-bia-sach-dep-2.jpg'),
('Sophia Rain', 'Language of love', 'theory', 2017, 4, 'https://thanhnien.mediacdn.vn/Uploaded/minhnguyet/2022_05_08/bia-sach2-9886.jpg'),
('AuthorX', 'Book Y', 'fiction', 2021, 5, 'https://img.pikbest.com/templates/20211021/bg/7e16ef5a6a47da9c9e76bb6c1b28a15a_117562.png!w700wp'),
('AuthorY', 'Book Z', 'science', 2019, 6, 'https://images.tuyensinh247.com/picture/images_question/1664871886-pxlm.jpg');

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
	VALUES (NameK, BirthDateK, EmailK, AddressK, PhoneK);
    
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