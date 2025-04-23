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
