--Create a new user, using ‘CREATE USER’ with username as your first name, and the password P@ssw0rd. 
--(Use MYSQL Shell or Command Line)

CREATE USER 'Username'@'localhost' IDENTIFED BY '123';

--  CodeWrite a GRANT statement inside the MYSQL Shell to give the user access to the following: 
--Select, update, insert, delete and execute from all databases and tables
GRANT SELECT ON * . * TO 'Username'@'localhost';
GRANT UPDATE ON * . * TO 'Username'@'localhost';
GRANT INSERT ON * . * TO 'Username'@'localhost';
GRANT DELETE ON * . * TO 'Username'@'localhost';
GRANT EXECUTE ON * . * TO 'Username'@'localhost';

--Write a REVOKE statement to remove the privileges delete from the user 
--account you created above. 
REVOKE delete ON * . * FROM 'Username'@'localhost';


--Open MySQL Workbench and create a user named Adam, with optional 
--password and optional host (% or localhost) via MySQL Workbench and 
--grant the user with the role: BackupAdmin.

CREATE ROLE BackupAdmin;
CREATE USER 'Adam'@'localhost' IDENTITFED '1234';
GRANT BackupAdmin ON * . * TO 'Adam'@'localhost';

--Remove the entire user Adam via MySQL Workbench and check if the user has been removed.
DROP USER 'Adam'@'localhost';