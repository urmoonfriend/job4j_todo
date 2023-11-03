CREATE TABLE categories
(
    id       SERIAL PRIMARY KEY,
    name     TEXT UNIQUE NOT NULL
);

CREATE TABLE tasks_categories
(
    id serial primary key,
    task_id int references tasks(id),
    category_id int references categories(id),
    UNIQUE (task_id, category_id)
)