CREATE OR REPLACE procedure pr_empezar_competicion(continuar out boolean)
    as
    begin
        continuar := false;
        update competiciones set estado = 'C' where cod_comp = 1;
        continuar := true;
end pr_empezar_competicion;