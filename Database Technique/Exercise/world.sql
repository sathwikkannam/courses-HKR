USE world;

-- Shows every coloums in the table country but has a limit of 3 rows. 
SELECT * FROM country LIMIT 3;

-- Only selects code and name colum from country but only 3 rows.
SELECT code, name FROM country LIMIT 3;


-- AS is used to create alisas
SELECT
    code AS 'Country code',
    name AS 'Name'
FROM country
LIMIT 3;

SELECT Code, Name, SurfaceArea, IndepYear, LifeExpectancy FROM country LIMIT 7;


SELECT code, name, population
FROM country
WHERE name LIKE '%land';

--  Code | Name   | Region     | Continent | Population | LifeExpect

SELECT Code, Name, Region, Continent, Population, LifeExpectancy From country
WHERE Population < 50000 AND Population > 10000 AND LifeExpectancy < 70
LIMIT 7;


SELECT Code, Name, Region, Continent, Population, LifeExpectancy From country
WHERE Code = "SWE" OR  Code = "FIN" OR Name = "Denmark" OR Name = "Norway" OR Name LIKE "green%"; 
-- THE LIKE command is used to check substring.

SELECT code, name, region, population, lifeExpectancy
FROM country
WHERE region LIKE 'Nordic%'
ORDER BY lifeExpectancy DESC;

SELECT code, name, region, continent, population, lifeExpectancy
FROM country
WHERE continent = 'Europe'
ORDER BY population ASC
LIMIT 5;

SELECT code, name, region, continent, population, lifeExpectancy
FROM country
WHERE continent = "Oceania" AND lifeExpectancy != NULL AND region = "Melanesia" OR region = "Polynesia";
-- ---


SELECT COUNT(code) AS 'Number of countries' FROM country;

SELECT COUNT(ID) AS 'Number of cities' FROM city;
SELECT COUNT(DISTINCT language) AS 'Number of languages' FROM countrylanguage;

SELECT AVG(lifeExpectancy) AS "Average life expectancy" FROM country;

SELECT SUM(population) AS "Total population" FROM country;

SELECT
     continent,
     AVG(lifeExpectancy) AS 'Average life expectancy'
FROM country
GROUP BY continent
ORDER BY AVG(lifeExpectancy) DESC;

SELECT
     continent,
     COUNT(name) AS 'Number of countries'
FROM country
GROUP BY continent
ORDER BY COUNT(name) DESC;

SELECT
     region,
     COUNT(name) AS 'Number of countries',
     AVG(lifeExpectancy) AS "Average life expectancy"
FROM country
GROUP BY region, "Number of countries", "Average life expectancy"
ORDER BY AVG(lifeExpectancy) DESC;


SELECT
     continent,
     COUNT(code) AS 'Number of countries'
FROM country
GROUP BY continent
HAVING COUNT(code) >= 10
ORDER BY COUNT(code) DESC;

SELECT
     region,
     COUNT(name) AS 'Number of countries',
     AVG(lifeExpectancy) AS "Average life expectancy"
FROM country
GROUP BY region, "Number of countries", "Average life expectancy"
HAVING COUNT(name) >= 10
ORDER BY AVG(lifeExpectancy) DESC;






