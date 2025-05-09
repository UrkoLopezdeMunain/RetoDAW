drop view vis_equ_info;
drop view vis_equ_jug;
drop table jugadores;
drop sequence sequencia_jugadores;
drop table enfrentamientos;
drop sequence sequencia_enfrentamientos;
drop table jornadas;
drop sequence sequencia_jornadas;
drop table competiciones;
drop sequence sequencia_competiciones;
drop table equipos;
drop sequence sequencia_equipos;
drop table usuarios;
drop sequence sequencia_usuarios;
drop table juegos;
drop sequence sequencia_juegos;

create sequence sequencia_juegos
  start with 1
  increment by 1;
  
create table juegos(
    cod_juego number(2),
    fecha_salida date not null,
    nombre varchar2(20) not null,
    constraint jue_nom_ck check(nombre = lower(nombre)),
    constraint jue_cod_pk primary key(cod_juego)
);

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_juegos
before insert on juegos
for each row
begin
  :new.cod_juego := sequencia_juegos.nextval;
end tr_juegos_sequencia;
/

insert into juegos (cod_juego,fecha_salida,nombre)
values(default,to_date('02-06-2020','DD-MM-YYYY'),'valorant');

create sequence sequencia_usuarios
  start with 1
  increment by 1;


create table usuarios(
    cod_usuario number(2),
    nombre varchar2(20) not null unique,
    contraseña varchar2(20),
    tipo_usuario varchar(1),
    constraint usu_nom_ck check(nombre = lower(nombre)),
    constraint usu_cod_pk primary key(cod_usuario),
    constraint usu_tip_ck check(tipo_usuario in('n','a'))
);

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_usuarios
before insert on usuarios
for each row
begin
  :new.cod_usuario := sequencia_usuarios.nextval;
end tr_sequencia_usuarios;
/

insert into usuarios (cod_usuario,nombre,contraseña,tipo_usuario)
values(default,'admin','Jm12345','a');
insert into usuarios (cod_usuario,nombre,contraseña,tipo_usuario)
values(default,'nieves','Jm12345','a');
insert into usuarios (cod_usuario,nombre,contraseña,tipo_usuario)
values(default,'blanca','Jm12345','a');
insert into usuarios (cod_usuario,nombre,contraseña,tipo_usuario)
values(default,'eider','Jm12345','a');
insert into usuarios (cod_usuario,nombre,contraseña,tipo_usuario)
values(default,'invitado','Jm12345','n');

create sequence sequencia_equipos
  start with 1
  increment by 1;

create table equipos(
    cod_equipo number(2),
    nombre varchar2(15) not null unique,
    fecha_fundacion date,
    puntuacion number(2) default 0,
    constraint equ_nom_ck check(nombre = lower(nombre)),
    constraint equ_cod_pk primary key(cod_equipo)
);

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_equipos
before insert on equipos
for each row
begin
  :new.cod_equipo := sequencia_equipos.nextval;
end tr_sequencia_equipos;
/

create sequence sequencia_competiciones
  start with 1
  increment by 1;

create table competiciones(
    cod_comp number(2),
    estado varchar(1) not null,
    cod_juego number (2),
    constraint com_cod_pk primary key(cod_comp),
    constraint com_jue_fk foreign key (cod_juego) 
        references juegos (cod_juego),
    constraint com_est_ck check (estado in ('A','C'))
);

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_competiciones
before insert on competiciones
for each row
begin
  :new.cod_comp := sequencia_competiciones.nextval;
end tr_sequencia_competiciones;
/

insert into competiciones (cod_comp,estado,cod_juego)
values(default,'A',1);

create sequence sequencia_jornadas
  start with 1
  increment by 1;

create table jornadas(
    num_jornada number(2),
    fecha_inicio date not null,
    cod_comp number (2),
    constraint jor_num_pk primary key(num_jornada),
    constraint jor_com_fk foreign key (cod_comp) 
        references competiciones (cod_comp)
);

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_jornadas
before insert on jornadas
for each row
begin
  :new.num_jornada := sequencia_jornadas.nextval;
end tr_sequencia_jornadas;
/

create sequence sequencia_enfrentamientos
  start with 1
  increment by 1;
  
create table enfrentamientos(
    id_enfrentamiento number(2),
    hora timestamp not null,
    resultados_eq_1 number(2),
    resultados_eq_2 number(2),
    cod_equ_1 number(2),
    cod_equ_2 number(2),
    num_jornada number(2),
    constraint enf_id_pk primary key(id_enfrentamiento),
    constraint enf_eq1_fk foreign key (cod_equ_1) 
        references equipos (cod_equipo),
    constraint enf_eq2_fk foreign key (cod_equ_2) 
        references equipos (cod_equipo),
    constraint enf_jor_fk foreign key (num_jornada) 
        references jornadas (num_jornada)
);

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_enfrentamientos
before insert on enfrentamientos
for each row
begin
  :new.id_enfrentamiento := sequencia_enfrentamientos.nextval;
end tr_sequencia_enfrentamientos;
/

create sequence sequencia_jugadores
  start with 1
  increment by 1;

create table jugadores(
    cod_jugador number(2),
    nombre varchar2(20) not null,
    apellido varchar2(20) not null,
    nacionalidad varchar2(3) not null,
    fecha_nac date not null,
    sueldo number(7,2) not null,
    nickname varchar2(20) not null unique,
    rol varchar2(20) not null,
    cod_equipo number(2),
    constraint jug_nom_ck check(nombre = lower(nombre)),
    constraint jug_ape_ck check(apellido = lower(apellido)),
    constraint jug_rol_nom_ck check(rol = lower(rol)),
    constraint jug_cod_pk primary key(cod_jugador),
    constraint jug_equ_fk foreign key (cod_equipo) 
        references equipos (cod_equipo),
    constraint jug_rol_ck check (rol in ('centinela','controlador',
                                    'iniciador','duelista'))
    );

--Trigger para añadir sequencia
create or replace trigger tr_sequencia_jugadores
before insert on jugadores
for each row
begin
  :new.cod_jugador := sequencia_jugadores.nextval;
end tr_sequencia_jugadores;
/

create or replace view vis_equ_jug as
    select e.cod_equipo, count(j.cod_jugador) as cantidad_jugadores
        from equipos e
        left join jugadores j
        on e.cod_equipo = j.cod_equipo
        group by e.cod_equipo
with read only;
   
create or replace view vis_equ_info as
    select e.nombre, e.fecha_fundacion, count(j.cod_jugador) as cantidad_jugadores,
        max(j.sueldo) as salario_maximo, min(j.sueldo) as salario_minimo,
        avg(j.sueldo) as salario_medio
        from equipos e
        left join jugadores j
        on e.cod_equipo = j.cod_equipo
        group by e.nombre, e.fecha_fundacion,e.cod_equipo
with read only;

create index enfren_jorna on enfrentamientos(num_jornada);

/*
create or replace view vis_jug_info as
    select j.nombre, j.apellido, j.rol, j.sueldo, e.nombre as nombre_equipo
    from jugadores j
    right join equipos e
    on e.cod_equipo = j.cod_equipo
with read only;
*/