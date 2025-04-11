<<<<<<< HEAD
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
=======
CREATE OR REPLACE procedure pr_empezar_competicion(continuar out boolean)
    as
    begin
        continuar := false;
        update competiciones set estado = 'C' where cod_comp = 1;
        continuar := true;
end pr_empezar_competicion;
>>>>>>> b3c4c0de2da491ddca988481736677d238093bd2
