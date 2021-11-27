create table candidate (
    id serial primary key,
    name varchar(255),
    experience varchar(255),
    salary int,
     base_id int unique references baseVacancies(id)
);

create table vacancy (
    id serial primary key,
    name varchar(255)
);

create table baseVacancies (
    id serial primary key,
    name varchar(255),
    vacancy_id int references vacancy(id)
);