create table adherent (
  id_adherent number(4) not null,
  nom_adherent varchar2(50) ,
  prenom_adherent varchar2(50),
  constraint pk_adherent primary key (id_adherent)
);

create table oeuvre (
  id_oeuvre number(4) not null,
  id_proprietaire number(4) not null,
  titre varchar2(250) ,
  prix number(11,2) constraint ck_oeuvre_prix check (prix > 0),
  constraint pk_oeuvre primary key (id_oeuvre)
) ;

create table cles (
  id_cle char(15) not null,
  val_cle number(4),
  lib_cle varchar2(80),
  constraint pk_cles primary key (id_cle)
);

create table proprietaire (
  id_proprietaire number(4) not null,
  nom_proprietaire varchar2(50) ,
  prenom_proprietaire varchar2(50) ,
  login varchar2(30) ,
  pwd varchar2(30),
  constraint pk_proprietaire primary key (id_proprietaire)
);

create table reservation (
  date_reservation date not null,
  id_oeuvre number(4) not null,
  id_adherent number(4) not null,
  statut varchar2(20) constraint ck_reservation_statut check(statut in ('Attente','Confirmée')),
  constraint pk_reservation primary key (date_reservation, id_oeuvre)
) ;


insert into adherent (id_adherent, nom_adherent, prenom_adherent) VALUES(1, 'Hugo', 'Victor');
insert into adherent (id_adherent, nom_adherent, prenom_adherent) VALUES(2, 'Claudel', 'Camille');
insert into adherent (id_adherent, nom_adherent, prenom_adherent) VALUES(3, 'Matisse', 'Henri');
insert into adherent (id_adherent, nom_adherent, prenom_adherent) VALUES(4, 'Sand', 'Georges');
insert into adherent (id_adherent, nom_adherent, prenom_adherent) VALUES(5, 'Balzac', 'Honoré');

insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(1, 2, 'Le chapeau à plumes', 557.00);
insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(2, 3, 'La balançoire', 800.00);
insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(3, 4, 'Les oreilles du lapin', 350.00);
insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(4, 2, 'Le penseur debout', 1250.00);
insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(5, 3, 'Les buveurs de café', 450.00);
insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(6, 5, 'La petite liseuse', 500.00);
insert into oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES(7, 2, 'En avant toutes', 750.00);

insert into cles (id_cle, val_cle, lib_cle) VALUES('OEUVRE', 7, 'Dernier id Oeuvre');
insert into cles (id_cle, val_cle, lib_cle) VALUES('PROPRIETAIRE', 6, 'Dernier id Propriétaire');

insert into proprietaire (id_proprietaire, nom_proprietaire, prenom_proprietaire, login, pwd) VALUES(1, 'Administrateur', '', 'admin', 'mdp');
insert into proprietaire (id_proprietaire, nom_proprietaire, prenom_proprietaire, login, pwd) VALUES(2, 'Gator', 'Ali', 'gator', 'ali');
insert into proprietaire (id_proprietaire, nom_proprietaire, prenom_proprietaire, login, pwd) VALUES(3, 'Zhette', 'Annie', 'zhette', 'annie');
insert into proprietaire (id_proprietaire, nom_proprietaire, prenom_proprietaire, login, pwd) VALUES(4, 'Auchon', 'Paul', 'auchon', 'paul');
insert into proprietaire (id_proprietaire, nom_proprietaire, prenom_proprietaire, login, pwd) VALUES(5, 'Thimaitre', 'Vincent', 'thimaitre', 'vincent');
insert into proprietaire (id_proprietaire, nom_proprietaire, prenom_proprietaire, login, pwd) VALUES(6, 'Iffique', 'Eléonore', 'iffique', 'eléonore');

alter table oeuvre
add constraint fk_oeuvre_proprietaire foreign key (id_proprietaire) references proprietaire (id_proprietaire);

alter table reservation
add constraint fk_reservation_adherent foreign key (id_adherent) references adherent (id_adherent);
alter table reservation
add constraint fk_reservation_oeuvre foreign key (id_oeuvre) references oeuvre (id_oeuvre);

create or replace function generer_pk (nom_cle in char)
return number
is
valeur cles.val_cle%TYPE;
Begin
   select val_cle into valeur from cles
   where id_cle = nom_cle for update;
   valeur := valeur + 1;
   update cles set val_cle = valeur where id_cle = nom_cle;
   return(valeur);
Exception
 When others then
   Raise;
End;
/