--liquibase formatted sql

--changeset celio:main-001
create schema if not exists main;

--rollback drop schema main;

--changeset celio:main-002

create table if not exists main.grupo
(
  gru_id bigserial primary key,
  gru_nome character varying(100) not null
);

--rollback drop table  main.grupo;

--changeset celio:main-003

create table if not exists  main.pessoa
(
  pes_id bigserial primary key,  
  pes_tipo smallint not null,
  pes_sexo character varying(1) not null,
  pes_nome character varying(100) not null,
  pes_apelido character varying(100) not null,
  pes_nome_pesquisa character varying(100) not null,
  pes_cpf character varying(14),
  pes_naturalidade character varying(14),
  pes_nacionalidade character varying(14),
  pes_estado_civil smallint,
  pes_escolaridade smallint,
  pes_profissao character varying(14),
  pes_nascimento date,
  pes_cadastro timestamp without time zone not null default now(),
  pes_email character varying(14),
  pes_celular character varying(14),
  pes_cep character varying(9),
  pes_uf character varying(2),
  pes_municipio character varying(45),
  pes_bairro character varying(45),
  pes_logradouro character varying(45),
  pes_numero character varying(15),  
  pes_grupo character varying(14),
  pes_cargo character varying(14)
);

--rollback drop table main.pessoa;

--changeset celio:main-004

create table if not exists main.pessoa_grupo
(
  pgr_id bigserial primary key,  
  pgr_cargo character varying(60),
  pgr_pes_id bigint not null,
  pgr_gru_id bigint not null,
  foreign key (pgr_pes_id) references main.pessoa (pes_id),
  foreign key (pgr_gru_id) references main.grupo (gru_id)
);

--rollback drop table main.pessoa_grupo;

--changeset celio:main-005

create table if not exists main.pessoa_contato
(
  pco_id bigserial primary key,  
  pco_tipo smallint not null,
  pco_descricao character varying(45) not null,  
  pco_pes_id bigint not null,  
  foreign key (pco_pes_id) references main.pessoa (pes_id)
);

--rollback drop table main.pessoa_contato;

--changeset celio:main-006

create table if not exists main.pessoa_endereco
(
  pen_id bigserial primary key,  
  pen_cep character varying(9),
  pen_uf character varying(2) not null,
  pen_municipio character varying(45),
  pen_bairro character varying(45),
  pen_logradouro character varying(45),
  pen_numero character varying(15),  
  pen_pes_id bigint not null,  
  foreign key (pen_pes_id) references main.pessoa (pes_id)
);

--rollback drop table main.pessoa_endereco;

--changeset celio:main-007

create index if not exists grupo_gru_nome_idx on main.grupo (gru_nome);

--changeset celio:main-008

create index if not exists pessoa_pes_nome_idx on main.pessoa (pes_nome_pesquisa);
