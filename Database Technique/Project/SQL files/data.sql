USE mydb;

INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES
("Kevin", "Fin", 73212, "Kevin.fin@gmail.com", "password@123");
INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES
("dummy1", "dummy_lastname1", 8262811, "dummy1@mail.com", "strongpassword1");
INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES
("dummy2", "dummy_lastname2", 8262812, "dummy1@mail.com", "strongpassword2");
INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES
("dummy3", "dummy_lastname3", 8262813, "dummy1@mail.com", "strongpassword3");
INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES
("dummy4", "dummy_lastname4", 8262814, "dummy1@mail.com", "strongpassword4");
INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES
("Kevin", "Fin", 73212, "Kevin.fin@gmail.com", "password@123");

SELECT * FROM applicant;


INSERT INTO prioreducation(School_Name, City, Country, Program, Grade, Applicant_ID) VALUES
("HKR", "Krinistand", "Sweden", "IB", "A", 2);
INSERT INTO prioreducation(School_Name, City, Country, Program, Grade, Applicant_ID) VALUES
("Vaxjo School", "Vaxjo", "Sweden", "IB", "C", 4);
INSERT INTO prioreducation(School_Name, City, Country, Program, Grade, Applicant_ID) VALUES
("Stockholm School", "Stockholm", "Sweden", "IB", "D", 5);
INSERT INTO prioreducation(School_Name, City, Country, Program, Grade, Applicant_ID) VALUES
("Osby School", "Osby", "Sweden", "IB", "100", 6);
INSERT INTO prioreducation(School_Name, City, Country, Program, Grade, Applicant_ID) VALUES
("Almhult School", "Almhult", "Sweden", "Swedish Program", "B", 3);
INSERT INTO prioreducation(School_Name, City, Country, Program, Grade, Applicant_ID) VALUES
("Good school", "Stirling", "Scotland", "IB", "AAA", 9);

SELECT * FROM prioreducation;

INSERT INTO university(Name, City, Country) VALUES
("HKR", "Krinistand", "Sweden");
INSERT INTO university(Name, City, Country) VALUES
("Stirling", "Stirling", "Scotland");
INSERT INTO university(Name, City, Country) VALUES
("UK School", "UK city", "UK");
INSERT INTO university(Name, City, Country) VALUES
("Warwick", "Warwick", "UK");
INSERT INTO university(Name, City, Country) VALUES
("LNU", "Vaxjo", "Sweden");

SELECT * FROM university;

INSERT INTO course(University_ID, Credits, Duration, Tuition, Course_Type, Start_Date, Teaching_Speed, Name) VALUES
(1, 180, 3, 500, 'Bachelor', "22-08-2022", 100, "Computer Science");
INSERT INTO course(University_ID, Credits, Duration, Tuition, Course_Type, Start_Date, Teaching_Speed, Name) VALUES
(2, 180, 3, 600, 'Bachelor', "23-08-2022", 100, "Physics");
INSERT INTO course(University_ID, Credits, Duration, Tuition, Course_Type, Start_Date, Teaching_Speed, Name) VALUES
(3, 180, 3, 700, 'Bachelor', "24-08-2022", 50, "Psychology");
INSERT INTO course(University_ID, Credits, Duration, Tuition, Course_Type, Start_Date, Teaching_Speed, Name) VALUES
(4, 180, 3, 800, 'Master', "25-08-2022", 50, "Economics");
INSERT INTO course(University_ID, Credits, Duration, Tuition, Course_Type, Start_Date, Teaching_Speed, Name) VALUES
(5, 180, 3, 900, 'Master', "26-08-2022", 100, "Software");

SELECT * FROM course;

INSERT INTO application(Applicant_ID, Course_ID, University_ID, Date, Application_Name, Visa) VALUES
(1, 1, 1, "05-01-2022", "Test", 'F1');
INSERT INTO application(Applicant_ID, Course_ID, University_ID, Date, Application_Name, Visa) VALUES
(2, 2, 2, "05-01-2022", "PhysicsApplication", 'F1');
INSERT INTO application(Applicant_ID, Course_ID, University_ID, Date, Application_Name, Visa) VALUES
(3, 3, 3, "06-01-2022", "PsychologyApplication", 'F2');
INSERT INTO application(Applicant_ID, Course_ID, University_ID, Date, Application_Name, Visa) VALUES
(3, 3, 3, "01-02-2020", "TestApplication", 'F3');
INSERT INTO application(Applicant_ID, Course_ID, University_ID, Date, Application_Name, Visa) VALUES
(4, 4, 4, "02-06-1999", "Application", 'F2');

SELECT * FROM application;


INSERT INTO payment(Application_ID, Amount, Card_Number, Payment_Type) VALUES 
(1, "500", 1234568901, 'Card');
INSERT INTO payment(Application_ID, Amount, Card_Number, Payment_Type) VALUES 
(2, "9999", 0987654321, 'Bank Transfer');
INSERT INTO payment(Application_ID, Amount, Card_Number, Payment_Type) VALUES 
(3, "600", 3181237, 'Card');
INSERT INTO payment(Application_ID, Amount, Card_Number, Payment_Type) VALUES 
(4, "51200", 137828739, 'Bank Transfer');
INSERT INTO payment(Application_ID, Amount, Card_Number, Payment_Type) VALUES 
(5, "125400", 168901, 'Card');

SELECT * FROM payment;