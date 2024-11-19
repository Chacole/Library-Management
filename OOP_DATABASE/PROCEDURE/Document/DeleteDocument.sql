DROP PROCEDURE IF EXISTS DeleteDocument;
DELIMITER //
CREATE PROCEDURE DeleteDocument(IN TitleK VARCHAR(255))
BEGIN
    SET sql_safe_updates = 0;
    SET @deleted_id = (SELECT ID FROM documents WHERE title = TitleK);
	DELETE FROM documents
	WHERE Title = TitleK;
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
END //
DELIMITER ;