CREATE OR REPLACE PUBLIC SYNONYM tpsc_utenti FOR utenti;


CREATE TABLE tpsc_carte_di_credito
(
    id_carta_di_credito   NUMBER (20, 0) NOT NULL,
    numero                VARCHAR2 (16 CHAR) NOT NULL,
    data_scadenza         DATE NOT NULL,
    data_inserimento      DATE DEFAULT SYSDATE NOT NULL
)
/

ALTER TABLE tpsc_carte_di_credito
ADD CONSTRAINT pk_tpsc_carte_di_credito PRIMARY KEY (id_carta_di_credito)
USING INDEX
/

ALTER TABLE tpsc_carte_di_credito
ADD CONSTRAINT ck1_tpsc_carte_di_credito_num CHECK (LENGTH (numero) = 16)
/


CREATE TABLE tpsc_conti_correnti
(
    id_conto_corrente   NUMBER (20, 0) NOT NULL,
    iban                VARCHAR2 (40 CHAR) NOT NULL,
    nome_banca          VARCHAR2 (100 CHAR),
    data_inserimento    DATE DEFAULT SYSDATE NOT NULL
)
/

ALTER TABLE tpsc_conti_correnti
ADD CONSTRAINT pk_tpsc_conti_correnti PRIMARY KEY (id_conto_corrente)
USING INDEX
/


CREATE TABLE tpsc_metodi_pagamento
(
    id_metodo_pagamento   NUMBER (20, 0) NOT NULL,
    titolare              VARCHAR2 (100 CHAR) NOT NULL,
    data_inserimento      DATE DEFAULT SYSDATE NOT NULL
)
/

ALTER TABLE tpsc_metodi_pagamento
ADD CONSTRAINT pk_tpsc_metodi_pagamento PRIMARY KEY (id_metodo_pagamento)
USING INDEX
/


ALTER TABLE tpsc_carte_di_credito
ADD CONSTRAINT fk1_tpsc_carte_di_credito_mp FOREIGN KEY (id_carta_di_credito)
REFERENCES tpsc_metodi_pagamento (id_metodo_pagamento)
/

ALTER TABLE tpsc_conti_correnti
ADD CONSTRAINT fk1_tpsc_conti_correnti_mp FOREIGN KEY (id_conto_corrente)
REFERENCES tpsc_metodi_pagamento (id_metodo_pagamento)
/
