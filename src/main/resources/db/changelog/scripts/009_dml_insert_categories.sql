INSERT INTO categories (name)
VALUES ('home');
INSERT INTO categories (name)
VALUES ('work');
INSERT INTO categories (name)
VALUES ('university');
INSERT INTO categories (name)
VALUES ('medicine');
INSERT INTO categories (name)
VALUES ('cooking');
INSERT INTO categories (name)
VALUES ('entertainment');

INSERT INTO tasks_categories (task_id, category_id)
SELECT id, 1 FROM tasks;
