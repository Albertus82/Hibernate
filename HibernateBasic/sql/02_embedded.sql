CREATE SEQUENCE seq_persone;


CREATE TABLE persone
(
    id_persona                  NUMBER (20, 0) NOT NULL,
    cognome                     VARCHAR2 (50 CHAR),
    nome                        VARCHAR2 (50 CHAR),
    via                         VARCHAR2 (50 CHAR),
    citta                       VARCHAR2 (50 CHAR),
    codice_avviamento_postale   CHAR (5 CHAR)
)
/

ALTER TABLE persone
ADD CONSTRAINT pk_persone PRIMARY KEY (id_persona)
USING INDEX
/
