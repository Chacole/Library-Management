DROP PROCEDURE IF EXISTS EditUser;
DELIMITER //
CREATE PROCEDURE EditUser(IN EmailKf VARCHAR(255), IN FullNameK VARCHAR(255), IN BirthDateK DATE, IN EmailKe VARCHAR(255), IN PhoneK VARCHAR(15), IN AddressK VARCHAR(255), IN PasswordK VARCHAR(100))
BEGIN
    SET sql_safe_updates = 0;
    UPDATE users
	SET FullName = IFNULL(NULLIF(FullNameK, ''), FullName),
    BirthDate = IFNULL(NULLIF(BirthDateK, ''), BirthDate),
    Email = IFNULL(NULLIF(EmailKe, ''), Email),
    Phone = IFNULL(NULLIF(PhoneK, ''), Phone),
    Address = IFNULL(NULLIF(AddressK, ''), Address)
	WHERE Email = EmailKf;
    
    UPDATE accounts
    SET Password = IFNULL(NULLIF(PasswordK, ''), Password)
    WHERE Email = EmailKf;
	SET sql_safe_updates = 1;
END //
DELIMITER ;