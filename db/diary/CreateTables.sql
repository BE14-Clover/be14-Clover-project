-- 기존 테이블 삭제
DROP TABLE IF EXISTS emotion_analyze CASCADE;
DROP TABLE IF EXISTS picture CASCADE;
DROP TABLE IF EXISTS diary_tag CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS sticker CASCADE;

-- emotion_analyze 테이블 생성
CREATE TABLE IF NOT EXISTS emotion_analyze
(
    id                INTEGER  NOT NULL AUTO_INCREMENT,
    total_score       INTEGER  NULL,
    analysis_content  TEXT     NULL,
    diary_id          INTEGER  NOT NULL,
    CONSTRAINT pk_emotion_analyze_id PRIMARY KEY (id),
    CONSTRAINT fk_emotion_analyze_diary_id FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE
);

-- picture 테이블 생성
CREATE TABLE IF NOT EXISTS picture
(
    id          INTEGER      NOT NULL AUTO_INCREMENT,
    image_path  TEXT         NULL,
    diary_id    INTEGER      NOT NULL,
    CONSTRAINT pk_picture_id PRIMARY KEY (id),
    CONSTRAINT fk_picture_diary_id FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE
);

-- diary_tag 테이블 생성
CREATE TABLE IF NOT EXISTS diary_tag
(
    id         INTEGER  NOT NULL AUTO_INCREMENT,
    diary_id   INTEGER  NOT NULL,
    tag_id     VARCHAR(255)  NOT NULL,
    CONSTRAINT pk_diary_tag_id PRIMARY KEY (id),
    CONSTRAINT fk_diary_tag_diary_id FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE,
    CONSTRAINT fk_diary_tag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

-- tag 테이블 생성
CREATE TABLE IF NOT EXISTS tag
(
    id          VARCHAR(255)  NOT NULL,
    tag_name    VARCHAR(255)  NULL,
    CONSTRAINT pk_tag_id PRIMARY KEY (id)
);

-- sticker 테이블 생성
CREATE TABLE IF NOT EXISTS sticker
(
    id          INTEGER  NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)  NULL,
    svg_code    TEXT     NULL,
    CONSTRAINT pk_sticker_id PRIMARY KEY (id)
);