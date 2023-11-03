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

DO
$$
DECLARE
source_record RECORD;
BEGIN
FOR source_record IN (SELECT id FROM tasks)
LOOP
        INSERT INTO tasks_categories (task_id, category_id)
        VALUES (source_record.id, 1);
END LOOP;
END $$;
