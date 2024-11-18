DELIMITER $$

CREATE TRIGGER before_borrowrecord_insert
BEFORE INSERT ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Lấy UserID từ bảng Users dựa trên UserName
    SET NEW.UserID = (SELECT ID FROM Users WHERE FullName = NEW.UserName LIMIT 1);

    -- Lấy DocumentID từ bảng Documents dựa trên DocumentTitle
    SET NEW.DocumentID = (SELECT ID FROM Documents WHERE Title = NEW.DocumentTitle LIMIT 1);
END$$

CREATE TRIGGER before_borrowrecord_update
BEFORE UPDATE ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Cập nhật UserID nếu UserName thay đổi
    IF OLD.UserName != NEW.UserName THEN
        SET NEW.UserID = (SELECT ID FROM Users WHERE FullName = NEW.UserName LIMIT 1);
    END IF;

    -- Cập nhật DocumentID nếu DocumentTitle thay đổi
    IF OLD.DocumentTitle != NEW.DocumentTitle THEN
        SET NEW.DocumentID = (SELECT ID FROM Documents WHERE Title = NEW.DocumentTitle LIMIT 1);
    END IF;
END$$

DELIMITER ;