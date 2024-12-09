CREATE TABLE DocumentReviews (
	ID INT AUTO_INCREMENT PRIMARY KEY,
	DocumentID INT,
    UserID INT,
    UserName VARCHAR(100),
    Rating DECIMAL(10, 1) CHECK (Rating >= 1.0 AND Rating <= 5.0),
    Comment TEXT,
    FOREIGN KEY (DocumentID) REFERENCES Documents(ID),
    FOREIGN KEY (UserID) REFERENCES Users(ID)
);

DELIMITER $$
CREATE TRIGGER before_documentreviews_insert
BEFORE INSERT ON DocumentReviews
FOR EACH ROW
BEGIN
    -- Lấy UserName từ bảng Users dựa trên UserID
    SET NEW.UserName = (SELECT Name FROM Users WHERE ID = NEW.UserID LIMIT 1);
    IF NEW.UserName IS NULL THEN
        SET NEW.UserName = 'Unknown User';
    END IF;
END$$

CREATE TRIGGER before_documentreviews_update
BEFORE UPDATE ON DocumentReviews
FOR EACH ROW
BEGIN
    -- Cập nhật UserName nếu UserID thay đổi
    IF OLD.UserID != NEW.UserID THEN
        SET NEW.UserName = (SELECT Name FROM Users WHERE ID = NEW.UserID LIMIT 1);
        IF NEW.UserName IS NULL THEN
            SET NEW.UserName = 'Unknown User';
        END IF;
    END IF;
END$$
DELIMITER ;

DELIMITER //
CREATE PROCEDURE AddReview(IN DocumentIDK INT, IN UserIDK INT, IN RatingK DECIMAL(10, 1), IN CommentK TEXT)
BEGIN
	INSERT INTO DocumentReviews(DocumentID, UserID, Rating, Comment)
	VALUES (DocumentIDK, UserIDK, RatingK, CommentK);
END;

CREATE PROCEDURE FindReview(IN DocumentIDK INT, IN UserIDK INT)
BEGIN
    SELECT * FROM DocumentReviews
	WHERE (DocumentID LIKE CONCAT('%', DocumentIDK, '%') OR DocumentIDK IS NULL OR DocumentIDK = 0)
	AND (UserID LIKE CONCAT('%', UserIDK, '%') OR UserIDK IS NULL OR UserIDK = 0);
END; 

CREATE PROCEDURE EditReview(IN DocumentIDKf INT, IN UserIDKf INT, IN DocumentIDK INT, IN UserIDK INT, IN RatingK DECIMAL(10, 1), IN CommentK TEXT)
BEGIN
	SET sql_safe_updates = 0;
	UPDATE DocumentReviews
	SET DocumentID = IFNULL(NULLIF(DocumentIDK, 0), DocumentID),
	UserID = IFNULL(NULLIF(UserIDK, 0), UserID),
	Rating = IFNULL(NULLIF(RatingK, 0.0), Rating),
	Comment = IFNULL(NULLIF(CommentK, ''), Comment)
	WHERE DocumentID = DocumentIDKf AND UserID = UserIDKf;
	SET sql_safe_updates = 1;
END;

CREATE PROCEDURE DeleteReview (IN DocumentIDK INT, IN UserIDK INT)
BEGIN
	SET sql_safe_updates = 0;
	SET @deleted_id = (SELECT ID FROM DocumentReviews WHERE DocumentID = DocumentIDK AND UserID = UserIDK); 
	DELETE FROM DocumentReviews 
	WHERE DocumentID = DocumentIDK AND UserID = UserIDK; 
    -- Đặt lại các ID > ID user đã xóa
	UPDATE DocumentReviews 
	SET ID = ID - 1 
	WHERE ID > @deleted_id; 
	-- Đặt lại AUTO_INCREMENT
	SET @max_id = IFNULL((SELECT MAX(ID) FROM DocumentReviews), 0); 
	SET @sql = CONCAT('ALTER TABLE DocumentReviews AUTO_INCREMENT = ', @max_id + 1);
	PREPARE stmt FROM @sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SET sql_safe_updates = 1;
END;
// DELIMITER ;