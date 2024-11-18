CREATE DATABASE LibraryManagement;
USE LibraryManagement;

-- Tài liệu
CREATE TABLE Documents (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,	-- tiêu đề
    ISBN VARCHAR(45),
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
    Phone VARCHAR(15),
    Address VARCHAR(255)
);

-- Tài khoản người dùng
CREATE TABLE Accounts (
	AccountID INT PRIMARY KEY,
    FullName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE,
    Password VARCHAR(100),
    FOREIGN KEY (AccountID) REFERENCES Users(ID)
);

-- Hồ sơ mượn trả
CREATE TABLE BorrowRecord (
    BorrowID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    UserName VARCHAR(255),
    DocumentID INT,
    DocumentTitle VARCHAR(255),
    BorrowDate DATE,
    ReturnDate DATE,
    FOREIGN KEY (DocumentID) REFERENCES Documents(ID),
    FOREIGN KEY (UserID) REFERENCES Users(ID)
);

insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Xác suất và các ứng dụng", "book", "Đặng Hùng Thắng", "Giáo dục Việt Nam", null, 16);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Thống kê và ứng dụng", "book", "Đặng Hùng Thắng", "Giáo dục", 1999, 20);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Phát triển thị trường trái phiếu chính phủ ở Việt Nam", "thesis", "Nguyễn Thị Thụy Hương", "Khoa Kinh Tế - ĐHQGHN", 2006, 12);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Lịch sử văn minh Ả Rập", "book", "Durant Will", "Bộ văn hóa thông tin", 2006, 19);
insert into documents (title, type, author, publisher, yearpublished, quantity)
values ("Tổ chức công việc làm ăn: kim chỉ nam của nhà doanh nghiệp", "book", "Nguyễn Hiến Lê", "Hồng Đức; Công ty cổ phần sách Bizbooks", 2018, 10);
