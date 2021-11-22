
create table modeles (
    id serial primary key,
    name varchar(2000)
);

create table mark (
    id serial primary key,
    name varchar(2000),
    model_id int references modeles(id)
);