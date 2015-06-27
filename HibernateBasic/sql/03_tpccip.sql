CREATE SEQUENCE seq_carte_di_credito;

CREATE SEQUENCE seq_conti_correnti;


CREATE TABLE tpccip_carte_di_credito
(
    id_carta_di_credito   NUMBER (20, 0) NOT NULL,
    numero                VARCHAR2 (16 CHAR) NOT NULL,
    data_scadenza         DATE NOT NULL,
    proprietario          VARCHAR2 (100 CHAR) NOT NULL
)
/

ALTER TABLE tpccip_carte_di_credito
ADD CONSTRAINT pk_tpccip_carte_di_credito PRIMARY KEY (id_carta_di_credito)
USING INDEX
/

ALTER TABLE tpccip_carte_di_credito
ADD CONSTRAINT ck1_tpccip_carte_numero CHECK (LENGTH(numero)=16
)
/


CREATE TABLE tpccip_conti_correnti
(
    id_conto_corrente   NUMBER (20, 0) NOT NULL,
    iban                VARCHAR2 (40 CHAR) NOT NULL,
    proprietario        VARCHAR2 (100 CHAR) NOT NULL
)
/

ALTER TABLE tpccip_conti_correnti
ADD CONSTRAINT pk_tpccip_conti_correnti PRIMARY KEY (id_conto_corrente)
USING INDEX
/


CREATE TABLE tpccip_utenti
(
    username              VARCHAR2 (50 CHAR),
    password              VARCHAR2 (50 CHAR),
    nome                  VARCHAR2 (50 CHAR),
    cognome               VARCHAR2 (50 CHAR),
    data_nascita          DATE,
    data_inserimento      TIMESTAMP (3) DEFAULT SYSTIMESTAMP,
    id_metodo_pagamento   NUMBER (20, 0)
)
/

ALTER TABLE tpccip_utenti
ADD CONSTRAINT nn_tpccip_utenti_password CHECK ("PASSWORD" IS NOT NULL)
/

ALTER TABLE tpccip_utenti
ADD CONSTRAINT nn_tpccip_utenti_nome CHECK ("NOME" IS NOT NULL)
/

ALTER TABLE tpccip_utenti
ADD CONSTRAINT nn_tpccip_utenti_cognome CHECK ("COGNOME" IS NOT NULL)
/

ALTER TABLE tpccip_utenti
ADD CONSTRAINT nn_tpccip_utenti_data_inserim CHECK ("DATA_INSERIMENTO" IS NOT NULL)
/

ALTER TABLE tpccip_utenti
ADD CONSTRAINT pk_tpccip_utenti PRIMARY KEY (username)
USING INDEX
/
