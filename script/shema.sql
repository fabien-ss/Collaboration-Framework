CREATE SEQUENCE seq_emp;

CREATE TABLE IF NOT EXISTS EMPLOYE(
    id_employe VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(250) NOT NULL,
    prenom VARCHAR(250) NOT NULL,
    date_enregistrement timestamp NOT NULL,
    mail VARCHAR(250) NOT NULL
);
