DELIMITER $$

CREATE TRIGGER sync_accounts_insert
AFTER INSERT ON users
FOR EACH ROW
BEGIN
    -- Thêm thông tin vào bảng accounts khi thêm bản ghi mới vào users
    INSERT INTO accounts (AccountID, Fullname, Email)
    VALUES (NEW.ID, NEW.Fullname, NEW.Email);
END$$

CREATE TRIGGER sync_accounts_update
AFTER UPDATE ON users
FOR EACH ROW
BEGIN
    -- Cập nhật thông tin trong bảng accounts khi có thay đổi trong bảng users
    UPDATE accounts
    SET Fullname = NEW.Fullname, Email = NEW.Email
    WHERE AccountID = NEW.ID;
END$$

CREATE TRIGGER delete_account_after_user_delete
AFTER DELETE ON users
FOR EACH ROW
BEGIN
    -- Xóa bản ghi trong bảng accounts khi xóa người dùng
    DELETE FROM accounts 
    WHERE AccountID = OLD.ID;
END$$

DELIMITER ;
