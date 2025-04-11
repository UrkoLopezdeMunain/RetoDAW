<<<<<<< HEAD
--Trigger edad
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2020','DD-MM-YYYY'),2000,'habsf','Centinela',3);

insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-1900','DD-MM-YYYY'),2000,'habsf','Centinela',3);

--Trigger sueldo
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-1992','DD-MM-YYYY'),500,'habsf','Centinela',3);

--Trigger fundacion equipo
insert into equipos values(default,'GMA',to_date('18-02-2018','DD-MM-YYYY'),
default);

--Trigger max 6 jugadores
insert into equipos values(default, 'GMA', to_date('18-02-2022','DD-MM-YYYY'),default);

insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'habsf','Centinela',1);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'wdsadf','Duelista',1);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'afw4','Centinela',1);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'ff32','Duelista',1);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'g53','Centinela',1);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'gw43qh5g','Duelista',1);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2002','DD-MM-YYYY'),2000,'afs4','Centinela',1);

--Trigger empezar competicion
update competiciones set estado = 'C' where cod_comp = 1;

=======
--Trigger edad
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-2020','DD-MM-YYYY'),2000,'habsf','Centinela',3);
insert into jugadores values(default,'Pepe','Perez','ESP',
to_date('18-02-1900','DD-MM-YYYY'),2000,'habsf','Centinela',3);

>>>>>>> b3c4c0de2da491ddca988481736677d238093bd2
