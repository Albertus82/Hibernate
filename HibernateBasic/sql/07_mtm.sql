CREATE SEQUENCE seq_docenti;

CREATE SEQUENCE seq_materie;


CREATE TABLE docenti
(
    id_docente         NUMBER (20, 0) NOT NULL,
    cognome            VARCHAR2 (50 CHAR) NOT NULL,
    nome               VARCHAR2 (50 CHAR) NOT NULL,
    data_inserimento   DATE DEFAULT SYSDATE NOT NULL
)
/

ALTER TABLE docenti
ADD CONSTRAINT pk_docenti PRIMARY KEY (id_docente)
USING INDEX
/


CREATE TABLE docenti_materie
(
    id_docente         NUMBER (20, 0) NOT NULL,
    id_materia         NUMBER (10, 0) NOT NULL,
    data_inserimento   DATE DEFAULT SYSDATE NOT NULL
)
/

ALTER TABLE docenti_materie
ADD CONSTRAINT pk_docenti_materie PRIMARY KEY (id_docente, id_materia)
USING INDEX
/


CREATE TABLE materie
(
    id_materia         NUMBER (10, 0) NOT NULL,
    nome               VARCHAR2 (100 CHAR) NOT NULL,
    data_inserimento   DATE DEFAULT SYSDATE NOT NULL
)
/

ALTER TABLE materie
ADD CONSTRAINT pk_materie PRIMARY KEY (id_materia)
USING INDEX
/

ALTER TABLE materie
ADD CONSTRAINT uk1_materie_nome UNIQUE (nome)
USING INDEX
/

ALTER TABLE docenti_materie
ADD CONSTRAINT fk1_docenti_materie_docenti FOREIGN KEY (id_docente)
REFERENCES docenti (id_docente)
/

ALTER TABLE docenti_materie
ADD CONSTRAINT fk2_docenti_materie_materie FOREIGN KEY (id_materia)
REFERENCES materie (id_materia)
/
