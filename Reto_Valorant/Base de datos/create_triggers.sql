drop table jugadores;
drop table enfrentamientos;
drop table jornadas;
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

insert into juegos (cod_juego,fecha_salida,nombre)
values(default,to_date('02-06-2020','DD-MM-YYYY'),1);

create table usuarios(
    cod_usuario number(2) generated always as identity,
    nombre varchar2(20) not null unique,
    contraseña varchar2(20),
    tipo_usuario varchar(1),
    constraint usu_cod_pk primary key(cod_usuario),
    constraint usu_tip_ck check(tipo_usuario in('n','a'))
);

insert into usuarios (cod_usuario,nombre,contraseña,tipo_usuario)
values(default,'admin','Jm12345','a');

create table equipos(
    cod_equipo number(2) generated always as identity,
    nombre varchar2(15) not null,
    fecha_fundacion date,
    puntuacion number(2) default 0,
    constraint equ_cod_pk primary key(cod_equipo)
);

create table competiciones(
    cod_comp number(2) generated always as identity,
    estado varchar(1) not null,
    cod_juego number (2),
    constraint com_cod_pk primary key(cod_comp),
    constraint com_jue_fk foreign key (cod_juego) 
        references juegos (cod_juego),
    constraint com_est_ck check (estado in ('A','C'))
);

insert into competiciones (cod_comp,estado,cod_juego)
values(default,'A',1);

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
    rol varchar2(20),
    cod_equipo number(2),
    constraint jug_cod_pk primary key(cod_jugador),
    constraint jug_equ_fk foreign key (cod_equipo) 
        references equipos (cod_equipo),
    constraint jug_rol_ck check (rol in ('Centinela','Controlador',
                                    'Iniciador','Duelista'))
    );

create or replace view vis_equ_jug as
    select e.cod_equipo, count(j.cod_jugador) as cantidad_jugadores
        from equipos e
        left join jugadores j
        on e.cod_equipo = j.cod_equipo
        group by e.cod_equipo;
        

--Trigger para comprobar si tiene edad correcta

create or replace trigger tr_jug_nac
before insert or update of fecha_nac on jugadores
for each row
    declare
        e_mensaje varchar2(255);
        edad_mayor exception;
        edad_menor exception;
    begin
        --La edad es menos de 16
        if :new.fecha_nac > add_months(sysdate,-192) then
            raise edad_menor;
        --La edad es mas de 65
        elsif :new.fecha_nac < add_months(sysdate,-780) then
           raise edad_mayor;
        end if;
    exception
        when edad_mayor then
            e_mensaje := 'La persona que se ha intentado ';
            if inserting then
                e_mensaje := e_mensaje || 'insertar';
            else
                e_mensaje := e_mensaje || 'actualizar';
            end if;
            e_mensaje := e_mensaje || ' tiene mas de 65 años.';
            raise_application_error(-20001,e_mensaje);
        when edad_menor then
            e_mensaje := 'La persona que se ha intentado ';
            if inserting then
                e_mensaje := e_mensaje || 'insertar';
            else
                e_mensaje := e_mensaje || 'actualizar';
            end if;
            e_mensaje := e_mensaje || ' tiene menos de 16 años.';
            raise_application_error(-20001,e_mensaje);
end tr_jug_nac;

/*
Trigger para comprobar si cobra mas de 1137
Si da error es por este trigger, no sabemos porque, pero no funciona si
se hace el trigger con el comentario encima
*/

create or replace trigger tr_jug_sueldo
before insert or update of sueldo on jugadores
for each row when (new.sueldo < 1137)
    declare
        e_mensaje varchar2(255);
        sueldo exception;
    begin
        raise sueldo;
    exception
        when sueldo then
            e_mensaje := 'La persona que se ha intentado ';
            if inserting then
                e_mensaje := e_mensaje || 'insertar';
            else
                e_mensaje := e_mensaje || 'actualizar';
            end if;
            e_mensaje := e_mensaje || ' tiene un sueldo menos a 1137.';
            raise_application_error(-20002,e_mensaje);
end tr_jug_sueldo;

/*
Trigger para comprobar si la fecha de fundacion del equipo es posterior
a la fecha de creacion del juego
*/

create or replace trigger tr_equi_fecha_fund
before insert or update of fecha_fundacion on equipos
for each row 
    declare
        fecha_salida date;
    
        e_mensaje varchar2(255);
        fecha_no_valida exception;
    begin
    
        select fecha_salida into fecha_salida
        from juegos
        where cod_juego = 1;
        
        if :new.fecha_fundacion < fecha_salida then
            raise fecha_no_valida;
        end if;
    exception
        when fecha_no_valida then
            e_mensaje := 'El equipo que se ha intentado ';
            if inserting then
                e_mensaje := e_mensaje || 'insertar';
            else
                e_mensaje := e_mensaje || 'actualizar';
            end if;
            e_mensaje := e_mensaje || ' se funda antes de la creacion ' ||
            'del juego, que es: ' || to_date(fecha_salida,'DD-MM-YYYY');
            raise_application_error(-20003,e_mensaje);
end tr_equi_fecha_fund;

create or replace trigger tr_empezar_competicion
before update of estado on competiciones
declare
    cantidad_equipos number;
    equipos_correctos number;
    equipos_totales number;
            
    cursor c_cantidad_jugadores is
        select *
        from vis_equ_jug;
    p_cantidad_jugadores vis_equ_jug%rowtype;
            
    e_no_hay_equipos exception;
    e_equipos_no_pares exception;
    e_jugadores_no_encontrados exception;
    e_no_suficientes_jugadores exception;
begin
    select count(*) into cantidad_equipos
    from equipos;
    
    if cantidad_equipos > 0 then
                
        if mod(cantidad_equipos, 2) = 0 then
                
            open c_cantidad_jugadores;
                    
            fetch c_cantidad_jugadores into p_cantidad_jugadores;
                    
            if c_cantidad_jugadores%found then
                    
                while c_cantidad_jugadores%found loop
                            
                    if p_cantidad_jugadores.cantidad_jugadores < 2 then
                        raise e_no_suficientes_jugadores;
                    end if;
                        
                    fetch c_cantidad_jugadores into p_cantidad_jugadores;
                end loop;
            else
                raise e_jugadores_no_encontrados;
            end if;
        else
            raise e_equipos_no_pares;
        end if;
    else
        raise e_no_hay_equipos;
    end if;
exception
    when e_no_suficientes_jugadores then
        raise_application_error(-20003,'No hay suficientes jugadores en ' ||
                           'el equipo ' || p_cantidad_jugadores.cod_equipo ||
                           ', deben ser al menos 2 jugadores por equipo.');
    when e_jugadores_no_encontrados then
         raise_application_error(-20003,'No se ha encontrado ningun ' ||
                        'jugador en los equipos.');
    when e_equipos_no_pares then
        raise_application_error(-20098,'Los equipos no pueden ser impares');
    when e_no_hay_equipos then
        raise_application_error(-20098,'No se ha encontrado ningun equipo');
end tr_empezar_competicion;