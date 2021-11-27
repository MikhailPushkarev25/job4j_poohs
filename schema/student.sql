create table students (
   id serial primary key,
   name varchar(255),
   age int,
   city varchar(255),
   account_id int unique references accounts(id)
);

create table accounts (
    id serial primary key,
    username varchar(255),
    active boolean,
    book_id int references books(id)
);

create table books (
   id serial primary key,
   name varchar(255),
   publishingHouse varchar(255)
);