DELIMITER $$

CREATE TRIGGER after_borrowrecord_insert
AFTER INSERT ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Cập nhật DocumentTitle từ bảng Documents
    UPDATE BorrowRecord
    SET DocumentTitle = (SELECT Title FROM Documents WHERE ID = NEW.DocumentID)
    WHERE BorrowID = NEW.BorrowID;

    -- Cập nhật UserName từ bảng Users
    UPDATE BorrowRecord
    SET UserName = (SELECT Name FROM Users WHERE ID = NEW.UserID)
    WHERE BorrowID = NEW.BorrowID;
END$$

CREATE TRIGGER after_borrowrecord_update
AFTER UPDATE ON BorrowRecord
FOR EACH ROW
BEGIN
    -- Cập nhật DocumentTitle từ bảng Documents nếu DocumentID thay đổi
    IF OLD.DocumentID != NEW.DocumentID THEN
        UPDATE BorrowRecord
        SET DocumentTitle = (SELECT Title FROM Documents WHERE ID = NEW.DocumentID)
        WHERE BorrowID = NEW.BorrowID;
    END IF;

    -- Cập nhật UserName từ bảng Users nếu UserID thay đổi
    IF OLD.UserID != NEW.UserID THEN
        UPDATE BorrowRecord
        SET UserName = (SELECT Name FROM Users WHERE ID = NEW.UserID)
        WHERE BorrowID = NEW.BorrowID;
    END IF;
END$$

DELIMITER ;