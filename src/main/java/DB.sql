DROP DATABASE IF EXISTS `AM_jsp_2026_02`;
CREATE DATABASE `AM_jsp_2026_02`;
USE `AM_jsp_2026_02`;

CREATE TABLE `article` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` CHAR(100),
    `body` CHAR(100)
);

INSERT INTO `article`
SET `title` = '제목1',
    `body` = '내용1';
INSERT INTO `article`
SET `title` = '제목2',
    `body` = '내용2';
INSERT INTO `article`
SET `title` = '제목3',
    `body` = '내용3';    

SELECT * FROM `article`;


