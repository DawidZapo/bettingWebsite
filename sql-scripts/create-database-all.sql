CREATE DATABASE IF NOT EXISTS betting;

USE `betting`;

SET foreign_key_checks = 0;
SET foreign_key_checks = 1;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `enabled` tinyint NOT NULL,  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` (`username`,`password`,`enabled`)
VALUES 
('admin','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1);


CREATE TABLE `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');


CREATE TABLE `users_roles` (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(1,2),
(1,3);

CREATE TABLE `user_details` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  points double,
  
  CONSTRAINT `FK_USER_DETAILS` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




USE betting;

CREATE TABLE player (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    seeded INT,
    atp TINYINT(1)
);

CREATE TABLE match_to_bet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    match_date DATE,
    match_time TIME,
    court_number VARCHAR(50),
    match_duration DOUBLE,
    atp TINYINT(1),
    round VARCHAR(50),
    player1_id BIGINT,
    player2_id BIGINT,
    player1_odds DOUBLE,
    player2_odds DOUBLE,
    score VARCHAR(30),
    winner VARCHAR(255),
    
    foreign key (player1_id) references player(id),
    foreign key (player2_id) references player(id)
);
USE betting;
CREATE TABLE bet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    match_to_bet_id BIGINT,
    amount DOUBLE,
    bet_on VARCHAR(255),
    expected_win DOUBLE,
    succeed TINYINT(1),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (match_to_bet_id) REFERENCES match_to_bet(id)
);