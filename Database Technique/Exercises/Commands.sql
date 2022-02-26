USE world;


-- Data Definational Language(DDL) used to create and define objects in a database and its attributes and datatypes.

-- Learning basic SQL syntax

-- Create is used to create a data type
CREATE TABLE nameoftable (coloumname INT);

-- ALTER is used to change or modifiy exsisting TABLES or data types;
ALTER TABLE nameoftable
ADD COLUMN coloum_name INT;

-- Rename objects
RENAME TABLE nameoftable to newtablename;

-- DELETE datatypes
DROP TABLE nameoftable;

-- TRUNCATE is used to delete all the information within the coloums in the table but maintain the database and attributes of each coloum and row.
TRUNCATE TABLE newtablename;

-- ----------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------

-- Data Query Language (DQL) used to manipulate and organize within database. 
-- SELECT FROM TABLE WHERE GROUP BY HAVING ORDER BY ASEC. 

-- Shows all rows in the databases
SHOW TABLES;

-- Selects a coloum in the specified table;
SELECT name FROM country;

-- Can select multiple coloums
SELECT Name, Region FROM country;


-- INSERT command is used to insert values into coloums
	-- INSERT INTO <TABLENAME> (COLUM1, COLUM2) VALUES (COLUM1_VALUE, COLUM2_VALUE);

-- UPDATE command is used to renew pre-exisiting data in table;
	-- UPDATE <TABLENAME>
	-- SET <COLUMNAME> = "SOMETHING";
    -- WHERE <COLUMNAME2> = "SOMETHIG ELSE";
    
-- DELETE is used specify what exactly to remove. 
	-- DELETE FROM TABLE
	-- WHERE
		-- CONDITION

-- WHERE command is a condition
	-- SELECT name 
		-- FROM country;
        
-- ORDER BY is used to ORDER either ASC or DESC
	-- ORDER BY COLUMNAME ASC or DESC

-- GROUP BY

