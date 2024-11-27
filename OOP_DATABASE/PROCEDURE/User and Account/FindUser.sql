DROP PROCEDURE IF EXISTS FindUser;
DELIMITER //
CREATE PROCEDURE FindUser(IN FullNameK VARCHAR(255), IN EmailK VARCHAR(255), IN PhoneK VARCHAR(15))
BEGIN
    SELECT * FROM users
    WHERE (FullName LIKE CONCAT('%', FullNameK, '%') OR FullNameK IS NULL OR FullNameK = '') 
    AND (Email LIKE CONCAT('%', EmailK, '%') OR EmailK IS NULL OR EmailK = '')
    AND (Phone LIKE CONCAT('%', PhoneK, '%') OR PhoneK IS NULL OR PhoneK = '');
END //
DELIMITER ;