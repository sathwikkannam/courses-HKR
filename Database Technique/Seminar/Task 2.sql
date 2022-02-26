--Write a SHOW statement to display all databases.
--DO THESE IN THE WINDOWS COMMAND TERMINAL!!!!!!!!!!!!!!!.
SHOW DATABASES;

--Select optional database (with USE command, e.g. USE  db) and then display all tables for that database.
USE world;
SHOW TABLES;

--Select mysql-database and check which user accounts you have in the table user.
SELECT user FROM mysql.user;

--Use mysqldump to get a backup of your project-database. Check if the file has been created.
mysqldump -u -p world > filename.sql;

--In this part, you need to recover your database from the backup that you have just made.
mysqldump -u -p < filename.sql;