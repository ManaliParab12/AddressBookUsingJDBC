SHOW DATABASE;
USE addressbook_service;

CREATE TABLE Person (
id INT NOT NULL,
firstName VARCHAR (100) NOT NULL,
lastName VARCHAR (100) NOT NULL,
address VARCHAR (100) NOT NULL,
city VARCHAR (100) NOT NULL,
state VARCHAR (100) NOT NULL,
zip INT NOT NULL,
phoneNumber VARCHAR NOT NULL,
emailId VARCHAR (100) NOT NULL,
startDate DATE NOT NULL,
PRIMARY KEY (id)
);

INSERT INTO Person (id, firstName, lastName, address, city, state, zip, phoneNumber, emailId, startDate) VALUES
     (1, 'Manali', 'Parab', 'Virar', 'Mumbai', 'Maharashtra', 401305, 8149877402, 'manaliparab10@gmail.com', '2016-01-03'),
     (2, 'Apurva', 'Bhagat', 'Saigaon', 'Raigad', 'Maharashtra', 455690, 7125649385, 'apurvabhagat2378@gmail.com', '2017-09-12'),
     (3, 'Sayali', 'Patil', 'Tiswadi', 'Panaji', 'Goa', 451236, 8923456712, 'patilsayali@gmail.com', '2018-02-09');

mysql> SELECT * FROM Person;