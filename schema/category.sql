
create table tasks (
    id serial primary key,
    description varchar(2000),
    category_id int references categories(id)
);

create table categories (
    id serial primary key,
    name varchar(2000)
);


