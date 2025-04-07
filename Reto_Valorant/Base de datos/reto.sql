drop table jugadores;
drop table enfrentamientos;
drop table jornadas;
drop table roles;
drop table competiciones;
drop table equipos;
drop table usuarios;
drop table juegos;

create table juegos(
    cod_juego number(2) generated always as identity,
    fecha_salida date not null,
    nombre varchar2(20) not null,
    constraint jue_cod_pk primary key(cod_juego)
);

create table usuarios(
    cod_usuario number(2) generated always as identity,
    nombre varchar2(20) not null unique,
    contraseña varchar2(20),
    tipo_usuario varchar(1),
    constraint usu_cod_pk primary key(cod_usuario)
);

create table equipos(
    cod_equipo number(2) generated always as identity,
    nombre varchar2(15) not null,
    fecha_fundacion date,
    puntuacion number(2),
    constraint equ_cod_pk primary key(cod_equipo)
);

create table competiciones(
    cod_comp number(2) generated always as identity,
    estado varchar(1) not null,
    cod_juego number (2),
    constraint com_cod_pk primary key(cod_comp),
    constraint com_jue_fk foreign key (cod_juego) 
        references juegos (cod_juego)
);

create table roles(
    cod_rol number(2) generated always as identity,
    rol varchar2(20) not null,
    cod_juego number (2),
    constraint rol_cod_pk primary key(cod_rol),
    constraint rol_jue_fk foreign key (cod_juego) 
        references juegos (cod_juego)
);

create table jornadas(
    num_jornada number(2) generated always as identity,
    fecha_inicio date not null,
    cod_comp number (2),
    constraint jor_num_pk primary key(num_jornada),
    constraint jor_com_fk foreign key (cod_comp) 
        references competiciones (cod_comp)
);

create table enfrentamientos(
    id_enfrentamiento number(2) generated always as identity,
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

create table jugadores(
    cod_jugador number(2) generated always as identity,
    nombre varchar2(20) not null,
    apellido varchar2(20) not null,
    nacionalidad varchar2(20) not null,
    fecha_nac date not null,
    sueldo number(5) not null,
    nicnkame varchar2(20) not null unique,
    cod_rol number(2),
    cod_equipo number(2),
    constraint jug_cod_pk primary key(cod_jugador),
    constraint jug_rol_fk foreign key (cod_rol) 
        references roles (cod_rol),
    constraint jug_equ_fk foreign key (cod_equipo) 
        references equipos (cod_equipo)
    );