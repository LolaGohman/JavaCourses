create table if not exists users
(
  id       bigserial primary key,
  username varchar(255) not null,
  password varchar(128) not null,
  isAdmin  boolean);