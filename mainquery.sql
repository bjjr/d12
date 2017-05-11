drop database if exists `Acme-CinemaDB`;
create database `Acme-CinemaDB`;
grant select, insert, update, delete
on `Acme-CinemaDB`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter,
create temporary tables, lock tables, create view, create routine,
alter routine, execute, trigger, show view
on `Acme-CinemaDB`.* to 'acme-manager'@'%';
