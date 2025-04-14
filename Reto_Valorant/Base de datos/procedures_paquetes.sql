CREATE OR REPLACE procedure pr_empezar_competicion(continuar out boolean)
    as
    begin
        continuar := false;
        update competiciones set estado = 'C' where cod_comp = 1;
        continuar := true;
end pr_empezar_competicion;

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
        where lower(e.nombre) = lower('gma');
exception
    when no_data_found then
        raise_application_error(-20098,'No se ha encontrado el equipo '
        || v_nom_equipo);
end pr_conseguir_info_jugadores;
