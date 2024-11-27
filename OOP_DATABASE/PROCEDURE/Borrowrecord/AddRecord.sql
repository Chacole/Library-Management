DROP PROCEDURE IF EXISTS AddRecord;
DELIMITER //
CREATE PROCEDURE AddRecord(IN UserIDK INT, IN DocumentIDK INT, IN BorrowDateK DATE)
BEGIN
    INSERT INTO borrowrecord (userID, documentID, borrowdate)
    VALUES (UserIDK, DocumentIDK, BorrowDateK);
END //
DELIMITER ;