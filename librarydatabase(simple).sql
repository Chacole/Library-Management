create database LibraryDatabase;
use LibraryDatabase;

create table Users (
	ID int auto_increment primary key,
    FullName varchar(255) not null,
    Email varchar(255) unique,
    Address varchar(255),
    Phone varchar(15)
);

create table Documents (
	ID int auto_increment primary key,
    Title varchar(255) not null,
    Author varchar(255),
    Type varchar(50),
    Year int,
    Quantity int default 1
);

create table BorrowRecord (
	BorrowID int auto_increment primary key,
	UserID int,
    DocumentID int,
    BorrowDate date,
    ReturnDate date,
    foreign key (UserID) references Users(ID),
    foreign key (DocumentID) references Documents(ID)
);

insert into users ( fullname, email, address, phone)
values ("Nguyễn Văn A", "23011701@vnu.edu.vn", "44/70 Trần Thái Tông-Dịch Vọng Hậu-Cầu Giấy-Hà Nội", "0973462181");
insert into users ( fullname, email, address, phone)
values ("Trần Anh B", "23011702@vnu.edu.vn", "12 Trung Kính-Trung Hòa-Cầu Giấy-Hà Nội","0347868172");
insert into users ( fullname, email, address, phone)
values ("Trần Tuấn C", "23011703@vnu.edu.vn", "120 Trung Kính-Yên Hòa-Cầu Giấy-Hà Nội","0385662101");
insert into users ( fullname, email, address, phone)
values ("Nguyễn Cảnh D", "23011704@vnu.edu.vn", "63 Trần Bình-Mai Dịch-Cầu Giấy-Hà Nội","0975684972");
insert into users ( fullname, email, address, phone)
values ("Ngô Tuấn K", "23011705@vnu.edu.vn", "94 Trần Tử Bình-Nghĩa Tân-Cầu Giấy-Hà Nội","0389884686");

insert into documents (title, author, type, year, quantity)
values ("xác suất và các ứng dụng", "Đặng Hùng Thắng", "sách", 2015, 10);
insert into documents (title, author, type, year, quantity)
values ("thống kê và ứng dụng", "Đặng Hùng Thắng", "sách", 2009, 12);
insert into documents (title, author, type, year, quantity)
values ("phát triển thị trường trái phiếu chính phủ ở Việt Nam", "Nguyễn Thị Thụy Hương", "luận văn", 2006, 8);
insert into documents (title, author, type, year, quantity)
values ("lịch sử văn minh thế giới", "Vũ Dương Ninh", "sách", 2005, 10);
insert into documents (title, author, type, year, quantity)
values ("tiếng Việt thực hành", null, "sách", 2007, 6);

insert into borrowrecord (userID, documentID, borrowdate, returndate)
values (2, 3, '2024-09-10', null);
insert into borrowrecord (userID, documentID, borrowdate, returndate)
values (1, 4, '2024-07-24', '2024-08-02');
insert into borrowrecord (userID, documentID, borrowdate, returndate)
values (4, 3, '2024-10-15', '2024-10-20');
insert into borrowrecord (userID, documentID, borrowdate, returndate)
values (1, 2, '2024-09-24', null);
