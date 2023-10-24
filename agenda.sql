create database agenda;
show databases;

use agenda;

create table contatos(
	id	int	primary key	auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50)
);

show tables;
describe contatos;

/* CRUD CREATE */
insert into contatos(nome, fone, email) 
values ('Darth Vader', '99999-1111', 'vader.darkside@gmail.com');

/* CRUD READ */
select * from contatos;

select * from contatos
order by nome;

select * from contatos
where id = 7;

/* CRUD UPDATE */
update contatos 
set nome = "Julia Roberts" 
where id = 7;

update contatos
set nome = "Julia", 
fone = "12 99685-7421", 
email = "julia.r@gmail.com"
where id = 7;

/* CRUD DELETE */
delete from contatos
where id = 7;