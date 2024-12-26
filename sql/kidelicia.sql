-- Roda a linha 2 e 3 antes do resto
create database kadora;
--
use kadora;

create table cadastro (
	idCadastro int primary key auto_increment,
	email varchar(45) unique, 
    senha varchar(45), 
    confSenha varchar(45),
    telefone varchar(45), 
    cpf varchar(45) unique
);

select * from cadastro;