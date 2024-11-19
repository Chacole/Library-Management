DROP PROCEDURE IF EXISTS DeleteUser;
DELIMITER //
CREATE PROCEDURE DeleteUser (IN EmailK VARCHAR(255))
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