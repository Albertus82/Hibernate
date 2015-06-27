CREATE SEQUENCE seq_persone;

CREATE TABLE persone
(
    id_persona   NUMBER (20, 0) NOT NULL,
    cognome      VARCHAR2 (50 BYTE),
    nome         VARCHAR2 (50 BYTE),
    via          VARCHAR2 (50 BYTE),
    citta        VARCHAR2 (50 BYTE),
    cap          CHAR (5 BYTE)
)
/

ALTER TABLE persone
ADD CONSTRAINT pk_persone PRIMARY KEY (id_persona)
USING INDEX
/
