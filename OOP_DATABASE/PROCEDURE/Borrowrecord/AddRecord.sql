DROP PROCEDURE IF EXISTS AddRecord;
DELIMITER //
CREATE PROCEDURE AddRecord(IN UserNameK VARCHAR(255), IN DocumentTitleK VARCHAR(255), IN BorrowDateK DATE)
BEGIN
    INSERT INTO borrowrecord (username, documenttitle, borrowdate)
    VALUES (UserNameK, DocumentTitleK, BorrowDateK);
END //
DELIMITER ;