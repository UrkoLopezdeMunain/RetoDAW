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
/

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
/

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
/

/*
Trigger para comprobar que máximo son 6 jugadores en el equipo al que se
quiere añadir la persona
*/

create or replace trigger tr_max_6_jugadores
before insert or update of cod_equipo on jugadores
for each row
declare
    v_cantidad_jugadores number;
    
    e_demasiados_jugadores exception;
begin
    select count(*) into v_cantidad_jugadores
    from jugadores
    where cod_equipo = :new.cod_equipo;
    
    if v_cantidad_jugadores = 6 then
        raise e_demasiados_jugadores;
    end if;
exception
    when e_demasiados_jugadores then
        raise_application_error(-20004,'Ya hay 6 jugadores en el equipo '
        || 'no puede añadir mas jugadores.');
end tr_max_6_jugadores;
/

--Trigger para comprobar si la competicion puede comenzar
create or replace trigger tr_empezar_competicion4
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
            close c_cantidad_jugadores;
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
end tr_empezar_competicion4;
/