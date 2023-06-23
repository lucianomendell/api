/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/

create table API.USUARIO (
   ID_USUARIO BIGINT               default 0  not null,
   NOME VARCHAR2(200) not null,
   LOGIN VARCHAR2(200) not null,
   SENHA VARCHAR2(200) not null,
   EMAIL   VARCHAR2(100),
   PERMISSOES  VARCHAR2(2000)
);

create sequence API.SQ_ID_USUARIO minvalue 1 start with 1 increment by 1 nocache;

alter table API.USUARIO
   add constraint PK_USUARIO primary key (ID_USUARIO);
     INSERT INTO API.USUARIO (ID_USUARIO, NOME, LOGIN, SENHA, EMAIL, PERMISSOES) VALUES (API.SQ_ID_USUARIO.nextval,'Administrador','root','$2a$10$R1/TADpxR/YC9fykMRmoluW72Z75YPzIQ7FdYBNaP1ruMj8ioR1Ve','admin@gmail.com.br','LEITURA_USUARIO,ESCRITA_USUARIO');
     INSERT INTO API.USUARIO (ID_USUARIO, NOME, LOGIN, SENHA, EMAIL, PERMISSOES) VALUES (API.SQ_ID_USUARIO.nextval,'Administrador','root2','$2a$10$R1/TADpxR/YC9fykMRmoluW72Z75YPzIQ7FdYBNaP1ruMj8ioR1Ve','admin2@gmail.com.br','LEITURA_USUARIO,ESCRITA_USUARIO');



