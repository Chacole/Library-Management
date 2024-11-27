DROP PROCEDURE IF EXISTS DeleteRecord;
DELIMITER //
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
END //
DELIMITER ;