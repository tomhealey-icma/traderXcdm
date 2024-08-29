Drop Table Trades IF EXISTS;

Drop Table AccountUsers IF EXISTS; 

Drop Table Positions IF EXISTS; 

Drop Table Accounts IF EXISTS; 

Drop Table CdmTrades IF EXISTS;

Drop Table CdmAccountUsers IF EXISTS; 

Drop Table CdmPositions IF EXISTS; 

Drop Table CdmAccounts IF EXISTS; 

Drop Sequence CdmACCOUNTS_SEQ IF EXISTS;

Drop Sequence ACCOUNTS_SEQ IF EXISTS;

CREATE TABLE Accounts ( ID INTEGER PRIMARY KEY, DisplayName VARCHAR (50) ) ; 

CREATE TABLE AccountUsers ( AccountID INTEGER NOT NULL, Username VARCHAR(15) NOT NULL, PRIMARY KEY (AccountID,Username));  

ALTER TABLE AccountUsers ADD FOREIGN KEY (AccountID) References Accounts(ID); 

CREATE TABLE Positions ( AccountID INTEGER , Security VARCHAR(15) , Updated TIMESTAMP, Quantity INTEGER, Primary Key (AccountID, Security) );  

Alter Table Positions ADD FOREIGN KEY (AccountID) References Accounts(ID) ; 

CREATE TABLE Trades ( ID Varchar (50) Primary Key, AccountID INTEGER, Created TIMESTAMP, Updated TIMESTAMP, Security VARCHAR (15) ,  Side VARCHAR(10) check (Side in ('Buy','Sell')),  Quantity INTEGER check Quantity > 0 , State VARCHAR(20) check (State in ('New', 'Processing', 'Settled', 'Cancelled'))) ;  

Alter Table Trades Add Foreign Key (AccountID) references Accounts(ID); 

CREATE SEQUENCE ACCOUNTS_SEQ start with 65000 INCREMENT BY 1;

--- SAMPLE DATA ---

INSERT into Accounts (ID, DisplayName) VALUES (22214, 'Test Account 20'); 
INSERT into Accounts (ID, DisplayName) VALUES (11413, 'Private Clients Fund TTXX'); 
INSERT into Accounts (ID, DisplayName) VALUES (42422, 'Algo Execution Partners'); 
INSERT into Accounts (ID, DisplayName) VALUES (52355, 'Big Corporate Fund'); 
INSERT into Accounts (ID, DisplayName) VALUES (62654, 'Hedge Fund TXY1'); 
INSERT into Accounts (ID, DisplayName) VALUES (10031, 'Internal Trading Book'); 
INSERT into Accounts (ID, DisplayName) VALUES (44044, 'Trading Account 1'); 

INSERT into AccountUsers (AccountID, Username) VALUES (22214, 'user01'); 
INSERT into AccountUsers (AccountID, Username) VALUES (22214, 'user03'); 
INSERT into AccountUsers (AccountID, Username) VALUES (22214, 'user09'); 
INSERT into AccountUsers (AccountID, Username) VALUES (22214, 'user05'); 
INSERT into AccountUsers (AccountID, Username) VALUES (22214, 'user07'); 

INSERT into AccountUsers (AccountID, Username) VALUES (62654, 'user09'); 
INSERT into AccountUsers (AccountID, Username) VALUES (62654, 'user05'); 
INSERT into AccountUsers (AccountID, Username) VALUES (62654, 'user07'); 
INSERT into AccountUsers (AccountID, Username) VALUES (62654, 'user01'); 

INSERT into AccountUsers (AccountID, Username) VALUES (10031, 'user01'); 
INSERT into AccountUsers (AccountID, Username) VALUES (10031, 'user03'); 
INSERT into AccountUsers (AccountID, Username) VALUES (10031, 'user09'); 

INSERT into AccountUsers (AccountID, Username) VALUES (44044, 'user09'); 
INSERT into AccountUsers (AccountID, Username) VALUES (44044, 'user05'); 
INSERT into AccountUsers (AccountID, Username) VALUES (44044, 'user07'); 
INSERT into AccountUsers (AccountID, Username) VALUES (44044, 'user04'); 
INSERT into AccountUsers (AccountID, Username) VALUES (44044, 'user01'); 
INSERT into AccountUsers (AccountID, Username) VALUES (44044, 'user06'); 
 

INSERT into Trades(ID, Created, Updated, Security, Side, Quantity, State, AccountID) VALUES('TRADE-22214-AABBCC', NOW(), NOW(), 'IBM', 'Sell', 100, 'Settled', 22214); 
INSERT into Trades(ID, Created, Updated, Security, Side, Quantity, State, AccountID) VALUES('TRADE-22214-DDEEFF', NOW(), NOW(), 'MS', 'Buy', 1000, 'Settled', 22214); 
INSERT into Trades(ID, Created, Updated, Security, Side, Quantity, State, AccountID) VALUES('TRADE-22214-GGHHII', NOW(), NOW(), 'C', 'Sell', 2000, 'Settled', 22214); 

INSERT into Positions (AccountID, Security, Updated, Quantity) VALUES(22214, 'MS',NOW(), 1000); 
INSERT into Positions (AccountID, Security, Updated, Quantity) VALUES(22214, 'IBM',NOW(), -100); 
INSERT into Positions (AccountID, Security, Updated, Quantity) VALUES(22214, 'C',NOW(), -2000); 


INSERT into Trades(ID, Created, Updated, Security, Side, Quantity, State, AccountID) VALUES('TRADE-52355-AABBCC', NOW(), NOW(), 'BAC', 'Sell', 2400, 'Settled', 52355); 
INSERT into Positions (AccountID, Security, Updated, Quantity) VALUES(52355, 'BAC',NOW(), -2400); 


--- CDM DATA ---

Drop Table CdmTrades IF EXISTS;

Drop Table CdmAccountUsers IF EXISTS; 

Drop Table CdmPositions IF EXISTS; 

Drop Table CdmAccounts IF EXISTS; 

Drop Sequence CdmACCOUNTS_SEQ IF EXISTS;

CREATE TABLE CdmAccounts ( ID INTEGER PRIMARY KEY, DisplayName VARCHAR (50), CdmAccountObj JSON(200) ) ; 

CREATE TABLE CdmAccountUsers ( AccountID INTEGER NOT NULL, Username VARCHAR(15) NOT NULL, CdmAccountUsersObj JSON(200), PRIMARY KEY (AccountID,Username));  

ALTER TABLE CdmAccountUsers ADD FOREIGN KEY (AccountID) References CdmAccounts(ID); 

CREATE TABLE CdmPositions ( AccountID INTEGER , Security VARCHAR(15) , Updated TIMESTAMP, Quantity INTEGER, CdmPositions JSON(200), Primary Key (AccountID, Security) );  

Alter Table CdmPositions ADD FOREIGN KEY (AccountID) References CdmAccounts(ID) ; 

CREATE TABLE CdmTrades ( ID Varchar (50) Primary Key, AccountID INTEGER, Created TIMESTAMP, Updated TIMESTAMP, Security VARCHAR (15) ,  Side VARCHAR(10) check (Side in ('Buy','Sell')),  Quantity INTEGER check Quantity > 0 , State VARCHAR(20) check (State in ('New', 'Processing', 'Settled', 'Cancelled')), CdmTrades JSON(200)) ;  

Alter Table CdmTrades Add Foreign Key (AccountID) references CdmAccounts(ID); 

CREATE SEQUENCE CdmACCOUNTS_SEQ start with 65000 INCREMENT BY 1;

INSERT into CdmAccounts (ID, DisplayName, CdmAccountObj) VALUES (22214, 'Test Account 20','{"name":{"value":"Test Account 20"},"partyId":[{"identifier":{"value":"22214"}}]}'); 
INSERT into CdmAccounts (ID, DisplayName,CdmAccountObj) VALUES (11413, 'Private Clients Fund TTXX','{"name":{"value":"Private Clients Fund TTXX"},"partyId":[{"identifier":{"value":"11413"}}]}'); 
INSERT into CdmAccounts (ID, DisplayName, CdmAccountObj) VALUES (42422, 'Algo Execution Partners','{"name":{"value":"Algo Execution Partners"},"partyId":[{"identifier":{"value":"42422"}}]}'); 
INSERT into CdmAccounts (ID, DisplayName, CdmAccountObj) VALUES (52355, 'Big Corporate Fund','{"name":{"value":"Big Corporate Fund"},"partyId":[{"identifier":{"value":"52355"}}]}'); 
INSERT into CdmAccounts (ID, DisplayName, CdmAccountObj) VALUES (62654, 'Hedge Fund TXY1','{"name":{"value":"Hedge Fund TXY1"},"partyId":[{"identifier":{"value":"62654"}}]}'); 
INSERT into CdmAccounts (ID, DisplayName, CdmAccountObj) VALUES (10031, 'Internal Trading Book','{"name":{"value":"Internal Trading Book"},"partyId":[{"identifier":{"value":"10031"}}]}'); 
INSERT into CdmAccounts (ID, DisplayName, CdmAccountObj) VALUES (44044, 'Trading Account 1','{"name":{"value":"Trading Account 1"},"partyId":[{"identifier":{"value":"44044"}}]}');

