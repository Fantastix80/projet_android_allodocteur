CREATE DATABASE ProjetAndroid;

USE ProjetAndroid;

CREATE TABLE utilisateur(
	email VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(40) NOT NULL,
    prenom VARCHAR(40) NOT NULL,
    isMedecin TINYINT NOT NULL DEFAULT 0,
    mdp CHAR(128) NOT NULL
);