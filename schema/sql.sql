create table book (
    id serial primary key,
    name varchar(2000)
);

create table author (
    id serial primary key,
    name varchar(2000),
    book_id int references book(id)
);