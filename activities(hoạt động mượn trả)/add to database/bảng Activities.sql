CREATE TABLE Activities (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    UserName VARCHAR(100),
    Request ENUM('return', 'borrow'),
    DocumentID INT,
    DocumentTitle VARCHAR(150),
    Status ENUM('pending', 'confirmed', 'denied'),
    Time DATETIME,
    FOREIGN KEY (DocumentID) REFERENCES Documents(ID),
    FOREIGN KEY (UserID) REFERENCES Users(ID)
);

DELIMITER $$
CREATE TRIGGER before_activities_insert
BEFORE INSERT ON activities
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

CREATE TRIGGER before_activities_update
BEFORE UPDATE ON activities
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
DELIMITER ;

DELIMITER //
CREATE PROCEDURE DeleteActivities (IN UserIDK INT)
BEGIN
	SET sql_safe_updates = 0;
	SET @deleted_id = (SELECT ID FROM Activities WHERE UserID = UserIDK LIMIT 1); 
	DELETE FROM Activities 
	WHERE UserID = UserIDK; 
    -- Đặt lại các ID > ID user đã xóa
	UPDATE Activities 
	SET ID = ID - 1 
	WHERE ID > @deleted_id; 
	-- Đặt lại AUTO_INCREMENT
	SET @max_id = IFNULL((SELECT MAX(ID) FROM Activities), 0); 
	SET @sql = CONCAT('ALTER TABLE Activities AUTO_INCREMENT = ', @max_id + 1);
	PREPARE stmt FROM @sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sql_safe_updates = 1;
END;
// DELIMITER ;