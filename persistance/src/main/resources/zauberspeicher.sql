DROP TABLE IF EXISTS ZAUBERSPEICHER;

CREATE TABLE ZAUBERSPEICHER(
                               ID SERIAL PRIMARY KEY,
                               ZAUBER VARCHAR(100) NOT NULL,
                               HELDID BIGINT NOT NULL REFERENCES HELDEN(ID),
                               KOSTEN INTEGER NOT NULL,
                               KOMPLEXITAET VARCHAR(1) NOT NULL,
                               SPOMOS VARCHAR(200) NOT NULL
);
