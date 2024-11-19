DROP PROCEDURE IF EXISTS EditRecord;
DELIMITER //
CREATE PROCEDURE EditRecord(IN BorrowIDK INT,IN UserNameK VARCHAR(255), IN DocumentTitleK VARCHAR(255), IN BorrowDateK DATE, IN ReturnDateK DATE)
BEGIN
    SET sql_safe_updates = 0;
    UPDATE borrowrecord
	SET UserName = IFNULL(NULLIF(UserNameK, ''), UserName),
    DocumentTitle = IFNULL(NULLIF(DocumentTitleK, ''), DocumentTitle),
    BorrowDate = IFNULL(NULLIF(BorrowDateK, ''), BorrowDate),
    ReturnDate = IFNULL(NULLIF(ReturnDateK, ''), ReturnDate)
	WHERE BorrowID = BorrowIDK;
	SET sql_safe_updates = 1;
END //
DELIMITER ;