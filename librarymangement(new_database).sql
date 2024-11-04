CREATE DATABASE LibraryManagement;
USE LibraryManagement;

-- Tài liệu
CREATE TABLE Documents (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,	-- tiêu đề
    Type varchar(100),
    Author VARCHAR(255),
    Publisher VARCHAR(255),	-- nhà xuất bản
    YearPublished INT,	-- năm xuất bản
    Quantity INT DEFAULT 1	-- số lượng
);

-- Người dùng
CREATE TABLE Users (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(255) NOT NULL,
    BirthDate DATE,
    Email VARCHAR(255) UNIQUE,
    PhoneNumber VARCHAR(15),
    Address VARCHAR(255)
);

-- Hồ sơ mượn trả
CREATE TABLE BorrowRecord (
    BorrowID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    DocumentID INT,
    BorrowDate DATE,
    ReturnDate DATE,
    FOREIGN KEY (DocumentID) REFERENCES Documents(ID),
    FOREIGN KEY (UserID) REFERENCES Users(ID)
);

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

insert into users (fullname, birthdate, email, phonenumber, address)
values ("Nguyễn Văn A", '2003-05-12', "23011701@vnu.edu.vn", "0973462181", "44/70 Trần Thái Tông-Dịch Vọng Hậu-Cầu Giấy-Hà Nội");
insert into users (fullname, birthdate, email, phonenumber, address)
values ("Trần Anh B", '2005-07-26', "23011702@vnu.edu.vn", "0347868172", "12 Trung Kính-Trung Hòa-Cầu Giấy-Hà Nội");
insert into users (fullname, birthdate, email, phonenumber, address)
values ("Trần Tuấn C", '2003-11-18', "23011703@vnu.edu.vn", "0385662101", "120 Trung Kính-Yên Hòa-Cầu Giấy-Hà Nội");
insert into users (fullname, birthdate, email, phonenumber, address)
values ("Nguyễn Cảnh D", '2000-02-16', "23011704@vnu.edu.vn", "0975684972", "63 Trần Bình-Mai Dịch-Cầu Giấy-Hà Nội");
insert into users (fullname, birthdate, email, phonenumber, address)
values ("Ngô Tuấn K", '1998-12-20', "23011705@vnu.edu.vn", "0389884686", "94 Trần Tử Bình-Nghĩa Tân-Cầu Giấy-Hà Nội");

insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Xác suất và các ứng dụng", "sách", "Đặng Hùng Thắng", "Giáo dục Việt Nam", null, 16);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Thống kê và ứng dụng", "sách", "Đặng Hùng Thắng", "Giáo dục", 1999, 20);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Phát triển thị trường trái phiếu chính phủ ở Việt Nam", "luận án, luận văn", "Nguyễn Thị Thụy Hương", "Khoa Kinh Tế - ĐHQGHN", 2006, 12);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Lịch sử văn minh Ả Rập", "sách", "Durant Will", "Bộ văn hóa thông tin", 2006, 19);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Tổ chức công việc làm ăn: kim chỉ nam của nhà doanh nghiệp", "sách", "Nguyễn Hiến Lê", "Hồng Đức; Công ty cổ phần sách Bizbooks", 2018, 10);

insert into borrowrecord (userID, documentID, borrowdate)
values (2, 3, '2024-09-10', null);
insert into borrowrecord (userID, documentID, borrowdate)
values (1, 4, '2024-07-24');
insert into borrowrecord (userID, documentID, borrowdate)
values (4, 3, '2024-10-15');
insert into borrowrecord (userID, documentID, borrowdate)
values (1, 2, '2024-10-30');

update borrowrecord 
set returndate = '2024-08-02'
where userid = 4;
update borrowrecord
set returndate = '2024-10-20'
where userid = 1;