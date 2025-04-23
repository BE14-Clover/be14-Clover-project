INSERT 
INTO user (
  id
, name
, phone_number
, email
, password
, is_deleted
, answer
, register_questions_id
) 
VALUES
(-1, '관리자', '010-0000-0000', 'ADMIN@moodiary.com', 'admin123', FALSE, '-', 1),
(1, '강수지', '010-1234-5678', 'rkd50071@gmail.com', 'pass1', FALSE, '부산', 3),
(2, '고성연', '010-2345-6789', 'rhtjddus0502@gmail.com', 'pass2', FALSE, '초코', 4),
(3, '고윤석', '010-3456-7890', 'jakego13129@gmail.com', 'pass3', FALSE, '개발자', 10),
(4, '김성민', '010-4567-8901', 'sungmin806@gmail.com', 'pass4', FALSE, '주토피아', 9),
(5, '이청민', '010-5678-9012', 'charlie4822@gmail.com', 'pass5', FALSE, '개발자', 1);