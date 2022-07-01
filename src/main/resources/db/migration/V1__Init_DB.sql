CREATE TABLE publishes (
    id int8 not null,
    description varchar(255),
    priority int4 not null,
    text varchar(255),
    publish_at int8,
    title varchar(255),
    primary key (id)
);