-- 기존 테이블 삭제
DROP TABLE IF EXISTS emotion_analyze CASCADE;
DROP TABLE IF EXISTS picture CASCADE;
DROP TABLE IF EXISTS diary_tag CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS sticker CASCADE;
DROP TABLE IF EXISTS diary CASCADE;
DROP TABLE IF EXISTS shared_diary_comment;
DROP TABLE IF EXISTS shared_diary;
DROP TABLE IF EXISTS shared_diary_room;

-- diary 테이블 생성
CREATE TABLE IF NOT EXISTS diary
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    title         VARCHAR(255) NULL COMMENT '제목 없으면 해당 날짜 DATE 형식으로 작성됨',
    content       TEXT         NULL,
    created_at    DATETIME     NULL,
    is_deleted    BOOLEAN      NULL COMMENT '소프트 딜리트',
    is_confirmed  BOOLEAN      NULL COMMENT '확정 O, 확정 X (04시에 스케줄러로 일괄처리)',
    sticker_layer TEXT         NULL,
    user_id       INTEGER      NOT NULL,
    CONSTRAINT pk_diary_id PRIMARY KEY (id),
    CONSTRAINT fk_diary_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

-- emotion_analyze 테이블 생성
CREATE TABLE IF NOT EXISTS emotion_analyze
(
    id               INTEGER NOT NULL AUTO_INCREMENT,
    total_score      INTEGER NULL,
    analysis_content TEXT    NULL,
    diary_id         INTEGER NOT NULL,
    CONSTRAINT pk_emotion_analyze_id PRIMARY KEY (id),
    CONSTRAINT fk_emotion_analyze_diary_id FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE
);

-- picture 테이블 생성
CREATE TABLE IF NOT EXISTS picture
(
    id         INTEGER NOT NULL AUTO_INCREMENT,
    image_path TEXT    NULL,
    diary_id   INTEGER NOT NULL,
    CONSTRAINT pk_picture_id PRIMARY KEY (id),
    CONSTRAINT fk_picture_diary_id FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE
);

-- tag 테이블 생성
CREATE TABLE IF NOT EXISTS tag
(
    id       VARCHAR(255) NOT NULL,
    tag_name VARCHAR(255) NULL,
    CONSTRAINT pk_tag_id PRIMARY KEY (id)
);

-- diary_tag 테이블 생성
CREATE TABLE IF NOT EXISTS diary_tag
(
    id       INTEGER      NOT NULL AUTO_INCREMENT,
    diary_id INTEGER      NOT NULL,
    tag_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_diary_tag_id PRIMARY KEY (id),
    CONSTRAINT fk_diary_tag_diary_id FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE,
    CONSTRAINT fk_diary_tag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

-- sticker 테이블 생성
CREATE TABLE IF NOT EXISTS sticker
(
    id       INTEGER      NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NULL,
    svg_code TEXT         NULL,
    CONSTRAINT pk_sticker_id PRIMARY KEY (id)
);

-- 공유 일기방 테이블 생성
CREATE TABLE shared_diary_room
(
    id       INT AUTO_INCREMENT PRIMARY KEY COMMENT '공유 일기 방 id',
    user_id1 INT COMMENT '유저 id1',
    user_id2 INT COMMENT '유저 id2'
);

-- 공유 일기 테이블 생성
CREATE TABLE shared_diary
(
    id                   INT AUTO_INCREMENT PRIMARY KEY COMMENT '공유 일기 id',
    content              TEXT COMMENT '일기 내용',
    created_at           DATETIME COMMENT '작성 시간',
    title                VARCHAR(255) COMMENT '일기 제목',
    is_deleted           BOOLEAN DEFAULT FALSE COMMENT '삭제 여부',
    fixed_state          BOOLEAN DEFAULT FALSE COMMENT '작성 상태',
    shared_diary_room_id INT COMMENT '공유 일기 방 id',
    user_id              INT COMMENT '작성자 id',
    FOREIGN KEY (shared_diary_room_id) REFERENCES shared_diary_room (id)
);

-- 공유 일기 댓글 테이블 생성
CREATE TABLE shared_diary_comment
(
    id              INT AUTO_INCREMENT PRIMARY KEY COMMENT '공유 일기 댓글 id',
    comment_content VARCHAR(255) COMMENT '댓글 내용',
    shared_diary_id INT COMMENT '공유 일기 id',
    user_id         INT COMMENT '작성자 id',
    FOREIGN KEY (shared_diary_id) REFERENCES shared_diary (id)
);


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

-- 행동 관련 테이블 생성 쿼리

DROP TABLE IF EXISTS USER_PREFERENCES CASCADE;

-- USER 테이블 삭제 필요
-- 임시 테이블 삭제 쿼리
DROP TABLE IF EXISTS USER CASCADE;

DROP TABLE IF EXISTS TAGGED_RECOMMENDED_ACTIONS CASCADE;
DROP TABLE IF EXISTS ACTION_TAG CASCADE;
DROP TABLE IF EXISTS RECOMMENDED_ACTIONS CASCADE;

CREATE TABLE IF NOT EXISTS RECOMMENDED_ACTIONS
(
    id          INT          NOT NULL AUTO_INCREMENT COMMENT '추천 행동 ID'
    ,   action      VARCHAR(255) NOT NULL COMMENT '추천 행동'
    ,   CONSTRAINT pk_id PRIMARY KEY (id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT ='추천 행동'
  DEFAULT CHARSET UTF8;

CREATE TABLE IF NOT EXISTS ACTION_TAG
(
    id                      INT          NOT NULL AUTO_INCREMENT COMMENT '행동 태그 ID'
    ,   name                    VARCHAR(255) NOT NULL COMMENT '행동 태그 이름'
    ,   parent_action_tag_id    INT              NULL COMMENT '상위(부모) 행동 태그 ID'
    ,   CONSTRAINT pk_id PRIMARY KEY (id)
    ,   CONSTRAINT fk_parent_action_tag_id
        FOREIGN KEY (parent_action_tag_id)
            REFERENCES ACTION_TAG (id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT ='행동 태그'
  DEFAULT CHARSET UTF8;

CREATE TABLE IF NOT EXISTS TAGGED_RECOMMENDED_ACTIONS
(
    recommended_actions_id  INT NOT NULL COMMENT '추천 행동 ID'
    ,   action_tag_id           INT NOT NULL COMMENT '행동 태그 ID'
    ,   CONSTRAINT fk_tagged_recommended_actions_recommended_actions_id
        FOREIGN KEY (recommended_actions_id)
            REFERENCES RECOMMENDED_ACTIONS(id)
    ,   CONSTRAINT fk_tagged_recommended_actions_action_tag_id
        FOREIGN KEY (action_tag_id)
            REFERENCES ACTION_TAG(id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT ='태그화된 추천 행동'
  DEFAULT CHARSET UTF8;

-- USER 테이블 생성 필요(fk 제약 충족을 위한 임시 테이블)
CREATE TABLE IF NOT EXISTS USER
(
    id  INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS USER_PREFERENCES
(
    user_id             INT         NOT NULL COMMENT '유저 ID'
    ,   action_tag_id       INT         NOT NULL COMMENT '행동 태그 ID'
    ,   weight              INT         NOT NULL DEFAULT 50 COMMENT '가중치'
    ,   last_recommended_at DATETIME    NOT NULL DEFAULT (CURRENT_TIME) COMMENT '마지막 추천 시간'
    ,   CONSTRAINT fk_user_preferences_user_id
        FOREIGN KEY (user_id)
            REFERENCES USER(id)
    ,   CONSTRAINT fk_user_preferences_action_tag_id
        FOREIGN KEY (action_tag_id)
            REFERENCES ACTION_TAG(id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT ='사용자 선호도'
  DEFAULT CHARSET UTF8;