//procedimiento que se encarga de iniciar la competicion
CREATE OR REPLACE procedure pr_empezar_competicion(continuar out number)
    as
    begin
        continuar := 0;
        update competiciones set estado = 'C' where cod_comp = 1;
        continuar := 1;
end pr_empezar_competicion;

update competiciones set estado = 'A' where cod_comp = 1;

/*
procedimiento que se encarga de devolver un informe con la informacion de los
equipos y sus jugadores
*/
create or replace procedure pr_conseguir_info_equipos (c_equ_info out sys_refcursor)
as
    no_datos exception;
begin
    open c_equ_info for
        select * from vis_equ_info;
    if c_equ_info%notfound then
        raise no_datos;
    end if;
exception
    when no_datos then
        raise_application_error(-20098,'No se ha encontrado ningun equipo');
end pr_conseguir_info_equipos;
/*
procedimiento que se encarga de devolver un informe con la informacion de los
jugadores de un equipo en concreto
*/
create or replace procedure pr_conseguir_info_jugadores 
(v_nom_equipo in equipos.nombre%type, c_cursor out sys_refcursor)
as
    v_existe varchar(1) := 'n';
begin
    select 's' into v_existe
    from equipos
    where lower(nombre) = lower(v_nom_equipo);

    open c_cursor for
        select j.nombre, j.apellido, j.rol, j.sueldo
        from jugadores j
        right join equipos e
        on e.cod_equipo = j.cod_equipo
        where lower(e.nombre) = lower(v_nom_equipo);
exception
    when no_data_found then
        raise_application_error(-20098,'No se ha encontrado el equipo '
        || v_nom_equipo);
end pr_conseguir_info_jugadores;

/*
procedimiento que se encarga de devolver un informe con la informacion de los
enfrentamientos
*/
create or replace procedure pr_conseguir_info_enfren
(v_num_jornada in jornadas.num_jornada%type, c_cursor out sys_refcursor)
as
begin
    open c_cursor for
        select eq1.nombre, en.resultados_eq_1, en.resultados_eq_2,
                eq2.nombre, to_char(en.hora, 'HH24:MI') as hora
        from equipos eq1
        join enfrentamientos en
        on en.cod_equ_1 = eq1.cod_equipo
        join equipos eq2
        on en.cod_equ_2 = eq2.cod_equipo
        where en.num_jornada = 1;
exception
    when no_data_found then
        raise_application_error(-20098,'No se ha encontrado ningún enfrentamiento '
        || 'en la jornada '|| v_num_jornada);
end pr_conseguir_info_enfren;