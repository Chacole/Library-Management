DELIMITER $$

-- Trigger giảm số lượng khi tài liệu được mượn
CREATE TRIGGER before_insert_borrowrecord
BEFORE INSERT ON borrowrecord
FOR EACH ROW
BEGIN
    UPDATE documents
    SET Quantity = Quantity - 1
    WHERE ID = NEW.DocumentID;
END$$

-- Trigger tăng số lượng khi tài liệu được trả
CREATE TRIGGER after_update_borrowrecord
AFTER UPDATE ON borrowrecord
FOR EACH ROW
BEGIN
    IF NEW.ReturnDate IS NOT NULL AND OLD.ReturnDate IS NULL THEN
        UPDATE documents
        SET Quantity = Quantity + 1
        WHERE ID = NEW.DocumentID;
    END IF;
END$$

DELIMITER ;