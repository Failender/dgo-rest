DROP TABLE IF EXISTS TALENT_KATEGORIEN;
DROP TABLE IF EXISTS TALENTE;


create table TALENT_KATEGORIEN(
                                  ID SERIAL PRIMARY KEY,
                                  NAME varchar(25) NOT NULL
);

create table TALENTE(
                        ID SERIAL PRIMARY KEY,
                        NAME varchar(40) NOT NULL UNIQUE,
                        KATEGORIE_ID INTEGER NOT NULL,
                        LERNKOMPLEXITAET varchar(1) NOT NULL,
                        BE varchar(5),
                        PROBE varchar(100)
);

INSERT INTO TALENT_KATEGORIEN VALUES(1, 'Gesellschaftlich');

INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET, BE) VALUES
('Betören', 1, 'B', 'BE-2'),
('Etikette', 1, 'B', 'BE-2'),
('Gassenwissen', 1, 'B', 'BE-4'),
('Lehren', 1, 'B', ''),
('Menschenkenntnis', 1, 'B', ''),
('Schauspielerei', 1, 'B', ''),
('Schriftlicher Ausdruck', 1, 'B', ''),
('Sich verkleIDen', 1, 'B', ''),
('Überreden', 1, 'B', ''),
('Überzeugen', 1, 'B', '');

INSERT INTO TALENT_KATEGORIEN VALUES(2, 'Natur');

INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET, BE) VALUES
('Fährtensuchen', 2, 'B', ''),
('Fallen stellen', 2, 'B', ''),
('Fesseln/Entfesseln', 2, 'B', ''),
('Fischen/Angeln', 2, 'B', ''),
('Orientierung', 2, 'B', ''),
('Wettervorhersage', 2, 'B', ''),
('WildnisleBEn', 2, 'B', '');

INSERT INTO TALENT_KATEGORIEN VALUES(3, 'Wissen');

INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET, BE) VALUES
('Anatomie', 3, 'B', ''),
('Baukunst', 3, 'B', ''),
('Brett-/Kartenspiel', 3, 'B', ''),
('Geografie', 3, 'B', ''),
('Geschichtswissen', 3, 'B', ''),
('Gesteinskunde', 3, 'B', ''),
('Götter und Kulte', 3, 'B', ''),
('Heraldik', 3, 'B', ''),
('Hüttenkunde', 3, 'B', ''),
('Kriegskunst', 3, 'B', ''),
('Kryptographie', 3, 'B', ''),
('Magiekunde', 3, 'B', ''),
('Mechanik', 3, 'B', ''),
('Pflanzenkunde', 3, 'B', ''),
('Philosophie', 3, 'B', ''),
('Rechnen', 3, 'B', ''),
('Rechtskunde', 3, 'B', ''),
('Sagen und Legenden', 3, 'B', ''),
('Schätzen', 3, 'B', ''),
('Sprachenkunde', 3, 'B', ''),
('Staatskunst', 3, 'B', ''),
('Sternkunde', 3, 'B', ''),
('Tierkunde', 3, 'B', '');

INSERT INTO TALENT_KATEGORIEN VALUES(4, 'Kampf');

INSERT INTO TALENTE (ID, NAME, KATEGORIE_ID, LERNKOMPLEXITAET,BE) VALUES
(42, 'Anderthalbhänder', 4, 'E', 'BE-2'),
(43, 'Armbrust', 4, 'C', 'BE-5'),
(44, 'BElagerungswaffen', 4, 'D', ''),
(45, 'Blasrohr', 4, 'D', 'BE-5'),
(46, 'Bogen', 4, 'E', 'BE-3'),
(47, 'Diskus', 4, 'D', 'BE-2'),
(48, 'Dolche', 4, 'D', 'BE-1'),
(49, 'Fechtwaffen', 4, 'E', 'BE-1'),
(50, 'Hiebwaffen', 4, 'D', 'BE-4'),
(51, 'Infanteriewaffen', 4, 'D', 'BE-3'),
(52, 'KettenstäBE', 4, 'E', 'BE-1'),
(53, 'Kettenwaffen', 4, 'D', 'BE-3'),
(54, 'Lanzenreiten', 4, 'E', null);

INSERT INTO TALENTE (ID, NAME, KATEGORIE_ID, LERNKOMPLEXITAET,BE) VALUES
(55, 'Peitsche', 4, 'E', 'BE-1'),
(56, 'Raufen', 4, 'C', 'BE'),
(57, 'Ringen', 4, 'D', 'BE'),
(58, 'SäBEl', 4, 'D', 'BE-2'),
(59, 'Schleuder', 4, 'E', 'BE-2'),
(60, 'Schwerter', 4, 'E', 'BE-2'),
(61, 'Speere', 4, 'D', 'BE-3'),
(62, 'StäBE', 4, 'D', 'BE-2'),
(63, 'WurfBEile', 4, 'D', 'BE-2'),
(64, 'Wurfmesser', 4, 'C', 'BE-3'),
(65, 'Wurfspeere', 4, 'C', 'BE-2'),
(66, 'Zweihandflegel', 4, 'D', 'BE-3'),
(67, 'Zweihandhiebwaffen', 4, 'D', 'BE-3'),
(68, 'Zweihandschwerter/-säBEl', 4, 'D', 'BE-2');

INSERT INTO TALENT_KATEGORIEN VALUES(5, 'Handwerk');

INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET) VALUES
    ('Abrichten', 5, 'B'),
    ('Ackerbau', 5, 'B'),
    ('Alchimie', 5, 'B'),
    ('BErgbau', 5, 'B'),
    ('Bogenbau', 5, 'B'),
    ('Boote fahren', 5, 'B'),
    ('Drucker', 5, 'B'),
    ('Fahrzeug lenken', 5, 'B'),
    ('Falschspiel', 5, 'B'),
    ('Feinmechanik', 5, 'B'),
    ('FeuersteinBEarBEitung', 5, 'B'),
    ('Fleischer', 5, 'B'),
    ('GerBEr/Kürschner', 5, 'B'),
    ('Grobschmied', 5, 'B'),
    ('Glaskunst', 5, 'B'),
    ('Handel', 5, 'B'),
    ('Hauswirtschaft', 5, 'B'),
    ('Heilkunde: Gift', 5, 'B'),
    ('Heilkunde: Krankheiten', 5, 'B'),
    ('Heilkunde: Seele', 5, 'B'),
    ('Heilkunde: Wunden', 5, 'B'),
    ('HolzBEarBEitung', 5, 'B'),
    ('Kartografie', 5, 'B'),
    ('Instrumentenbauer', 5, 'B'),
    ('Kartographie', 5, 'B'),
    ('Kochen', 5, 'B');


INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET) VALUES
    ('Kristallzucht', 5, 'B'),
    ('LederarBEiten', 5, 'B'),
    ('Malen/Zeichnen', 5, 'B'),
    ('Maurer', 5, 'B'),
    ('Metallguss', 5, 'B'),
    ('Musizieren', 5, 'B'),
    ('Schlösser knacken', 5, 'B'),
    ('Schnaps Brennen', 5, 'B'),
    ('SchneIDern', 5, 'B'),
    ('Seefahrt', 5, 'B'),
    ('Seiler', 5, 'B'),
    ('Steinmetz', 5, 'B'),
    ('SteinschneIDer/Juwelier', 5, 'B'),
    ('Stellmacher', 5, 'B'),
    ('Stoffe färBEn', 5, 'B'),
    ('Tätowieren', 5, 'B'),
    ('Töpfern', 5, 'B'),
    ('Viehzucht', 5, 'B'),
    ('Webkunst', 5, 'B'),
    ('Winzer', 5, 'B'),
    ('Zimmermann', 5, 'B');

INSERT INTO TALENT_KATEGORIEN VALUES(6, 'GaBEn');
    
INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET) VALUES
    ('Ritualkenntnis: Gildenmagie', 6, 'E'),
    ('Ritualkenntnis: DruIDe', 6, 'E'),
    ('Gefahreninstinkt', 6, 'E');

INSERT INTO TALENT_KATEGORIEN VALUES(7, 'Körperlich');

INSERT INTO TALENTE (NAME, KATEGORIE_ID, LERNKOMPLEXITAET, BE) VALUES
    ('Akrobatik', 7, 'D', 'BEx2'),
    ('Athletik', 7, 'D', 'BEx2'),
    ('Fliegen', 7, 'D', 'BE'),
    ('Gaukeleien', 7, 'D', 'BEx2'),
    ('Klettern', 7, 'D', 'BEx2'),
    ('KörperBEherrschung', 7, 'D', 'BEx2'),
    ('Reiten', 7, 'D', ' BE-2'),
    ('Schleichen', 7, 'D', 'BE'),
    ('Schwimmen', 7, 'D', 'BEx2'),
    ('SelbstBEherrschung', 7, 'D', ''),
    ('Sich verstecken', 7, 'D', 'BE-2'),
    ('Singen', 7, 'D', 'BE-3'),
    ('Sinnenschärfe', 7, 'D', ''),
    ('Skifahren', 7, 'D', 'BE-2'),
    ('Stimmen imitieren', 7, 'D', 'BE-4'),
    ('Tanzen', 7, 'D', 'BEx2'),
    ('Taschendiebstahl', 7, 'D', 'BEx2'),
    ('Zechen', 7, 'D', '');
