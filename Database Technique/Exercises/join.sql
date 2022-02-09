USE world;

SHOW TABLES;

SELECT * FROM country;

SELECT * FROM countrylanguage;

SELECT * 
FROM country 
INNER JOIN countrylanguage
ON country.code = countrylanguage.CountryCode;
