
-- use the project database;
USE mydb;

-- The following command is used to select all the columns within the Applicant table.
SELECT * from applicant;

-- The following command was applied to select all the columns within the Prior Education table. 
SELECT * from prior_education;

--  following SQL command was applied to insert an applicant into the Applicant table.
INSERT INTO applicant(First_Name, Last_Name, Phone_Number, Email_Address, Password) VALUES 
("Kevin", "Fin", 073212, "kevin.fin@mail.com", "password@123");

-- The following SQL command was applied to insert an applicant’s prior education information into the Prior Education table.
INSERT INTO prior_education (School_Name, City, Country, Program, Grade, Applicant_ID) VALUES 
("Almhult School", "Almhult", "Sweden", "Swedish program", "B", 3);

-- The following SQL command was utilized to delete a tuple from the Applicant table.
DELETE FROM applicant WHERE Applicant_ID = 1;

-- The following SQL was used to delete a tuple from the Prior Education table.
DELETE FROM prior_education WHERE Applicant_ID = 1;
DELETE FROM prior_education WHERE Applicant_ID = 3;

-- The following SQL commands were used to update a value in the Applicant table.
UPDATE applicant SET Password = "strongpassword1" WHERE Applicant_ID = 2;
UPDATE applicant SET Password = "strongpassword2" WHERE Applicant_ID = 3;
UPDATE applicant SET Password = "strongpassword3" WHERE Applicant_ID = 4;
UPDATE applicant SET Password = "strongpassword4" WHERE Applicant_ID = 5;

-- The following SQL command is used to inner join the Applicant and Prior Education table.
SELECT * 
FROM applicant
INNER JOIN prior_education
ON applicant.Applicant_ID = prior_education.Applicant_ID;

-- The following SQL command is used to left join the Applicant and Prior Education table.
SELECT * 
FROM applicant
LEFT JOIN prior_education
ON applicant.Applicant_ID = prior_education.Applicant_ID;

-- The following SQL command is used to right join the Applicant and Prior Education table.
SELECT * 
FROM applicant
RIGHT JOIN prior_education
ON applicant.Applicant_ID = prior_education.Applicant_ID;

