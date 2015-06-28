CREATE SEQUENCE seq_metodo_pagamento;


CREATE TABLE tpccu_carte_di_credito
(
    id_metodo_pagamento   NUMBER (20, 0) NOT NULL,
    numero                VARCHAR2 (16 CHAR) NOT NULL,
    data_scadenza         DATE NOT NULL,
    proprietario          VARCHAR2 (100 CHAR) NOT NULL
)
/

ALTER TABLE tpccu_carte_di_credito
ADD CONSTRAINT pk_tpccu_carte_di_credito PRIMARY KEY (id_metodo_pagamento)
USING INDEX
/

ALTER TABLE tpccu_carte_di_credito
ADD CONSTRAINT ck1_tpccu_carte_numero CHECK (LENGTH (numero) = 16)
/


CREATE TABLE tpccu_conti_correnti
(
    id_metodo_pagamento   NUMBER (20, 0) NOT NULL,
    iban                  VARCHAR2 (40 CHAR) NOT NULL,
    proprietario          VARCHAR2 (100 CHAR) NOT NULL
)
/

ALTER TABLE tpccu_conti_correnti
ADD CONSTRAINT pk_tpccu_conti_correnti PRIMARY KEY (id_metodo_pagamento)
USING INDEX
/


CREATE TABLE tpccu_utenti
(
    username              VARCHAR2 (50 CHAR) NOT NULL,
    password              VARCHAR2 (50 CHAR) NOT NULL,
    nome                  VARCHAR2 (50 CHAR) NOT NULL,
    cognome               VARCHAR2 (50 CHAR) NOT NULL,
    data_nascita          DATE,
    data_inserimento      TIMESTAMP (3) DEFAULT SYSTIMESTAMP NOT NULL,
    id_metodo_pagamento   NUMBER (20, 0)
)
/

ALTER TABLE tpccu_utenti
ADD CONSTRAINT pk_tpccu_utenti PRIMARY KEY (username)
USING INDEX
/
