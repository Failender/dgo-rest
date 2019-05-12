DROP TABLE IF EXISTS ROLES_TO_USER;
DROP TABLE IF EXISTS ROLES_TO_RIGHTS;
DROP TABLE IF EXISTS ROLES;
DROP TABLE IF EXISTS RIGHTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS GRUPPEN;
DROP TABLE IF EXISTS HELDEN;
DROP TABLE IF EXISTS HELD_VERSION;


CREATE TABLE USERS(
                    ID SERIAL PRIMARY KEY,
                    NAME varchar(40) NOT NULL,
                    PASSWORD varchar(60),
                    TOKEN varchar(64),
                    GRUPPE_ID BIGINT,
                    CAN_WRITE BOOLEAN NOT NULL
);


CREATE TABLE GRUPPEN(
                      ID SERIAL PRIMARY KEY,
                      NAME VARCHAR(40) NOT NULL,
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
                     HKEY BIGINT NOT NULL
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
                      ID SERIAL PRIMARY KEY,
                      NAME varchar(20) NOT NULL
);

CREATE TABLE RIGHTS(
                       ID SERIAL PRIMARY KEY,
                       NAME varchar(20) NOT NULL
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


INSERT INTO ROLES VALUES
    (1, 'Administrator');

INSERT INTO RIGHTS VALUES
    (1, 'CREATE_USER');

INSERT INTO ROLES_TO_RIGHTS VALUES
    (1, 1);

