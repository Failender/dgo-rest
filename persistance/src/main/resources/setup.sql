DROP TABLE IF EXISTS ASSET;
DROP TABLE IF EXISTS GEGENSTAND_TO_LAGERORT;
DROP TABLE IF EXISTS LAGERORT;
DROP TABLE IF EXISTS ROLES_TO_USER;
DROP TABLE IF EXISTS PDFS_TO_USER;
DROP TABLE IF EXISTS HELD_GELDBOERSE;
DROP TABLE IF EXISTS HELD_INVENTAR;
DROP TABLE IF EXISTS HELD_UEBERSICHT;
DROP TABLE IF EXISTS ROLES_TO_RIGHTS;
DROP TABLE IF EXISTS ZAUBERSPEICHER;
DROP TABLE IF EXISTS ROLES;
DROP TABLE IF EXISTS RIGHTS;
DROP TABLE IF EXISTS GRUPPEN;
DROP TABLE IF EXISTS HELDEN;
DROP TABLE IF EXISTS HELD_VERSION;
DROP TABLE IF EXISTS PDFS;
DROP TABLE IF EXISTS RAUMPLAN_EBENE;
DROP TABLE IF EXISTS RAUMPLAN;
DROP TABLE IF EXISTS USERS;


CREATE TABLE USERS(
                    ID        SERIAL PRIMARY KEY,
                    NAME      varchar(40) NOT NULL UNIQUE,
                    PASSWORD  varchar(60),
                    TOKEN     varchar(64),
                    GRUPPE_ID BIGINT,
                    CAN_WRITE BOOLEAN     NOT NULL
);


CREATE TABLE GRUPPEN(
                      ID    SERIAL PRIMARY KEY,
                      NAME  VARCHAR(40) NOT NULL UNIQUE,
                      DATUM INTEGER
);


CREATE TABLE HELDEN(
                     ID SERIAL PRIMARY KEY,
                     USER_ID BIGINT NOT NULL,
                     NAME VARCHAR(200) NOT NULL,
                     GRUPPE_ID INTEGER NOT NULL,
                     PUBLIC BOOLEAN NOT NULL,
                     DELETED BOOLEAN NOT NULL,
                     ACTIVE BOOLEAN NOT NULL,
                     HKEY BIGINT NOT NULL,
                     LOCK_EXPIRE TIMESTAMP
);

CREATE TABLE HELD_VERSION(
                       ID SERIAL PRIMARY KEY,
                       HELDID BIGINT NOT NULL,
                       VERSION INTEGER NOT NULL,
                       CREATED_DATE TIMESTAMP  NOT NULL,
                       LAST_EVENT varchar(200),
                       CACHE_ID varchar(36) NOT NULL,
                       AP INTEGER NOT NULL
);

CREATE TABLE ROLES(
                    ID   SERIAL PRIMARY KEY,
                    NAME varchar(20) NOT NULL UNIQUE
);

CREATE TABLE RIGHTS(
                     ID   SERIAL PRIMARY KEY,
                     NAME varchar(20) NOT NULL UNIQUE
);

CREATE TABLE ROLES_TO_RIGHTS(
                                ROLE_ID INTEGER REFERENCES ROLES(ID),
                                RIGHT_ID INTEGER REFERENCES RIGHTS(ID),
                                PRIMARY KEY(ROLE_ID , RIGHT_ID)
);

CREATE TABLE ROLES_TO_USER(
                              ROLE_ID INTEGER REFERENCES ROLES(ID),
                              USER_ID INTEGER REFERENCES USERS(ID),
                              PRIMARY KEY(USER_ID, ROLE_ID)
);

CREATE TABLE PDFS
(
  ID   SERIAL PRIMARY KEY,
  NAME varchar(20) NOT NULL UNIQUE
);

CREATE TABLE PDFS_TO_USER
(
  PDF_ID INTEGER REFERENCES PDFS (ID),
  USER_ID INTEGER REFERENCES USERS (ID),
  PRIMARY KEY (PDF_ID, USER_ID)
);


INSERT INTO ROLES VALUES
    (1, 'Administrator');

INSERT INTO RIGHTS VALUES
    (1, 'CREATE_USER'),
    (2, 'VIEW_ALL'),
    (3, 'EDIT_ALL');

INSERT INTO ROLES_TO_RIGHTS VALUES
    (1, 1),
    (1,2),
    (1,3);

CREATE TABLE ZAUBERSPEICHER(
                               ID SERIAL PRIMARY KEY,
                               ZAUBER VARCHAR(100) NOT NULL,
                               HELDID BIGINT NOT NULL REFERENCES HELDEN(ID),
                               KOSTEN INTEGER NOT NULL,
                               QUALITAET INTEGER NOT NULL,
                               KOMPLEXITAET VARCHAR(1) NOT NULL,
                               SPOMOS VARCHAR(200) NOT NULL,
                               ZFW INTEGER NOT NULL,
                               MR INTEGER
);



CREATE TABLE HELD_UEBERSICHT(
    ID SERIAL PRIMARY KEY,
    HELDID BIGINT UNIQUE NOT NULL REFERENCES HELDEN(ID),
    LEP INTEGER NOT NULL,
    ASP INTEGER NOT NULL,
    WUNDEN INTEGER[] NOT NULL

);


CREATE TABLE HELD_INVENTAR(
  ID SERIAL PRIMARY KEY,
  NAME VARCHAR(200) NOT NULL,
  HELDID BIGINT NOT NULL REFERENCES HELDEN(ID),
  CONTAINER INTEGER REFERENCES HELD_INVENTAR(ID),
  GEWICHT FLOAT,
  ANZAHL INTEGER NOT NULL,
  NOTIZ VARCHAR(200)

);

CREATE TABLE HELD_GELDBOERSE(
    ID SERIAL PRIMARY KEY,
    HELD_ID BIGINT NOT NULL UNIQUE REFERENCES HELDEN(ID),
    ANZAHL INTEGER NOT NULL

);


CREATE TABLE RAUMPLAN(
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(200) NOT NULL,
    OWNER BIGINT NOT NULL REFERENCES USERS(ID),
    CREATED_DATE TIMESTAMP  NOT NULL
);

CREATE TABLE RAUMPLAN_EBENE(
  ID SERIAL PRIMARY KEY,
  NAME VARCHAR(200) NOT NULL,
  PARENT BIGINT NOT NULL REFERENCES RAUMPLAN(ID),
  BESCHREIBUNG TEXT NOT NULL
);

/* 1.1 */
CREATE TABLE ASSET (
                       ID SERIAL PRIMARY KEY,
                       GRUPPE BIGINT UNIQUE NOT NULL REFERENCES GRUPPEN(ID),
                       NAME VARCHAR(100)
);

CREATE TABLE LAGERORT(
                         ID SERIAL PRIMARY KEY,
                         NAME varchar(40) NOT NULL,
                         HELDID BIGINT NOT NULL REFERENCES HELDEN(ID),
                         NOTIZ VARCHAR(200)
);

CREATE TABLE GEGENSTAND_TO_LAGERORT(
                                       ID SERIAL PRIMARY KEY,
                                       LAGERORT BIGINT REFERENCES LAGERORT(ID),
                                       NAME VARCHAR(200) NOT NULL,
                                       AMOUNT INTEGER NOT NULL
);
