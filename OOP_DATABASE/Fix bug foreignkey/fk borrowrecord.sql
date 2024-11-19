ALTER TABLE borrowrecord DROP Foreign Key borrowrecord_ibfk_1; 
ALTER TABLE borrowrecord ADD constraint borrowrecord_ibfk_1 foreign key (DocumentID) references documents(ID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE borrowrecord DROP Foreign Key borrowrecord_ibfk_2; 
ALTER TABLE borrowrecord ADD constraint borrowrecord_ibfk_2 foreign key (UserID) references users(ID) ON UPDATE CASCADE ON DELETE CASCADE;