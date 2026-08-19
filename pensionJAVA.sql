DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_type
        WHERE typname = 'statut_prsn'
    ) THEN
        CREATE TYPE statut_prsn AS ENUM (
            'Marie(e)',
            'Divorce(e)',
            'Veuf(ve)'
        );
    END IF;
END
$$;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tarif' AND column_name = 'numtarif')
       AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tarif' AND column_name = 'num_tarif') THEN
        ALTER TABLE tarif RENAME COLUMN numtarif TO num_tarif;
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'personne' AND column_name = 'prenom')
       AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'personne' AND column_name = 'prenoms') THEN
        ALTER TABLE personne RENAME COLUMN prenom TO prenoms;
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'fk_im')
       AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'im') THEN
        ALTER TABLE payer RENAME COLUMN fk_im TO im;
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'fk_numtarif')
       AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'num_tarif') THEN
        ALTER TABLE payer RENAME COLUMN fk_numtarif TO num_tarif;
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'date_paiement')
       AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'payer' AND column_name = 'date') THEN
        ALTER TABLE payer RENAME COLUMN date_paiement TO date;
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'conjoint' AND column_name = 'fk_im')
       AND NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'conjoint' AND column_name = 'im') THEN
        ALTER TABLE conjoint RENAME COLUMN fk_im TO im;
    END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'personne'
          AND column_name = 'situation'
          AND udt_name = 'statut_prsn'
    ) THEN
        ALTER TABLE personne ALTER COLUMN situation TYPE VARCHAR(20) USING situation::text;
    END IF;
END
$$;

CREATE TABLE IF NOT EXISTS tarif (
    num_tarif VARCHAR(10) PRIMARY KEY,
    diplome VARCHAR(50),
    categorie VARCHAR(50),
    montant INTEGER
);

CREATE TABLE IF NOT EXISTS personne (
    im VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(25),
    prenoms VARCHAR(50),
    datenais DATE,
    diplome VARCHAR(50),
    contact VARCHAR(50),
    statut BOOLEAN DEFAULT TRUE,
    situation VARCHAR(20),
    nomconjoint VARCHAR(25),
    prenomconjoint VARCHAR(25),
    fk_numtarif VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS payer (
    id SERIAL PRIMARY KEY,
    im VARCHAR(50) NOT NULL,
    num_tarif VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT fk_payer_personne FOREIGN KEY (im)
        REFERENCES personne(im) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_payer_tarif FOREIGN KEY (num_tarif)
        REFERENCES tarif(num_tarif) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS conjoint (
    id SERIAL PRIMARY KEY,
    numpension VARCHAR(10) NOT NULL UNIQUE,
    nomconjoint VARCHAR(25),
    prenomconjoint VARCHAR(25),
    montant INTEGER,
    statutconjoint BOOLEAN DEFAULT TRUE,
    im VARCHAR(50) UNIQUE,
    CONSTRAINT fk_conjoint_personne FOREIGN KEY (im)
        REFERENCES personne(im) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE personne ADD COLUMN IF NOT EXISTS diplome VARCHAR(50);
ALTER TABLE personne ADD COLUMN IF NOT EXISTS nomconjoint VARCHAR(25);
ALTER TABLE personne ADD COLUMN IF NOT EXISTS prenomconjoint VARCHAR(25);
ALTER TABLE personne ADD COLUMN IF NOT EXISTS fk_numtarif VARCHAR(10);

UPDATE personne p
SET diplome = t.diplome
FROM tarif t
WHERE p.diplome IS NULL
  AND p.fk_numtarif = t.num_tarif;

CREATE INDEX IF NOT EXISTS idx_personne_fk_numtarif ON personne(fk_numtarif);
CREATE INDEX IF NOT EXISTS idx_payer_num_tarif ON payer(num_tarif);
CREATE INDEX IF NOT EXISTS idx_payer_im ON payer(im);
CREATE INDEX IF NOT EXISTS idx_conjoint_im ON conjoint(im);
