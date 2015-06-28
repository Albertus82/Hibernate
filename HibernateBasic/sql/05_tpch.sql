CREATE OR REPLACE PUBLIC SYNONYM tpch_utenti FOR utenti;


CREATE TABLE tpch_metodi_pagamento
(
    id_metodo_pagamento        NUMBER (20, 0) NOT NULL,
    id_tipo_metodo_pagamento   NUMBER (2, 0) NOT NULL,
    titolare                   VARCHAR2 (100 CHAR) NOT NULL,
    carta_numero               VARCHAR2 (16 CHAR),
    carta_data_scadenza        DATE,
    conto_iban                 VARCHAR2 (40 CHAR),
    conto_nome_banca           VARCHAR2 (100 CHAR),
    data_inserimento           TIMESTAMP (3) DEFAULT SYSTIMESTAMP NOT NULL
)
/

ALTER TABLE tpch_metodi_pagamento
ADD CONSTRAINT pk_metodi_pagamento PRIMARY KEY (id_metodo_pagamento)
USING INDEX
/


/* Questa ulteriore tabella non e' necessaria ma e' sicuramente utile */
CREATE TABLE tpch_tipi_metodo_pagamento
(
    id_tipo_metodo_pagamento   NUMBER (2, 0) NOT NULL,
    descrizione                VARCHAR2 (50 BYTE) NOT NULL
)
/

ALTER TABLE tpch_tipi_metodo_pagamento
ADD CONSTRAINT pk_tipi_metodo_pagamento PRIMARY KEY (id_tipo_metodo_pagamento)
USING INDEX
/

ALTER TABLE tpch_metodi_pagamento
ADD CONSTRAINT fk1_tpch_metodi_pagamento_tipo FOREIGN KEY (id_tipo_metodo_pagamento)
REFERENCES tpch_tipi_metodo_pagamento (id_tipo_metodo_pagamento)
/

INSERT
  INTO tpch_tipi_metodo_pagamento (id_tipo_metodo_pagamento, descrizione)
VALUES (1, 'Conto corrente');

INSERT
  INTO tpch_tipi_metodo_pagamento (id_tipo_metodo_pagamento, descrizione)
VALUES (2, 'Carta di credito');

COMMIT WORK;
