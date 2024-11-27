DELIMITER $$

CREATE TRIGGER before_borrowrecord_insert
BEFORE INSERT ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Lấy UserName từ bảng Users dựa trên UserID
    SET NEW.UserName = (SELECT FullName FROM Users WHERE ID = NEW.UserID LIMIT 1);
    IF NEW.UserName IS NULL THEN
        SET NEW.UserName = 'Unknown User';
    END IF;

    -- Lấy DocumentTitle từ bảng Documents dựa trên DocumentID
    SET NEW.DocumentTitle = (SELECT Title FROM Documents WHERE ID = NEW.DocumentID LIMIT 1);
    IF NEW.DocumentTitle IS NULL THEN
        SET NEW.DocumentTitle = 'Unknown Document';
    END IF;
END$$

CREATE TRIGGER before_borrowrecord_update
BEFORE UPDATE ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Cập nhật UserName nếu UserID thay đổi
    IF OLD.UserID != NEW.UserID THEN
        SET NEW.UserName = (SELECT FullName FROM Users WHERE ID = NEW.UserID LIMIT 1);
        IF NEW.UserName IS NULL THEN
            SET NEW.UserName = 'Unknown User';
        END IF;
    END IF;

    -- Cập nhật DocumentTitle nếu DocumentID thay đổi
    IF OLD.DocumentID != NEW.DocumentID THEN
        SET NEW.DocumentTitle = (SELECT Title FROM Documents WHERE ID = NEW.DocumentID LIMIT 1);
        IF NEW.DocumentTitle IS NULL THEN
            SET NEW.DocumentTitle = 'Unknown Document';
        END IF;
    END IF;
END$$

DELIMITER ;