DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS GRUPPEN;
DROP TABLE IF EXISTS HELDEN;
DROP TABLE IF EXISTS HELD_VERSION;


CREATE TABLE USERS(
                    ID SERIAL PRIMARY KEY,
                    NAME varchar(40) NOT NULL,
                    PASSWORD varchar(60),
                    TOKEN varchar(64),
                    GRUPPE_ID INTEGER NOT NULL,
                    CAN_WRITE BOOLEAN NOT NULL
);


CREATE TABLE GRUPPEN(
                      ID SERIAL PRIMARY KEY,
                      NAME VARCHAR(40) NOT NULL,
                      DATUM INTEGER
);


CREATE TABLE HELDEN(
                     ID SERIAL PRIMARY KEY,
                     USER_ID INTEGER NOT NULL,
                     NAME VARCHAR(200) NOT NULL,
                     GRUPPE_ID INTEGER NOT NULL,
                     PUBLIC BOOLEAN NOT NULL,
                     DELETED BOOLEAN NOT NULL,
                     ACTIVE BOOLEAN NOT NULL,
                     HKEY INTEGER NOT NULL
);

CREATE TABLE HELD_VERSION(
                           ID SERIAL PRIMARY KEY,
                           HELDID INTEGER NOT NULL,
                           VERSION INTEGER NOT NULL,
                           CREATED_DATE TIMESTAMP  NOT NULL,
                           LAST_EVENT varchar(200),
                           CACHE_ID varchar(36) NOT NULL,
                           AP INTEGER NOT NULL
);