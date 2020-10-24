CREATE DATABASE evaluation2;

create sequence seq_users;
CREATE TABLE Users(
	id VARCHAR(20) PRIMARY KEY,
	nom VARCHAR(60) NOT NULL,
	mdp VARCHAR(100) NOT NULL,
	etat integer NOT NULL
);

create sequence seq_avion;
CREATE TABLE Avion(
	id VARCHAR(20) PRIMARY KEY,
	nom VARCHAR(60) NOT NULL,
	nb_place integer NOT NULL,
	dateCreation date NOT NULL,
	etat integer NOT NULL
);

create sequence seq_ville;
CREATE TABLE ville(
	id VARCHAR(20) PRIMARY KEY,
	nom VARCHAR(60) NOT NULL,
	dateCreation date NOT NULL,
	etat integer NOT NULL
);

create sequence seq_trajet;
CREATE TABLE Trajet(
	id VARCHAR(20) PRIMARY KEY,
	ville_dep VARCHAR(150) NOT NULL,
	ville_arr VARCHAR(150) NOT NULL,
	distance decimal NOT NULL,
	dateCreation date NOT NULL,
	FOREIGN KEY (ville_dep) REFERENCES ville(id),
	FOREIGN KEY (ville_arr) REFERENCES ville(id),
	etat integer NOT NULL
);

create sequence seq_tarif;
CREATE TABLE Tarif(
	id VARCHAR(20) PRIMARY KEY,
	idtrajet VARCHAR(20) NOT NULL,
	dateApplication Timestamp NOT NULL,
	prix decimal(10,2) NOT NULL,
	FOREIGN KEY (idtrajet) REFERENCES trajet(id),
	etat integer NOT NULL
);


create sequence seq_ConditionTarif;
CREATE TABLE ConditionTarif(
	id VARCHAR(10) PRIMARY KEY,
	description VARCHAR(200) NOT NULL,
	pourcentage decimal NOT NULL
);

create sequence seq_Reduction;
CREATE TABLE Reduction(
	id VARCHAR(10) PRIMARY KEY,
	description VARCHAR(200) NOT NULL,
	age_max integer NOT NULL,
	age_min integer NOT NULL,
	pourcentage decimal NOT NULL
);

create sequence seq_Vol;
create sequence seq_numVol start with 10;
CREATE TABLE Vol(
	id VARCHAR(20) PRIMARY KEY,
	Num VARCHAR(20) NOT NULL,
	idTrajet VARCHAR(20) NOT NULL,
	idAvion VARCHAR(20) NOT NULL,
	dateDep date NOT NULL,
	horaire time,
	FOREIGN KEY	(idtrajet) REFERENCES trajet(id),
	FOREIGN KEY	(idAvion) REFERENCES avion(id),
	etat integer 
);

create sequence seq_billet;
CREATE TABLE Billet(
	id VARCHAR(20) PRIMARY KEY,
	NomClient VARCHAR(50) NOT NULL,
	age integer NOT NULL,
	idVol VARCHAR(20) NOT NULL,
	modifiable integer NOT NULL,
	remboursable integer NOT NULL,
	montant decimal(10,2),
	dateCreation date,
	FOREIGN KEY (idVol) REFERENCES Vol(id),
	etat integer 
);

create sequence seq_remboursement;
CREATE TABLE Remboursement(
	id VARCHAR(20) PRIMARY KEY,
	idBillet VARCHAR(20) NOT NULL,
	montant decimal NOT NULL,
	FOREIGN KEY (idBillet) REFERENCES billet(id),
	etat integer
);

--//USERS
INSERT INTO Users VALUES('US'||nextVal('seq_users'),'Admin','1111',0);

--//Ville
INSERT INTO Ville VALUES('VI'||nextVal('seq_ville'),'Tana','2020-10-11',0);
INSERT INTO Ville VALUES('VI'||nextVal('seq_ville'),'Toamasina','2020-10-11',0);
INSERT INTO Ville VALUES('VI'||nextVal('seq_ville'),'Diego','2020-10-11',0);
INSERT INTO Ville VALUES('VI'||nextVal('seq_ville'),'Nosybe','2020-10-11',0);

--//Avion
INSERT INTO Avion VALUES('AV'||nextVal('seq_avion'),'Twin otter',19,'2020-10-11',0);
INSERT INTO Avion VALUES('AV'||nextVal('seq_avion'),'Beech craft 200',8,'2020-10-11',0);

--//Trajet
INSERT INTO Trajet VALUES('TR'||nextVal('seq_trajet'),'VI1','VI2',354,'2020-10-12',0);
INSERT INTO Trajet VALUES('TR'||nextVal('seq_trajet'),'VI2','VI1',354,'2020-10-12',0);

--//Tarif
INSERT INTO Tarif VALUES('TI'||nextVal('seq_tarif'),'TR1','2020-10-12',15000,0);
INSERT INTO Tarif VALUES('TI'||nextVal('seq_tarif'),'TR2','2020-10-12',15000,0);

--//condition tarif
INSERT INTO ConditionTarif VALUES('CT'||nextVal('seq_ConditionTarif'),'modifiable',15);
INSERT INTO ConditionTarif VALUES('CT'||nextVal('seq_ConditionTarif'),'remboursable',15);
INSERT INTO ConditionTarif VALUES('CT'||nextVal('seq_ConditionTarif'),'penalite',10);

--//reduction
INSERT INTO Reduction VALUES('RD'||nextVal('seq_Reduction'),'Adulte',100,12,100);
INSERT INTO Reduction VALUES('RD'||nextVal('seq_Reduction'),'Enfant',12,2,50);
INSERT INTO Reduction VALUES('RD'||nextVal('seq_Reduction'),'Bebe',2,0,10);

--//Vol
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC01', 'TR1', 'AV1', '2020-10-12', '12:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC02', 'TR1', 'AV1', '2020-10-12', '15:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC03', 'TR2', 'AV1', '2020-10-14', '12:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC04', 'TR1', 'AV2', '2020-10-14', '13:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC05', 'TR2', 'AV1', '2020-10-15', '12:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC06', 'TR1', 'AV2', '2020-10-15', '15:30',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC07', 'TR1', 'AV2', '2020-10-17', '01:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC08', 'TR1', 'AV1', '2020-10-17', '16:00',0);
INSERT INTO Vol VALUES('VL'||nextval('seq_vol'), 'CC09', 'TR2', 'AV1', '2020-10-18', '02:30',0);

--//billet
INSERT INTO billet VALUES('BI'||nextval('seq_billet'), 'Rakoto',22,'VL1',1,1,1500,'2020-10-13',0);
INSERT INTO billet VALUES('BI'||nextval('seq_billet'), 'Rabe',26,'VL1',0,0,12000,'2020-10-13',0);
INSERT INTO billet VALUES('BI'||nextval('seq_billet'), 'Rama',7,'VL1',1,0,13500,'2020-10-13',0);

--//view
CREATE or REPLACE view trajet_detail as
select 	
		trajet.*,
		ville.nom origin,
		v2.nom destination,
		tarif.prix as prix
from 
	trajet join tarif on trajet.id = tarif.idtrajet 
	join ville on ville.id = trajet.ville_dep 
	join ville as v2 on v2.id = trajet.ville_arr where trajet.etat != '99';

CREATE or REPLACE view trajet_detail_tout as
select 
		trajet.*,
		ville.nom origin,
		v2.nom destination,
		CASE
		    WHEN tarif.prix > 0 THEN tarif.prix
	    	ELSE 0
		END prix
from 
	trajet join ville on ville.id = trajet.ville_dep 
	join ville as v2 on v2.id = trajet.ville_arr 
	left join tarif on trajet.id = tarif.idtrajet where trajet.etat != '99';

create view count_billet as
select 	vol.id idvol
		,count(billet.*) nb_billet 
from vol left join (select * from billet where etat != '20' and etat != '99') as billet on vol.id = billet.idvol 
group by vol.id ;

create view vol_detail as 
select 
		vol.*,
		trajet_detail.origin,
		trajet_detail.destination,
		trajet_detail.prix,
		CASE 
			WHEN vol.etat = 10 THEN 'validé'
			WHEN vol.etat = 0 THEN 'en cours'
			WHEN vol.etat = 99 THEN 'annulé'
			ELSE 'null'
		END etat_string,
		avion.nom nom_avion,
		avion.nb_place nb_place,
		count_billet.nb_billet,
		(nb_place - nb_billet) reste_place
from vol join trajet_detail on trajet_detail.id = vol.idTrajet
	join avion on avion.id = vol.idAvion join count_billet on count_billet.idvol = vol.id; 

create view billet_detail as
select billet.*,
		vol.num num_vol,
		CASE 
			WHEN Remboursement.montant > 0 THEN Remboursement.montant
			ELSE 0
		END montant_remboursement
from billet join vol on vol.id = billet.idVol
	left join Remboursement on Remboursement.idBillet = billet.id;






------------------///////////

select *from vol_detail where datedep >= '2020-10-02' and datedep <= '2020-10-19' and horaire = '16:00' and idtrajet = '' and idAvion = ''; 

create view TrajetComplet as
select 
		ville.nom origin,
		v2.nom destination,
		trajet.distance,
		tarif.prix,
		CASE
		    WHEN ConditionTarif.description = 'modifiable' THEN ConditionTarif.pourcentage
	    	ELSE 0
		END modifiable,
		CASE
		    WHEN ct2.description = 'remboursable' THEN ct2.pourcentage
	    	ELSE 0
		END remboursable
from 
	trajet join ville on ville.id = trajet.ville_dep 
	join ville as v2 on v2.id = trajet.ville_arr 
	left join tarif on trajet.id = tarif.idtrajet,ConditionTarif,ConditionTarif as ct2;



select * from TrajetComplet where modifiable > 0 and remboursable > 0;

-------Clear base;
drop view billet_detail;
drop sequence seq_remboursement;
drop table Remboursement;
drop sequence seq_billet;
drop table billet;
drop sequence seq_vol;
drop sequence seq_numVol;
drop view vol_detail;
drop view count_billet;
drop view trajet_detail_tout;
drop view trajet_detail;
drop table vol;
drop sequence seq_tarif;
drop table tarif;
drop sequence seq_trajet;
drop table trajet;
