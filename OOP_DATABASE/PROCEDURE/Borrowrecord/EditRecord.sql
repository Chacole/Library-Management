DROP PROCEDURE IF EXISTS EditRecord;
DELIMITER //
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
END //
DELIMITER ;