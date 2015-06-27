CREATE SEQUENCE seq_offerte;

CREATE SEQUENCE seq_oggetti;


CREATE TABLE offerte
(
    id_offerta   NUMBER (20, 0) NOT NULL,
    importo      NUMBER (12, 2) NOT NULL,
    id_oggetto   NUMBER (20, 0) NOT NULL
)
/

ALTER TABLE offerte
ADD CONSTRAINT pk_offerte PRIMARY KEY (id_offerta)
USING INDEX
/


CREATE TABLE oggetti
(
    id_oggetto         NUMBER (20, 0) NOT NULL,
    descrizione        VARCHAR2 (50 CHAR) NOT NULL,
    data_inserimento   DATE NOT NULL
)
/

ALTER TABLE oggetti
ADD CONSTRAINT pk_oggetti PRIMARY KEY (id_oggetto)
USING INDEX
/

ALTER TABLE offerte
ADD CONSTRAINT fk1_offerte_oggetti FOREIGN KEY (id_oggetto)
REFERENCES oggetti (id_oggetto)
/
