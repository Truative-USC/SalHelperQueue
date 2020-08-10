
USE HelperQueue;

DROP TABLE IF EXISTS Class;
DROP TABLE IF EXISTS CourseProducers;


CREATE TABLE Class (
	classID INT(40) PRIMARY KEY auto_increment, -- make sure large enough value
	prefix VARCHAR(7) NOT NULL,
    num INT(3) NOT NULL
);



INSERT INTO Class (prefix, num) VALUES
 ('CSCI', 103),
 ('CSCI', 104),
 ('CSCI', 109),
 ('CSCI', 110),
 ('CSCI', 170),
 ('CSCI', 201),
 ('CSCI', 270),
 ('CSCI', 280),
 ('CSCI', 281),
 ('CSCI', 310),
 ('CSCI', 350),
 ('CSCI', 353),
 ('CSCI', 360);
 
 
 CREATE TABLE CourseProducers (
	CPID INT(40) PRIMARY KEY auto_increment, -- make sure large enough value
	fname VARCHAR(20) NOT NULL,
    lname VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    classID INT(3) NOT NULL
   -- FOREIGN KEY fkclass(classID) REFERENCES Class(num)
);

INSERT INTO CourseProducers (fname, lname, email, classID) VALUES 
('Eric', 'Test', 'lungyouy@usc.edu', 201),
('Ahmed', 'Test', 'aldulaim@usc.edu', 201),
('Valerie', 'Test', 'wangvale@usc.edu', 201),
('Mujahid', 'Test', 'mujahidn@usc.edu', 201),
('Sahil', 'Test', 'sahilaga@usc.edu', 201),
('Peter', 'Kaminsky', 'pkaminsk@usc.edu', 201),
('Helena', 'Rhee', 'helenarh@usc.edu', 201),
('Michelle', 'Yin', 'yinm@usc.edu', 201),
('Natalie', 'Monger', 'monger@usc.edu', 201),
('Janson', 'Lau', 'jansonla@usc.edu', 201),
('Annie', 'Song', 'annieson@usc.edu', 201),
('Ruth', 'Libowsky', 'libowsky@usc.edu', 201);


 
 