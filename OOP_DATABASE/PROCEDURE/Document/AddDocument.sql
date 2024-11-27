DROP PROCEDURE IF EXISTS AddDocument;
DELIMITER //
CREATE PROCEDURE AddDocument(IN TitleK VARCHAR(255), IN ISBNK VARCHAR(45), IN TypeK VARCHAR(100), IN AuthorK VARCHAR(255), IN PublisherK VARCHAR(255), IN YearPublishedK INT, IN QuantityK INT)
BEGIN
    INSERT INTO documents (Title, ISBN, Type, Author, Publisher, YearPublished, Quantity)
    VALUES (TitleK, ISBNK, TypeK, AuthorK, PublisherK, YearPublishedK, QuantityK);
END //
DELIMITER ;