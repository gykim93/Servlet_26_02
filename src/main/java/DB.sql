# 데이터베이스 생성
DROP DATABASE IF EXISTS `AM_jsp_2026_02`;
CREATE DATABASE `AM_jsp_2026_02`;
USE `AM_jsp_2026_02`;


# 게시글 테이블 생성
CREATE TABLE `article` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `regDate` DATETIME NOT NULL,
    `updateDate` DATETIME NOT NULL,
    `title` CHAR(100) NOT NULL,
    `body` CHAR(100) NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `member`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`regDate` DATETIME NOT NULL,
	`updateDate` DATETIME NOT NULL,
	`loginId` CHAR(100) NOT NULL,
	`loginPw` CHAR(100) NOT NULL,
	`name` CHAR(100) NOT NULL
);

# 게시글 데이터 삽입
INSERT INTO `article`
SET `regDate` = NOW(),
	`updateDate` = NOW(),
	`title` = '제목1',
	`body` = '내용1';
	
INSERT INTO `article`
SET `regDate` = NOW(),
	`updateDate` = NOW(),
	`title` = '제목2',
	`body` = '내용2';

INSERT INTO `article`
SET `regDate` = NOW(),
	`updateDate` = NOW(),
	`title` = '제목2',
	`body` = '내용2';
	
SELECT * FROM `article`;

# 회원 데이터 삽입
INSERT INTO `member`
SET `regDate` = NOW(),
	`updateDate` = NOW(),
	`loginId` = 'test1',
	`loginPw` = 'test1',
	`name` = '홍길동';
	
INSERT INTO `member`
SET `regDate` = NOW(),
	`updateDate` = NOW(),
	`loginId` = 'test1',
	`loginPw` = 'test1',
	`name` = '홍길순';	
	
SELECT * FROM `member`;