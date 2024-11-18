DROP PROCEDURE IF EXISTS FindDocument;
DELIMITER //
CREATE PROCEDURE FindDocument(IN ISBNK VARCHAR(45), IN TitleK VARCHAR(255), IN TypeK VARCHAR(100), IN AuthorK VARCHAR(255))
BEGIN
    SELECT * FROM documents
    WHERE (ISBN LIKE CONCAT('%', ISBNK, '%') OR ISBNK IS NULL OR ISBNK = '') 
    AND (Title LIKE CONCAT('%', TitleK, '%') OR TitleK IS NULL OR TitleK = '')
    AND (Type LIKE CONCAT('%', TypeK, '%') OR TypeK IS NULL OR TypeK = '')
    AND (Author LIKE CONCAT('%', AuthorK, '%') OR AuthorK IS NULL OR AuthorK = '');
END //
DELIMITER ;