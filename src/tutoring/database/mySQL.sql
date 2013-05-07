drop table if exists ParaprofessionalSession;
drop table if exists ParaprofessionalCategory;
drop table if exists Paraprofessional;
drop table if exists Course;
drop table if exists Subject;
drop table if exists Category;
drop table if exists Location;
drop table if exists User;
drop table if exists Role;
drop table if exists Client;
drop table if exists Teacher;
drop table if exists Agenda; 	
drop table if exists AgendaCategory;
 
create table Client (
 
                            	clientID int primary key auto_increment,
                            	fName varchar(100) not null,
                            	lName varchar(100) not null,
                            	phone varchar(15),
                            	email varchar(100)
) Engine=InnoDB;
 
create table Category (
                            	
                            	categoryID int primary key auto_increment,
                            	name varchar(50) not null
) Engine=InnoDB;
 
create table Location (
 
                            	locationID int primary key auto_increment,
                            	name varchar(50) not null
) Engine=InnoDB;
 
create table Role(
 
                            	roleID int primary key auto_increment,
                            	type varchar(100) not null
) Engine=InnoDB;
 
create table User (
                            	userName varchar(50) primary key not null,
                            	password varchar(50) not null,
                            	fName varchar(100) not null,
                            	lName varchar(100) not null,
                            	roleID int not null,
                            	constraint fk_user_roleid foreign key (roleID) references Role(roleID)
) Engine=InnoDB;
 
create table Teacher (
                            	
                            	teacherID int primary key auto_increment,
                            	fName varchar(100) not null,
                            	lName varchar(100) not null
) Engine=InnoDB;
 
create table Subject (
                            	
                            	subjectID int primary key auto_increment,
                            	categoryID int not null,
                            	abbrevName varchar(10) not null,
                            	constraint fk_subject_categoryid foreign key (categoryID) references Category(categoryID)
) Engine=InnoDB;
 
create table Course (
                            	courseID int primary key auto_increment,
                            	teacherID int not null,
                            	subjectID int not null,
                            	level int not null,
                            	constraint fk_course_teacherid foreign key (teacherID) references Teacher(teacherID),
                            	constraint fk_course_subjectid foreign key (subjectID) references Subject(subjectID)
) Engine=InnoDB;
 
create table Paraprofessional (
                            	paraprofessionalID int primary key auto_increment,
                            	fName varchar(100) not null,
                            	lName varchar(100) not null,
                            	hireDate date not null,
                            	terminationDate date null,
                            	isClockedIn boolean not null,
                            	roleID int not null,
                            	constraint fk_paraprofessional_roleid foreign key(roleID) references Role(roleID)
) Engine=InnoDB;
 
create table ParaprofessionalCategory (
                            	paraprofessionalID int,
                            	categoryID int,
                            	constraint  pk_paraprofessionalcategory_composite primary key (paraprofessionalID, categoryID),
                            	constraint fk_paraprofessionalcategory_paraprofessionalid foreign key(paraprofessionalID) references Paraprofessional(ParaprofessionalID),
                            	constraint fk_paraprofessionalcategory_categoryid foreign key(categoryID) references Category(categoryID)
) Engine=InnoDB;
 
create table ParaprofessionalSession (
                            	paraprofessionalSessionID int primary key auto_increment,
                            	paraprofessionalID int not null,
                            	paraprofessionalCreatorID int not null,
                            	clientID int not null,
                            	courseID int not null,
                            	timeAndDateEntered timestamp not null,
                            	sessionStart timestamp null,
                            	sessionEnd timestamp null,
                            	grammarCheck boolean not null,
                            	notes text,
                            	locationID int not null,
                            	walkout boolean not null,
 
                            	constraint fk_paraprofessionalsession_paraprofessionalid foreign key(paraprofessionalID) references Paraprofessional(paraprofessionalID),
                            	constraint fk_paraprofessionalsession_clientid foreign key(clientID) references Client(clientID),
                            	constraint fk_paraprofessionalsession_courseid foreign key(courseID) references Course(courseID),
                            	constraint fk_paraprofessionalsession_locationid foreign key(locationID) references Location(locationID),
                            	constraint fk_paraprofessionalsession_paraprofessionalcreatorid foreign key(paraprofessionalCreatorID) references Paraprofessional(paraprofessionalID)
) Engine=InnoDB;
 
create table AgendaCategory (
                            	agendaCategoryID int primary key auto_increment,
                            	type varchar(100) not null
) Engine=InnoDB;
 
create table Agenda (
                            	agendaID int primary key auto_increment,
                            	date date not null,
                            	notes text not null,
                            	agendaCategoryID int not null,
 
                            	constraint fk_agenda_agendacategoryid foreign key(agendaCategoryID) references AgendaCategory(agendaCategoryID)
) Engine=InnoDB;
 
 
INSERT INTO `nbefus_tms`.`AgendaCategory` (`type`) VALUES ('Tutor Out');
INSERT INTO `nbefus_tms`.`AgendaCategory` (`type`) VALUES ('Appointment');
INSERT INTO `nbefus_tms`.`AgendaCategory` (`type`) VALUES ('Note');
 
INSERT INTO `nbefus_tms`.`Agenda` (`date`, `notes`, `agendaCategoryID`) VALUES ('2013-05-19 11:30:00', 'Precious not coming in today', 1);
INSERT INTO `nbefus_tms`.`Agenda` (`date`, `notes`, `agendaCategoryID`) VALUES ('2013-05-20 11:30:00', 'Fire Drill at 1:00 P.M.', 3);
INSERT INTO `nbefus_tms`.`Agenda` (`date`, `notes`, `agendaCategoryID`) VALUES ('2013-05-21 11:30:00', 'No tutors today', 1);
 
INSERT INTO `nbefus_tms`.`Teacher` (`fName`, `lName`) VALUES ('Edward', 'Souza');
INSERT INTO `nbefus_tms`.`Teacher` (`fName`, `lName`) VALUES ('Milica', 'Barjactarovic');
INSERT INTO `nbefus_tms`.`Teacher` (`fName`, `lName`) VALUES ('Yi', 'Zhu');
INSERT INTO `nbefus_tms`.`Teacher` (`fName`, `lName`) VALUES ('Carl', 'Farrell');
INSERT INTO `nbefus_tms`.`Teacher` (`fName`, `lName`) VALUES ('Curt', 'Powley');
 
INSERT INTO `nbefus_tms`.`Category` (`name`) VALUES ('MABS');
INSERT INTO `nbefus_tms`.`Category` (`name`) VALUES ('ENG');
INSERT INTO `nbefus_tms`.`Category` (`name`) VALUES ('LANG');
 
INSERT INTO `nbefus_tms`.`Subject` (`categoryID`, `abbrevName`) VALUES (1, 'CSCI');
INSERT INTO `nbefus_tms`.`Subject` (`categoryID`, `abbrevName`) VALUES (1, 'MATH');
INSERT INTO `nbefus_tms`.`Subject` (`categoryID`, `abbrevName`) VALUES (1, 'PHIL');
INSERT INTO `nbefus_tms`.`Subject` (`categoryID`, `abbrevName`) VALUES (1, 'IS');
INSERT INTO `nbefus_tms`.`Subject` (`categoryID`, `abbrevName`) VALUES (1, 'ACCT');
 
INSERT INTO `nbefus_tms`.`Course` (`teacherID`, `subjectID`, `level`) VALUES (1, 1, 3301);
INSERT INTO `nbefus_tms`.`Course` (`teacherID`, `subjectID`, `level`) VALUES (1, 1, 3201);
INSERT INTO `nbefus_tms`.`Course` (`teacherID`, `subjectID`, `level`) VALUES (1, 4, 6050);
INSERT INTO `nbefus_tms`.`Course` (`teacherID`, `subjectID`, `level`) VALUES (1, 4, 6065);
INSERT INTO `nbefus_tms`.`Course` (`teacherID`, `subjectID`, `level`) VALUES (5, 1, 4911);
INSERT INTO `nbefus_tms`.`Course` (`teacherID`, `subjectID`, `level`) VALUES (3, 2, 1301);
 
INSERT INTO `nbefus_tms`.`Role` (`type`) VALUES ('TUTOR');
INSERT INTO `nbefus_tms`.`Role` (`type`) VALUES ('MENTOR');
INSERT INTO `nbefus_tms`.`Role` (`type`) VALUES ('SIA');
INSERT INTO `nbefus_tms`.`Role` (`type`) VALUES ('ADMIN');
 
INSERT INTO `nbefus_tms`.`User` (`userName`, `password`, `fName`, `lName`, `roleID`) VALUES ('nbefus_admin', 'password', 'Nathaniel', 'Befus', '4');
INSERT INTO `nbefus_tms`.`User` (`userName`, `password`, `fName`, `lName`, `roleID`)  VALUES ('pbinas_admin', 'password', 'Precious', 'Binas', '4');
INSERT INTO `nbefus_tms`.`User` (`userName`, `password`, `fName`, `lName`, `roleID`) VALUES ('ssasahara_admin', 'password', 'Shohei', 'Sasahara', '4');
INSERT INTO `nbefus_tms`.`User` (`userName`, `password`, `fName`, `lName`, `roleID`)  VALUES ('nbefus_sia', 'password', 'Nathaniel', 'Befus', '3');
INSERT INTO `nbefus_tms`.`User` (`userName`, `password`, `fName`, `lName`, `roleID`) VALUES ('pbinas_sia', 'password', 'Precious', 'Binas', '3');
INSERT INTO `nbefus_tms`.`User` (`userName`, `password`, `fName`, `lName`, `roleID`) VALUES ('ssasahara_sia', 'password', 'Shohei', 'Sasahara', '3');
 
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Precious', 'Binas', '2013-03-20 10:00:00', default, 'false', '1');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Vicky', 'Cardenas', '2013-03-19 10:00:00', default, 'false', '1');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Christina', 'Bowmen', '2013-03-18 10:00:00', default, 'false', '1');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Ryan', 'Obrero', '2013-03-17 10:00:00', default, 'false', '1');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Kylie', 'Petermann', '2013-03-10 10:00:00', default, 'false', '2');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Jonathan', 'Low', '2013-03-20 10:00:00', default, 'false', '1');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Tawny', 'DeMello', '2013-03-18 10:00:00', default, 'false', '3');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Cheryl', 'Batara', '2013-03-01 10:00:00', default, 'false', '3');
INSERT INTO `nbefus_tms`.`Paraprofessional` (`fName`, `lName`, `hireDate`, `terminationDate`, `isClockedIn`, `roleID`) VALUES ('Anthony', 'Chai', '2013-02-20 10:00:00', default, 'false', '1');
 
 
 
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('1', '1');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('2', '2');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('2', '3');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('3', '1');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('4', '1');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('4', '2');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('5', '2');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('6', '1');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('9', '1');
INSERT INTO `nbefus_tms`.`ParaprofessionalCategory` (`paraprofessionalID`, `categoryID`) VALUES ('9', '3');
 
INSERT INTO `nbefus_tms`.`Location` (`name`) VALUES ('DOWNTOWN');
INSERT INTO `nbefus_tms`.`Location` (`name`) VALUES ('HAWAII LOA');
 
INSERT INTO `nbefus_tms`.`Client` (`fName`, `lName`, `phone`, `email`) VALUES ('Precious', 'Binas', '8082807117', 'pbinas@gmail.com');
INSERT INTO `nbefus_tms`.`Client` (`fName`, `lName`, `phone`, `email`) VALUES ('Nate', 'Befus', '7204277266', 'nbefus@gmail.com');
INSERT INTO `nbefus_tms`.`Client` (`fName`, `lName`, `phone`, `email`) VALUES ('Sho', 'Sasahra', '8087778694', 'ssasahara@gmail.com');
 
 
 
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (1, 4, 1, 1, NOW(), '2013-05-20 11:00:00', '2013-05-20 11:30:00', false, 'This is a note', 1, false);
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (1, 4, 2, 3, NOW(), '2013-05-20 10:00:00', '2013-05-20 11:00:00', false, 'This is a note 2', 1, false);
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (1, 4, 3, 2, NOW(), '2013-05-20 09:00:00', '2013-05-20 09:30:00', false, 'This is a note', 1, false);
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (2, 3, 2, 1, NOW(), '2013-05-20 12:00:00', '2013-05-20 12:50:00', false, 'This is another note', 2, false);
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (3, 3, 3, 3, NOW(), '2013-05-21 08:00:00', '2013-05-21 09:30:00', false, 'This is a good note', 2, false);
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (4, 4, 1, 6, NOW(), '2013-05-20 10:00:00', '2013-05-20 10:30:00', false, 'This is a great note', 1, false);
INSERT INTO `nbefus_tms`.`ParaprofessionalSession` (`paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES (5, 2, 2, 4, NOW(), '2013-05-20 11:00:00', '2013-05-20 11:30:00', false, 'This is a crazy note', 1, false);

INSERT INTO `ParaprofessionalSession` (`paraprofessionalSessionID`, `paraprofessionalID`, `paraprofessionalCreatorID`, `clientID`, `courseID`, `timeAndDateEntered`, `sessionStart`, `sessionEnd`, `grammarCheck`, `notes`, `locationID`, `walkout`) VALUES
(16, 9, 9, 2, 1, '2013-03-26 15:31:41', NULL, NULL, 0, '33', 1, 0),
(17, 3, 1, 1, 1, '2013-03-26 15:32:32', NULL, NULL, 0, '', 1, 0),
(19, 9, 9, 2, 1, '2013-03-26 15:43:42', NULL, NULL, 0, 'try', 1, 0),
(20, 9, 9, 2, 1, '2013-03-26 15:44:57', NULL, NULL, 0, '', 1, 0);

