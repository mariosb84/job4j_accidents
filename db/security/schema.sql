--CREATE TABLE users (
  --username VARCHAR(50) NOT NULL,
  --password VARCHAR(100) NOT NULL,
 -- enabled boolean default true,
  --PRIMARY KEY (username)
--);

--CREATE TABLE authorities (
 -- username VARCHAR(50) NOT NULL,
 -- authority VARCHAR(50) NOT NULL,
 -- FOREIGN KEY (username) REFERENCES users(username)
--);

CREATE TABLE authorities (
  authorities_id serial primary key,
  authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
  user_id serial primary key,
  user_name VARCHAR(50) NOT NULL unique,
  user_password VARCHAR(100) NOT NULL,
  enabled boolean default true,
  authority_id int not null references authorities(authorities_id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (user_name, user_password, enabled, authority_id)
values ('root', '$2a$10$YBJHZ27EMitJklPIYRqKsOdXxa4ySYGDPGvBk/nBXy6uGFvq2OOSO', true,
(select authorities_id from authorities where authority = 'ROLE_ADMIN'));