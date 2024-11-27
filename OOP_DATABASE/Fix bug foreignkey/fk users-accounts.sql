ALTER TABLE accounts DROP Foreign Key accounts_ibfk_1; 
ALTER TABLE accounts ADD constraint accounts_ibfk_1 foreign key (AccountID) references users(ID) ON UPDATE CASCADE ON DELETE CASCADE;