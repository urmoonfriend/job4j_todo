ALTER TABLE todo_user
ADD COLUMN user_zone VARCHAR(255);

UPDATE todo_user
SET user_zone = 'Asia/Almaty';