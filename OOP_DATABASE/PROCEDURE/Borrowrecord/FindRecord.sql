DROP PROCEDURE IF EXISTS FindRecord;
DELIMITER //
CREATE PROCEDURE FindRecord(IN BorrowIDK INT, IN UserNameK VARCHAR(255), IN DocumentTitleK VARCHAR(255))
BEGIN
    SELECT * FROM borrowrecord
    WHERE (BorrowID LIKE CONCAT('%', BorrowIDK, '%') OR BorrowIDK IS NULL OR BorrowIDK = '') 
    AND (UserName LIKE CONCAT('%', UserNameK, '%') OR UserNameK IS NULL OR UserNameK = '')
    AND (DocumentTitle LIKE CONCAT('%', DocumentTitleK, '%') OR DocumentTitleK IS NULL OR DocumentTitleK = '');
END //
DELIMITER ;