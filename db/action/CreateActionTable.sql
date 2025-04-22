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