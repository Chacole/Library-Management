DROP PROCEDURE IF EXISTS Register;
DELIMITER //
CREATE PROCEDURE Register (IN FullNameK VARCHAR(255), IN BirthDateK DATE, IN EmailK VARCHAR(255), IN PhoneK VARCHAR(15), IN AddressK VARCHAR(255), IN PasswordK VARCHAR(100))
BEGIN
	INSERT INTO users (fullname, birthdate, email, phone, address)
	VALUES (FullNameK, BirthDateK, EmailK, PhoneK, AddressK);
	SET sql_safe_updates = 0;
	UPDATE accounts
	SET password = PasswordK
	WHERE FullName = FullNameK;
	SET sql_safe_updates = 1;
END;
// DELIMITER ;