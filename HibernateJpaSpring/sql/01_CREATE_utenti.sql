CREATE TABLE utenti
(
    username           VARCHAR2 (50 CHAR) CONSTRAINT pk_utenti PRIMARY KEY USING INDEX,
    password           VARCHAR2 (50 CHAR) CONSTRAINT nn1_utenti_password NOT NULL,
    nome               VARCHAR2 (50 CHAR) CONSTRAINT nn2_utenti_nome NOT NULL,
    cognome            VARCHAR2 (50 CHAR) CONSTRAINT nn3_utenti_cognome NOT NULL,
    data_nascita       DATE,
    data_inserimento   DATE DEFAULT SYSDATE CONSTRAINT nn4_utenti_data_inserimento NOT NULL
)
/
