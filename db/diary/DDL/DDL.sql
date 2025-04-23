use moodiarydb;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user` CASCADE;
DROP TABLE IF EXISTS `register_questions` CASCADE;
DROP TABLE IF EXISTS `user_pet` CASCADE;
DROP TABLE IF EXISTS `pet` CASCADE;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `register_questions` (
    `id`                            INT NOT NULL AUTO_INCREMENT,
    `register_questions_content`    VARCHAR(255) NOT NULL,
    
    CONSTRAINT `PK_register_questions`
        PRIMARY KEY (`id`),
    UNIQUE KEY `UK_register_questions_content` (`register_questions_content`)
);

CREATE TABLE `user` (
    `id`                            INT NOT NULL AUTO_INCREMENT,
    `name`                          VARCHAR(30) NOT NULL,
    `phone_number`                  VARCHAR(15) NOT NULL UNIQUE,
    `email`                         VARCHAR(255) NOT NULL UNIQUE,
    `password`                      VARCHAR(255) NOT NULL,
    `is_deleted`                    BOOLEAN NOT NULL DEFAULT FALSE,
    `answer`                        VARCHAR(255) NOT NULL,
    `register_questions_id`         INT NOT NULL,
    
    CONSTRAINT `PK_user`
        PRIMARY KEY (`id`),
    
    CONSTRAINT `FK_user_to_register_questions`
        FOREIGN KEY (`register_questions_id`)
        REFERENCES `register_questions` (`id`)
);

CREATE TABLE `pet` (
    `id`                INT NOT NULL AUTO_INCREMENT,
    `pet_name`          varchar(30) NOT NULL,
    `pet_img`           TEXT,
    CONSTRAINT `PK_pet` 
        PRIMARY KEY (`id`)
);

CREATE TABLE `user_pet` (
    `user_id`           INT NOT NULL,
    `pet_id`            INT NOT NULL,

    CONSTRAINT `PK_user_pet`
        PRIMARY KEY (`user_id`, `pet_id`),

    CONSTRAINT `FK_user_pet_to_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `user` (`id`),

    CONSTRAINT `FK_user_pet_to_pet`
        FOREIGN KEY (`pet_id`)
            REFERENCES `pet` (`id`)
);