# Simple "TO DO List" Project

## Описание проекта:

#### - Страница со списком всех заданий. В таблице отображаем имя, дату создания и состояние (выполнено или нет)

#### - На странице со списком есть кнопка "Добавить задание".

#### - На странице со списком есть кнопка "Добавить задание".

#### - На странице со списком есть три ссылки: "Все", "Выполненные", "Новые".При переходе по ссылкам в таблице отображается: все задания, только выполненные или только новые.

## Стек технологий

#### - Java 17, Spring boot, Thymeleaf, Bootstrap, Hibernate, PostgreSql

## Требования к окружению

#### - Java 17, Maven 4.0, PostgreSQL 15

## Запуск проекта

#### - Создание бд в СУБД

```sql
create database todo;
```

#### - Создание таблиц в бд из файла /scripts/001_ddl_create_initial_schema.sql

```sql
CREATE TABLE tasks (
                      id SERIAL PRIMARY KEY,
                      description TEXT,
                      created TIMESTAMP,
                      done BOOLEAN
);
```

## Взаимодействие с приложением
1) Список всех заданий
![img.png](images/img.png)
2) Фильтр выполненных заданий
![img_1.png](images/img_1.png)
3) Фильтр новых заданий
![img_2.png](images/img_2.png)
4) Создание задания
![img_3.png](images/img_3.png)
5) Страница с заданием
![img_4.png](images/img_4.png)
6) Редактирование задания 
![img_5.png](images/img_5.png)
7) Выполнение задания
![img_6.png](images/img_6.png)
8) Удаление задания
![img_7.png](images/img_7.png)


## Контакты
#### - @urmoonfriend
