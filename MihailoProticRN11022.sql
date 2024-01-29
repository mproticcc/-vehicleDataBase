
drop database gradskiPrevoz;
create database gradskiPrevoz;
use gradskiPrevoz;

CREATE TABLE Kategorije (
    KategorijaID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL
    
);
ALTER TABLE Kategorije ADD CONSTRAINT UQ_Kategorije_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_Kategorije_Naziv ON Kategorije(Naziv);

CREATE TABLE PotkategorijaVozila (
	PotkategorijaVozilaID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL
);
ALTER TABLE PotkategorijaVozila ADD CONSTRAINT UQ_PotkategorijaVozila_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_PotkategorijaVozila_Naziv ON PotkategorijaVozila(Naziv);

CREATE TABLE Vagon (
    VagonID INTEGER PRIMARY KEY,
    MestaZaSedenje INTEGER NOT NULL,
    MestaZaStajanje INTEGER NOT NULL
);
CREATE INDEX IDX_Vagon_MestaZaSedenje ON Vagon(MestaZaSedenje);
CREATE INDEX IDX_Vagon_MestaZaStajanje ON Vagon(MestaZaStajanje);
CREATE UNIQUE INDEX UQ_Vagon_Mesta ON Vagon(MestaZaSedenje, MestaZaStajanje);

CREATE TABLE Vozila (
    VoziloID INTEGER PRIMARY KEY,
    KategorijaID INTEGER,
    PotkategorijaVozilaID INTEGER,
    MestaZaSedenje INTEGER NOT NULL,
    MestaZaStajanje INTEGER NOT NULL,
    DatumProizvodnje DATE NOT NULL,
    DatumPoslednjeEvaluacije DATE NOT NULL,
    FOREIGN KEY (KategorijaID) REFERENCES Kategorije(KategorijaID),
    FOREIGN KEY (PotkategorijaVozilaID) REFERENCES PotkategorijaVozila(PotkategorijaVozilaID)
);
CREATE INDEX IDX_Vozila_Kategorija_Potkategorija ON Vozila(KategorijaID, PotkategorijaVozilaID);
ALTER TABLE Vozila ADD CONSTRAINT UQ_Vozila_VoziloID UNIQUE (VoziloID);

CREATE TABLE VozilaIVagoni(
    VoziloID INTEGER,
    VagonID INTEGER,
    PRIMARY KEY (VoziloID, VagonID),
    FOREIGN KEY (VoziloID) REFERENCES Vozila(VoziloID),
    FOREIGN KEY (VagonID) REFERENCES Vagon(VagonID)
);
CREATE INDEX IDX_VozilaIVagoni_VoziloID ON VozilaIVagoni(VoziloID);
CREATE INDEX IDX_VozilaIVagoni_VagonID ON VozilaIVagoni(VagonID);
CREATE UNIQUE INDEX UQ_VozilaIVagoni_VoziloVagon ON VozilaIVagoni(VoziloID, VagonID);

CREATE TABLE KategorijeVozaca (
    KategorijeVozacaID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL
);
ALTER TABLE KategorijeVozaca ADD CONSTRAINT UQ_KategorijeVozaca_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_Kategorije_Naziv ON KategorijeVozaca(Naziv);

CREATE TABLE Vozaci (
    VozacID INTEGER PRIMARY KEY,
    Ime VARCHAR(50) NOT NULL,
    Prezime VARCHAR(50) NOT NULL,
    KategorijeVozacaID INTEGER,
    FOREIGN KEY (KategorijeVozacaID) REFERENCES KategorijeVozaca(KategorijeVozacaID)
);
CREATE INDEX IDX_Vozaci_KategorijeVozaca ON Vozaci(KategorijeVozacaID);
ALTER TABLE Vozaci ADD CONSTRAINT UQ_Vozaci_VozacID UNIQUE (VozacID);

CREATE TABLE RadnoVremeVozaca (
    VozacID INTEGER,
    RadniSatiDan INTEGER,
    RadniSatiSubota INTEGER,
    RadniSatiNedelja INTEGER,
    PRIMARY KEY (VozacID),
    FOREIGN KEY (VozacID) REFERENCES Vozaci(VozacID)
);
CREATE INDEX IDX_RadnoVremeVozaca_VozacID ON RadnoVremeVozaca(VozacID);
ALTER TABLE RadnoVremeVozaca ADD CONSTRAINT UQ_RadnoVremeVozaca_VozacID UNIQUE (VozacID);

CREATE TABLE TipoviGaraza (
    TipGarazeID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL
);
ALTER TABLE TipoviGaraza ADD CONSTRAINT UQ_TipoviGaraza_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_TipoviGaraza_Naziv ON TipoviGaraza(Naziv);

CREATE TABLE Garaze (
    GarazaID INTEGER PRIMARY KEY,
    Kapacitet INTEGER NOT NULL,
    Adresa VARCHAR(50) NOT NULL,
    TipGarazeID INTEGER,
    FOREIGN KEY (TipGarazeID) REFERENCES TipoviGaraza(TipGarazeID)
);
CREATE INDEX IDX_Garaze_TipGaraze ON Garaze(TipGarazeID);
ALTER TABLE Garaze ADD CONSTRAINT UQ_Garaze_GarazaID UNIQUE (GarazaID);

CREATE TABLE VozilaUGarazi (
    VoziloUGaraziID INTEGER PRIMARY KEY,
    VoziloID INTEGER,
    GarazaID INTEGER,
    OznakaGaraze TEXT,
    MestoUGarazi INTEGER,
    FOREIGN KEY (VoziloID) REFERENCES Vozila(VoziloID),
    FOREIGN KEY (GarazaID) REFERENCES Garaze(GarazaID)
);
CREATE INDEX IDX_VozilaUGarazi_VoziloID ON VozilaUGarazi(VoziloID);
CREATE INDEX IDX_VozilaUGarazi_GarazaID ON VozilaUGarazi(GarazaID);
ALTER TABLE VozilaUGarazi ADD CONSTRAINT UQ_VozilaUGarazi_VoziloUGaraziID UNIQUE (VoziloUGaraziID);

CREATE TABLE PreduzecaZaProdajuVozila (
    PreduzeceID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    KontaktTelefon VARCHAR(50) NOT NULL,
    VebSajt TEXT
);
ALTER TABLE PreduzecaZaProdajuVozila ADD CONSTRAINT UQ_PreduzecaZaProdajuVozila_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_PreduzecaZaProdajuVozila_Naziv ON PreduzecaZaProdajuVozila(Naziv);

CREATE TABLE KupovinaVozila (
    KupovinaID INTEGER PRIMARY KEY,
    GarazaID INTEGER,
    PreduzeceID INTEGER,
    DatumKupovine DATE NOT NULL,
    BrojVozila INTEGER NOT NULL,
    PotkategorijaVozila TEXT,
    FOREIGN KEY (GarazaID) REFERENCES Garaze(GarazaID),
    FOREIGN KEY (PreduzeceID) REFERENCES PreduzecaZaProdajuVozila(PreduzeceID)
);
CREATE INDEX IDX_KupovinaVozila_GarazaID ON KupovinaVozila(GarazaID);
CREATE INDEX IDX_KupovinaVozila_PreduzeceID ON KupovinaVozila(PreduzeceID);
ALTER TABLE KupovinaVozila ADD CONSTRAINT UQ_KupovinaVozila_KupovinaID UNIQUE (KupovinaID);

CREATE TABLE Zone (
    ZonaID INTEGER PRIMARY KEY,
    Kod VARCHAR(50) NOT NULL,
    Naziv VARCHAR(50) NOT NULL
);
ALTER TABLE Zone ADD CONSTRAINT UQ_Zone_Kod UNIQUE (Kod);
CREATE INDEX IDX_Zone_Kod ON Zone(Kod);

CREATE TABLE Stajalista (
    StajalisteID INTEGER PRIMARY KEY,
    Kod VARCHAR(50) NOT NULL,
    Naziv VARCHAR(50) NOT NULL,
    TipStajalista VARCHAR(50) NOT NULL, -- Drumsko, Tramvajsko, Železničko
    ZonaID INTEGER,
    FOREIGN KEY (ZonaID) REFERENCES Zone(ZonaID)
);
ALTER TABLE Stajalista ADD CONSTRAINT UQ_Stajalista_Kod UNIQUE (Kod);
CREATE INDEX IDX_Stajalista_Kod ON Stajalista(Kod);

CREATE TABLE Linije (
    LinijaID INTEGER PRIMARY KEY,
    RedniBroj VARCHAR(50) NOT NULL,
    TipVozila VARCHAR(50) NOT NULL, -- Autobus, Tramvaj, Voz
    Smena VARCHAR(50) NOT NULL, -- Dnevna ili Noćna
    BrojSmerova INTEGER NOT NULL
);
ALTER TABLE Linije ADD CONSTRAINT UQ_Linije_RedniBroj UNIQUE (RedniBroj);
CREATE INDEX IDX_Linije_RedniBroj ON Linije(RedniBroj);

CREATE TABLE Smerovi (
    SmerID INTEGER PRIMARY KEY,
    LinijaID INTEGER,
    BrojPolazaka INTEGER NOT NULL,
    FOREIGN KEY (LinijaID) REFERENCES Linije(LinijaID)
);
CREATE INDEX IDX_Smerovi_LinijaID ON Smerovi(LinijaID);

CREATE TABLE Terminusi (
    TerminusID INTEGER PRIMARY KEY,
    StajalisteID INTEGER,
    SmerID INTEGER,
    FOREIGN KEY (StajalisteID) REFERENCES Stajalista(StajalisteID),
    FOREIGN KEY (SmerID) REFERENCES Smerovi(SmerID)
);
ALTER TABLE Terminusi ADD CONSTRAINT UQ_Terminusi_TerminusID UNIQUE (TerminusID);

CREATE TABLE PotkategorijeTerminusa (
    TerminusID INTEGER,
    PotkategorijaVozila VARCHAR(50) NOT NULL,
    PRIMARY KEY (TerminusID, PotkategorijaVozila),
    FOREIGN KEY (TerminusID) REFERENCES Terminusi(TerminusID)
);
CREATE INDEX IDX_PotkategorijeTerminusa_TerminusID ON PotkategorijeTerminusa(TerminusID);

CREATE TABLE RedVoznje (
    RedVoznjeID INTEGER PRIMARY KEY,
    SmerID INTEGER,
    VremePolaska TIME NOT NULL,
    FOREIGN KEY (SmerID) REFERENCES Smerovi(SmerID)
);
CREATE INDEX IDX_RedVoznje_SmerID ON RedVoznje(SmerID);

CREATE TABLE KategorijePeriodaVazenja (
    KategorijaID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL,
    Opis VARCHAR(50) NOT NULL
);
ALTER TABLE KategorijePeriodaVazenja ADD CONSTRAINT UQ_KategorijePeriodaVazenja_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_KategorijePeriodaVazenja_Naziv ON KategorijePeriodaVazenja(Naziv);

CREATE TABLE PeriodiVazenja (
    PeriodID INTEGER PRIMARY KEY,
    KategorijaID INTEGER,
    Cena DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (KategorijaID) REFERENCES KategorijePeriodaVazenja(KategorijaID)
);
CREATE INDEX IDX_PeriodiVazenja_KategorijaID ON PeriodiVazenja(KategorijaID);

CREATE TABLE Putnici (
    PutnikID INTEGER PRIMARY KEY,
    Ime VARCHAR(30) NOT NULL,
    Prezime VARCHAR(30) NOT NULL
);
ALTER TABLE Putnici ADD CONSTRAINT UQ_Putnici_ImePrezime UNIQUE (Ime, Prezime);
CREATE INDEX IDX_Putnici_ImePrezime ON Putnici(Ime, Prezime);

CREATE TABLE PutnickeKarte (
    KartaID INTEGER PRIMARY KEY,
    PutnikID INTEGER,
    PeriodVazenjaID INTEGER,
    DatumIzdavanja DATE NOT NULL,
    FOREIGN KEY (PutnikID) REFERENCES Putnici(PutnikID),
    FOREIGN KEY (PeriodVazenjaID) REFERENCES PeriodiVazenja(PeriodID)
);
CREATE INDEX IDX_PutnickeKarte_PutnikID ON PutnickeKarte(PutnikID);
CREATE INDEX IDX_PutnickeKarte_PeriodVazenjaID ON PutnickeKarte(PeriodVazenjaID);

CREATE TABLE PovlasceneKategorije (
    PovlascenaID INTEGER PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL,
    Popust DECIMAL(5, 2) NOT NULL
);
ALTER TABLE PovlasceneKategorije ADD CONSTRAINT UQ_PovlasceneKategorije_Naziv UNIQUE (Naziv);
CREATE INDEX IDX_PovlasceneKategorije_Naziv ON PovlasceneKategorije(Naziv);

CREATE TABLE CeneKarata (
    CenaID INTEGER PRIMARY KEY,
    KategorijaID INTEGER,
    PovlascenaID INTEGER,
    Cena DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (KategorijaID) REFERENCES KategorijePeriodaVazenja(KategorijaID),
    FOREIGN KEY (PovlascenaID) REFERENCES PovlasceneKategorije(PovlascenaID)
);
CREATE INDEX IDX_CeneKarata_KategorijaID ON CeneKarata(KategorijaID);
CREATE INDEX IDX_CeneKarata_PovlascenaID ON CeneKarata(PovlascenaID);
ALTER TABLE CeneKarata ADD CONSTRAINT UQ_CeneKarata_CenaID UNIQUE (CenaID);


CREATE TABLE DozivotneKarte (
    DozivotnaID INTEGER PRIMARY KEY,
    PovlascenaID INTEGER,
    UslovGodina INTEGER NOT NULL,
    Cena DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (PovlascenaID) REFERENCES PovlasceneKategorije(PovlascenaID)
);
CREATE INDEX IDX_DozivotneKarte_PovlascenaID ON DozivotneKarte(PovlascenaID);
ALTER TABLE DozivotneKarte ADD CONSTRAINT UQ_DozivotneKarte_DozivotnaID UNIQUE (DozivotnaID);



INSERT INTO Kategorije (KategorijaID, Naziv) VALUES 
(1, 'Autobus'),
(2, 'Tramvaj'),
(3, 'Trolejbus'),
(4, 'Elektrobus'),
(5, 'Minibus'),
(6, 'Voz');

INSERT INTO PotkategorijaVozila (PotkategorijaVozilaID, Naziv) VALUES 
(1, 'Manji gradski'),
(2, 'Veći gradski'),
(3, 'Prigradski'),
(4, 'Klasični'),
(5, 'Zglobni'),
(6, 'Manji trolejbus'),
(7, 'Veći trolejbus');

INSERT INTO Vagon (VagonID, MestaZaSedenje, MestaZaStajanje) VALUES 
(1, 40, 60),
(2, 100, 200);

INSERT INTO Vozila (VoziloID, KategorijaID, PotkategorijaVozilaID, MestaZaSedenje, MestaZaStajanje, DatumProizvodnje, DatumPoslednjeEvaluacije) VALUES 
(1, 1, 1, 30, 50, '2022-01-01', '2023-01-01'),
(2, 1, 2, 55, 100, '2022-02-01', '2023-02-01'),
(3, 1, 3, 50, 10, '2022-03-01', '2023-03-01'),
(4, 2, 4, 30, 50, '2022-04-01', '2023-04-01'),
(5, 2, 5, 55, 100, '2022-05-01', '2023-05-01'),
(6, 2, 4, 40, 60, '2022-06-01', '2023-06-01'), 
(7, 3, 6, 30, 50, '2022-07-01', '2023-07-01'), 
(8, 3, 7, 55, 100, '2022-08-01', '2023-08-01'),
(9, 4, 1, 30, 50, '2022-09-01', '2023-09-01'), 
(10, 5, 2, 15, 20, '2022-10-01', '2023-10-01'), 
(11, 6, NULL, NULL,0, '2022-11-01', '2023-11-01');

INSERT INTO VozilaIVagoni (VoziloID, VagonID) VALUES 
(4, 1),
(11, 2);

INSERT INTO KategorijeVozaca (KategorijeVozacaID, Naziv) VALUES 
    (1, 'A1'),
    (2, 'A2'),
    (3, 'A3'),
    (4, 'A4'),
    (5, 'A5'),
    (6, 'A6'),
    (7, 'T1'),
    (8, 'T2'),
    (9, 'T3'),
    (10, 'T4'),
    (11, 'V');
    
INSERT INTO Vozaci (VozacID, Ime, Prezime, KategorijeVozacaID) VALUES 
    (1, 'Marko', 'Marković', 1),
    (2, 'Ana', 'Anić', 2),
    (3, 'Jovan', 'Jovanović', 3),
    (4, 'Milica', 'Milić', 4),
    (5, 'Nikola', 'Nikolić', 5),
    (6, 'Katarina', 'Katić', 6),
    (7, 'Dragan', 'Dragić', 7),
    (8, 'Jovana', 'Jović', 8),
    (9, 'Stefan', 'Stefanović', 9),
    (10, 'Tamara', 'Tamić', 10),
    (11, 'Vladimir', 'Vladimirović', 11);

INSERT INTO RadnoVremeVozaca (VozacID, RadniSatiDan, RadniSatiSubota, RadniSatiNedelja) VALUES 
    (1, 28, 16, 24),
    (2, 8, 6, 4),
    (3, 8, 6, 4),
    (4, 8, 6, 4),
    (5, 8, 6, 4),
    (6, 8, 6, 4),
    (7, 8, 6, 4),
    (8, 8, 6, 4),
    (9, 8, 6, 4),
    (10, 8, 6, 4),
    (11, 8, 6, 4);

INSERT INTO TipoviGaraza (TipGarazeID, Naziv) VALUES 
    (1, 'Autobusi i Minibusevi'),
    (2, 'Elektrobusevi'),
    (3, 'Trolejbusi'),
    (4, 'Tramvaji'),
    (5, 'Vozovi'),
    (6, 'Automobili'),
    (7, 'Kamioni'),
    (8, 'Motocikli'),
    (9, 'Bicikli'),
    (10, 'Čamci');

INSERT INTO Garaze (GarazaID, Kapacitet, Adresa, TipGarazeID) VALUES 
    (1, 100, 'Adresa1', 1),
    (2, 80, 'Adresa2', 2),
    (3, 120, 'Adresa3', 3),
    (4, 150, 'Adresa4', 4),
    (5, 200, 'Adresa5', 5),
    (6, 150, 'Adresa6', 1),
    (7, 120, 'Adresa7', 2),
    (8, 100, 'Adresa8', 3),
    (9, 80, 'Adresa9', 4),
    (10, 200, 'Adresa10', 5);

INSERT INTO VozilaUGarazi (VoziloUGaraziID, VoziloID, GarazaID, OznakaGaraze, MestoUGarazi) VALUES 
    (1, 1, 1, 'A', 1),
    (2, 2, 2, 'B', 2),
    (3, 3, 3, 'C', 3),
    (4, 4, 4, 'D', 4),
    (5, 5, 5, 'E', 5),
    (6, 6, 6, 'F', 6),
    (7, 7, 7, 'G', 7),
    (8, 8, 8, 'H', 8),
    (9, 9, 9, 'I', 9),
    (10, 10, 10, 'J', 10);

INSERT INTO PreduzecaZaProdajuVozila (PreduzeceID, Naziv, Email, KontaktTelefon, VebSajt) VALUES 
    (1, 'Preduzeće1', 'info@preduzece1.com', '123-456-789', 'www.preduzece1.com'),
    (2, 'Preduzeće2', 'info@preduzece2.com', '987-654-321', 'www.preduzece2.com'),
    (3, 'Preduzeće3', 'info@preduzece3.com', '111-222-333', 'www.preduzece3.com'),
    (4, 'Preduzeće4', 'info@preduzece4.com', '444-555-666', 'www.preduzece4.com'),
    (5, 'Preduzeće5', 'info@preduzece5.com', '777-888-999', 'www.preduzece5.com'),
    (6, 'Preduzeće6', 'info@preduzece6.com', '000-111-222', 'www.preduzece6.com'),
    (7, 'Preduzeće7', 'info@preduzece7.com', '333-444-555', 'www.preduzece7.com'),
    (8, 'Preduzeće8', 'info@preduzece8.com', '666-777-888', 'www.preduzece8.com'),
    (9, 'Preduzeće9', 'info@preduzece9.com', '999-000-111', 'www.preduzece9.com'),
    (10, 'Preduzeće10', 'info@preduzece10.com', '222-333-444', 'www.preduzece10.com');

INSERT INTO KupovinaVozila (KupovinaID, GarazaID, PreduzeceID, DatumKupovine, BrojVozila, PotkategorijaVozila) VALUES 
    (1, 1, 1, '2023-01-15', 5, 'Autobusi'),
    (2, 2, 2, '2023-02-20', 3, 'Elektrobusevi'),
    (3, 3, 3, '2023-03-25', 2, 'Trolejbusi'),
    (4, 4, 4, '2023-04-10', 4, 'Tramvaji'),
    (5, 5, 5, '2023-05-12', 3, 'Vozovi'),
    (6, 6, 6, '2023-06-18', 1, 'Automobili'),
    (7, 7, 7, '2023-07-21', 3, 'Kamioni'),
    (8, 8, 8, '2023-08-30', 2, 'Motocikli'),
    (9, 9, 9, '2023-09-02', 5, 'Bicikli'),
    (10, 10, 10, '2023-10-05', 4, 'Čamci');

INSERT INTO Zone (ZonaID, Kod, Naziv) VALUES 
    (1, 'Z1', 'Zona 1'),
    (2, 'Z2', 'Zona 2'),
    (3, 'Z3', 'Zona 3'),
    (4, 'Z4', 'Zona 4'),
    (5, 'Z5', 'Zona 5'),
    (6, 'Z6', 'Zona 6'),
    (7, 'Z7', 'Zona 7'),
    (8, 'Z8', 'Zona 8'),
    (9, 'Z9', 'Zona 9'),
    (10, 'Z10', 'Zona 10');

INSERT INTO Stajalista (StajalisteID, Kod, Naziv, TipStajalista, ZonaID) VALUES 
    (1, 'S1', 'Stajalište 1', 'Drumsko', 1),
    (2, 'S2', 'Stajalište 2', 'Tramvajsko', 2),
    (3, 'S3', 'Stajalište 3', 'Železničko', 3),
    (4, 'S4', 'Stajalište 4', 'Drumsko', 4),
    (5, 'S5', 'Stajalište 5', 'Tramvajsko', 5),
    (6, 'S6', 'Stajalište 6', 'Železničko', 6),
    (7, 'S7', 'Stajalište 7', 'Drumsko', 7),
    (8, 'S8', 'Stajalište 8', 'Tramvajsko', 8),
    (9, 'S9', 'Stajalište 9', 'Železničko', 9),
    (10, 'S10', 'Stajalište 10', 'Drumsko', 10);

INSERT INTO Linije (LinijaID, RedniBroj, TipVozila, Smena, BrojSmerova) VALUES 
    (1, '21A', 'Autobus', 'Dnevna', 2),
    (2, '5T', 'Tramvaj', 'Noćna', 3),
    (3, '15B', 'Autobus', 'Dnevna', 2),
    (4, '8M', 'Tramvaj', 'Noćna', 3),
    (5, '2A', 'Autobus', 'Dnevna', 2),
    (6, '10T', 'Tramvaj', 'Noćna', 3),
    (7, '11C', 'Autobus', 'Dnevna', 2),
    (8, '3S', 'Tramvaj', 'Noćna', 3),
    (9, '9A', 'Autobus', 'Dnevna', 2),
    (10, '6T', 'Tramvaj', 'Noćna', 3);

INSERT INTO Smerovi (SmerID, LinijaID, BrojPolazaka) VALUES 
    (1, 1, 5),
    (2, 2, 8),
    (3, 3, 7),
    (4, 4, 6),
    (5, 5, 5),
    (6, 6, 9),
    (7, 7, 6),
    (8, 8, 8),
    (9, 9, 7),
    (10, 10, 10);

INSERT INTO Terminusi (TerminusID, StajalisteID, SmerID) VALUES 
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 2),
    (4, 1, 1),
    (5, 2, 1),
    (6, 3, 2),
    (7, 1, 2),
    (8, 2, 2),
    (9, 3, 1),
    (10, 1, 1),
    (11, 2, 1),
    (12, 3, 2),
    (13, 1, 2);

INSERT INTO PotkategorijeTerminusa (TerminusID, PotkategorijaVozila) VALUES 
    (1, 'A1'),
    (2, 'A2'),
    (3, 'V'),
    (4, 'A1'),
    (5, 'A2'),
    (6, 'V'),
    (7, 'A1'),
    (8, 'A2'),
    (9, 'V'),
    (10, 'A1'),
    (11, 'A2'),
    (12, 'V'),
    (13, 'A1');

INSERT INTO RedVoznje (RedVoznjeID, SmerID, VremePolaska) VALUES 
    (1, 1, '10:00:00'),
    (2, 1, '11:30:00'),
    (3, 2, '18:00:00'),
    (4, 2, '19:30:00'),
    (5, 1, '14:00:00'),
    (6, 1, '15:30:00'),
    (7, 2, '20:00:00'),
    (8, 2, '21:30:00'),
    (9, 1, '16:00:00'),
    (10, 1, '17:30:00');

INSERT INTO KategorijePeriodaVazenja (KategorijaID, Naziv, Opis) VALUES 
    (1, 'Dnevna karta', 'Važi za jedan dan'),
    (2, 'Noćna karta', 'Važi za noćnu smenu'),
    (3, 'Nedeljna karta', 'Važi za celu nedelju'),
    (4, 'Mesečna karta', 'Važi za ceo mesec'),
    (5, 'Godišnja karta', 'Važi za celu godinu');


INSERT INTO PeriodiVazenja (PeriodID, KategorijaID, Cena) VALUES 
    (1, 1, 200.00),
    (2, 2, 250.00),
    (3, 3, 300.00),
    (4, 4, 500.00),
    (5, 5, 1000.00);
    
INSERT INTO Putnici (PutnikID, Ime, Prezime) VALUES
    (1, 'Marko', 'Markovic'),
    (2, 'Ana', 'Ivanovic'),
    (3, 'Nikola', 'Nikolic'),
    (4, 'Jovana', 'Jovanovic'),
    (5, 'Stefan', 'Stefanovic'),
    (6, 'Tamara', 'Tamaric'),
    (7, 'Aleksandar', 'Aleksandric'),
    (8, 'Milica', 'Milicic'),
    (9, 'Dusan', 'Dusanovic'),
    (10, 'Jelena', 'Jelic');
    
INSERT INTO PutnickeKarte (KartaID, PutnikID, PeriodVazenjaID, DatumIzdavanja) VALUES
    (1, 1, 1, '2024-01-20'),
    (2, 2, 2, '2024-01-21'),
    (3, 3, 3, '2024-01-22'),
    (4, 4, 4, '2024-01-23'),
    (5, 5, 5, '2024-01-24'),
    (6, 6, 1, '2024-01-25'),
    (7, 7, 2, '2024-01-26'),
    (8, 8, 3, '2024-01-27'),
    (9, 9, 4, '2024-01-28'),
    (10, 10, 5, '2024-01-29');

INSERT INTO PovlasceneKategorije (PovlascenaID, Naziv, Popust) VALUES 
    (1, 'Vozači', 0.20),
    (2, 'DojiLja', 0.30),
    (3, 'Penzioneri', 0.25),
    (4, 'Studenti', 0.35),
    (5, 'Učenici', 0.40),
    (6, 'Nezaposleni', 0.30),
    (7, 'Osobe sa invaliditetom', 0.50),
    (8, 'Lica sa vojnom legitimacijom', 0.40),
    (9, 'Deca do 7 godina', 0.60),
    (10, 'Deca od 7 do 12 godina', 0.50),
    (11, 'Trudnice', 0.35);

INSERT INTO CeneKarata (CenaID,KategorijaID, PovlascenaID, Cena) VALUES 
    (1,1, 1, 160.00),
    (2,2, 2, 175.00),
    (3,3, 3, 180.00),
    (4,4, 4, 325.00),
    (5,5, 5, 600.00),
    (6,1, 6, 80.00),
    (7,2, 7, 120.00),
    (8,3, 8, 200.00),
    (9,4, 9, 400.00),
    (10,5, 10, 400.00);

INSERT INTO DozivotneKarte (DozivotnaID, PovlascenaID, UslovGodina, Cena) VALUES 
    (1, 1, 15, 3000.00),
    (2, 2, 26, 2800.00),
    (3, 3, 17, 2500.00),
    (4, 4, 30, 2200.00),
    (5, 5, 39, 2000.00),
    (6, 6, 20, 1800.00),
    (7, 7, 29, 1600.00),
    (8, 8, 42, 1400.00),
    (9, 9, 53, 1200.00),
    (10, 10, 64, 1000.00);

-- 1. Izračunati koliko ukupno mesta postoji u gradskom prevozu.
--  Izračunati takođe koliko je mesta za stajanje i koliko je mesta za sedenje.
    
    SELECT SUM(V.MestaZaSedenje) + SUM(V.MestaZaStajanje) + SUM(VG.MestaZaStajanje) + SUM(VG.MestaZaSedenje) AS UkupnoMesta,
    SUM(V.MestaZaSedenje) + SUM(VG.MestaZaSedenje) AS MestaZaSedenje,
    SUM(V.MestaZaStajanje) +  SUM(VG.MestaZaStajanje) AS MestaZaStajanje
	FROM Vozila V LEFT JOIN VozilaIVagoni VV ON V.VoziloID = VV.VoziloID
	LEFT JOIN Vagon VG ON VV.VagonID = VG.VagonID;


-- 2. Izračunati koliko je vozila po potkategoriji bilo uspešno evaluirano bar jednom u toku 2023.godine.
	SELECT pv.Naziv AS PotkategorijaVozila, COUNT(v.VoziloID) AS BrojEvaluiranihVozila FROM PotkategorijaVozila pv
	LEFT JOIN Vozila v ON pv.PotkategorijaVozilaID = v.PotkategorijaVozilaID 
	AND v.DatumPoslednjeEvaluacije BETWEEN '2023-01-01' AND '2023-12-31' GROUP BY pv.Naziv;

-- 3. Za svaku kategoriju vozača izračunati koliko vozača pripada toj kategoriji.
	SELECT kv.Naziv AS KategorijaVozaca, COUNT(v.VozacID) AS BrojVozaca FROM KategorijeVozaca kv 
	LEFT JOIN Vozaci v ON kv.KategorijeVozacaID = v.KategorijeVozacaID GROUP BY kv.Naziv;

-- 4. Izračunati koliko vozača radi više od 40h nedeljno.
	SELECT COUNT(*) AS BrojVozacaPreko40Sati FROM RadnoVremeVozaca rv 
	WHERE (rv.RadniSatiDan + rv.RadniSatiSubota + rv.RadniSatiNedelja) > 40;

-- 5. Za svaku garažu sa klasičnim tramvajima ispisati da li je moguće da se svakom klasičnom tramvaju u njoj doda po vagon.
	SELECT G.GarazaID,G.Adresa AS AdresaGaraze,G.Kapacitet AS KapacitetGaraze,
    COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 1 THEN V.VoziloID END) AS BrojKlasicnihTramvaja,
    COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 2 THEN V.VoziloID END) AS BrojVagona,
    COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID IN (1, 2) THEN V.VoziloID END) AS UkupnoTramvajaIVagona,
    COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 2 THEN V.VoziloID END) >= 
    COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 1 THEN V.VoziloID END) AS BrojVagonaVeceIliJednakoTramvajima,
    CASE WHEN 
        COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 1 THEN V.VoziloID END) > 0 AND
        COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 2 THEN V.VoziloID END) > 0 AND
        COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 2 THEN V.VoziloID END) >= 
        COUNT(DISTINCT CASE WHEN V.PotkategorijaVozilaID = 1 THEN V.VoziloID END)
        THEN 'Da' ELSE 'Ne' END AS MogucnostDodavanjaVagona
	FROM Garaze G JOIN VozilaUGarazi VUG ON G.GarazaID = VUG.GarazaID JOIN Vozila V ON VUG.VoziloID = V.VoziloID  
	WHERE V.PotkategorijaVozilaID IN (1, 2) 
	GROUP BY G.GarazaID, G.Adresa, G.Kapacitet;
    
-- 6. Za svaku garažu izračunati od kog prodavca bi bilo najbolje kupiti nova vozila.
--    Uzeti da garaža želi da prilikom kupovine ispuni sva prazna mesta.
	SELECT G.GarazaID, G.Adresa AS AdresaGaraze, G.Kapacitet AS KapacitetGaraze,
		PV.PreduzeceID, PV.Naziv AS NazivProdavca, PV.Email AS KontaktEmail, PV.KontaktTelefon,
		(G.Kapacitet - COALESCE(COUNT(VUG.VoziloID), 0)) AS BrojPraznihMesta
	FROM Garaze G JOIN PreduzecaZaProdajuVozila PV ON 1=1
	LEFT JOIN VozilaUGarazi VUG ON G.GarazaID = VUG.GarazaID
	LEFT JOIN Vozila V ON VUG.VoziloID = V.VoziloID GROUP BY G.GarazaID, PV.PreduzeceID
	HAVING G.Kapacitet - COALESCE(COUNT(VUG.VoziloID), 0) > 0
	ORDER BY G.GarazaID, (G.Kapacitet - COALESCE(COUNT(VUG.VoziloID), 0)) DESC;

-- 7. Izračunati koliko linija sadrži koliko smerova.
	SELECT L.LinijaID, COUNT(S.SmerID) AS BrojSmerova FROM Linije L
	LEFT JOIN Smerovi S ON L.LinijaID = S.LinijaID GROUP BY L.LinijaID ORDER BY L.LinijaID;

-- 8. Ispisati redni broj linije sa najviše i liniju sa najmanje polazaka po smeru
	SELECT L.LinijaID, L.RedniBroj AS RedniBrojLinije, MAX(S.BrojPolazaka) AS NajvisePolazaka,
		MIN(S.BrojPolazaka) AS NajmanjePolazaka FROM Linije L JOIN Smerovi S ON L.LinijaID = S.LinijaID
	GROUP BY L.LinijaID, L.RedniBroj ORDER BY L.LinijaID;

-- 9. Dojiljama ćemo smatrati majke koje su bile trudne pre najviše 3 godine. 
--    Izračunati koliko će biti dojilja za godinu dana.

	SELECT COUNT(DISTINCT P.PutnikID) AS BrojDojilja FROM Putnici P JOIN PutnickeKarte PK ON P.PutnikID = PK.PutnikID
	JOIN PeriodiVazenja PV ON PK.PeriodVazenjaID = PV.PeriodID
	JOIN PovlasceneKategorije PKat ON PV.KategorijaID = PKat.PovlascenaID
	WHERE YEAR(PK.DatumIzdavanja) >= YEAR(CURDATE()) - 1 AND PKat.Naziv = 'DojiLja';

-- 10. Ispisati sve putnike koji ostvaruju pravo na doživotnu kartu, a nisu ga iskoristili. 
	SELECT Putnici.* FROM Putnici JOIN DozivotneKarte ON Putnici.PutnikID = DozivotneKarte.PovlascenaID
	LEFT JOIN PutnickeKarte ON Putnici.PutnikID = PutnickeKarte.PutnikID AND PutnickeKarte.PeriodVazenjaID = DozivotneKarte.DozivotnaID
	WHERE DozivotneKarte.UslovGodina <= YEAR(CURRENT_DATE) - 10
	AND (PutnickeKarte.KartaID IS NULL OR PutnickeKarte.DatumIzdavanja < CURRENT_DATE);
