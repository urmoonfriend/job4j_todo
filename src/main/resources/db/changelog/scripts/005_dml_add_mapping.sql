ALTER TABLE tasks
    ADD COLUMN user_id int references todo_user (id);
