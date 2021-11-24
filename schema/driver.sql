create table car (
    id serial primary key,
    modeles varchar(2000),
    engine_id int unique references engine(id)
);

create table engine (
    id serial primary key,
    types varchar(2000)
);

create table driver (
    id serial primary key,
    name varchar(2000)
);

create table history_owner (
    id serial primary key,
    driver_id int references driver(id),
    car_id int references car(id)
);