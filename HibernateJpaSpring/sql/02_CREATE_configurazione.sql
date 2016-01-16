CREATE TABLE configurazione
(
    chiave             VARCHAR2 (100 CHAR) CONSTRAINT pk_configurazione2 PRIMARY KEY USING INDEX,
    valore             VARCHAR2 (2000 CHAR),
    data_inserimento   DATE DEFAULT SYSDATE CONSTRAINT nn1_configurazione_data_insert NOT NULL
)
/
