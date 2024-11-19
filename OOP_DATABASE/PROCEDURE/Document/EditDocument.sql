DROP PROCEDURE IF EXISTS EditDocument;
DELIMITER //
CREATE PROCEDURE EditDocument(IN TitleKf VARCHAR(255), IN TitleKe VARCHAR(255), IN ISBNK VARCHAR(45), IN TypeK VARCHAR(100), IN AuthorK VARCHAR(255), IN PublisherK VARCHAR(255), IN YearPublishedK INT, IN QuantityK INT)
BEGIN
    SET sql_safe_updates = 0;
    UPDATE documents
	SET Title = IFNULL(NULLIF(TitleKe, ''), Title),
    ISBN = IFNULL(NULLIF(ISBNK, ''), ISBN),
    Type = IFNULL(NULLIF(TypeK, ''), Type),
    Author = IFNULL(NULLIF(AuthorK, ''), Author),
    Publisher = IFNULL(NULLIF(PublisherK, ''), Publisher),
    YearPublished = IFNULL(NULLIF(YearPublishedK, ''), YearPublished),
    Quantity = IFNULL(NULLIF(QuantityK, ''), Quantity)
	WHERE Title = TitleKf;
	SET sql_safe_updates = 1;
END //
DELIMITER ;