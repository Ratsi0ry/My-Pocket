DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_type
        WHERE typname = 'statut_prsn'
    ) THEN
        CREATE TYPE statut_prsn AS ENUM (
            'marié(e)',
            'divorcé(e)',
            'veuf(ve)'
        );
    END IF;
END
$$;


CREATE TABLE IF NOT EXISTS tarif (
    numTarif VARCHAR(10) PRIMARY KEY,
    diplome VARCHAR(50),
    categorie VARCHAR(50),
    montant INTEGER
);


CREATE TABLE IF NOT EXISTS personne (
    IM VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(25),
    prenom VARCHAR(25),
    dateNAis DATE,
    contact VARCHAR(50),
    statut BOOLEAN DEFAULT TRUE,
    situation statut_prsn DEFAULT 'veuf(ve)',
    FK_numTarif VARCHAR(10),

    CONSTRAINT fk_numTarif_personne FOREIGN KEY (FK_numTarif)
    REFERENCES tarif(numTarif) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS payer (
    id SERIAL PRIMARY KEY,
    date_paiement TIMESTAMP NOT NULL,
    FK_numTarif VARCHAR(10) NOT NULL,
    FK_IM VARCHAR(50) NOT NULL,

    CONSTRAINT fk_numTarif_payer FOREIGN KEY (FK_numTarif)
	REFERENCES tarif(numTarif) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_IM_payer FOREIGN KEY (FK_IM)
    REFERENCES personne(IM) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS conjoint (
    id SERIAL PRIMARY KEY,
    numPension VARCHAR(10) NOT NULL UNIQUE,
    nomConjoint VARCHAR(25),
    prenomConjoint VARCHAR(25),
    montant INTEGER,
    statutConjoint BOOLEAN DEFAULT TRUE,
    FK_IM VARCHAR(50) NOT NULL,

    CONSTRAINT fk_IM_conjoint FOREIGN KEY (FK_IM)
	REFERENCES personne(IM) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE INDEX IF NOT EXISTS idx_personne_fk_numtarif
    ON personne(FK_numTarif);

CREATE INDEX IF NOT EXISTS idx_payer_fk_numtarif
    ON payer(FK_numTarif);

CREATE INDEX IF NOT EXISTS idx_payer_fk_im
    ON payer(FK_IM);

CREATE INDEX IF NOT EXISTS idx_conjoint_fk_im
    ON conjoint(FK_IM);


SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public';

ALTER TABLE conjoint ADD constraint unique_conjoint_fk_im UNIQUE (fk_im);

SELECT fk_im, COUNT(*)
FROM conjoint
GROUP BY fk_im
HAVING COUNT(*) > 1;

SELECT *
FROM conjoint
WHERE fk_im = 'F123';

