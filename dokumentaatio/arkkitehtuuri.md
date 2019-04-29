# Arkkitehtuurikuvaus

## Rakenne
Ohjelma käytettä seuraavanlaista, kolmitasoista pakkausrakennetta:

![Pakkausrakenne](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/pakkausrakenne.png )

## Käyttöliittymä
Sovelluksen käyttöliittymä toteutetaan yhtenä Scene-oliona, ja se koostuu kolmesta pääelementistä: Esineiden valintalistana toimiva ListView, Valittuja esineitä esittelevä TilePane sekä Trade Up-sopimuksen lopputulemaa esittävä PieChart. Kun käyttäjä valikoi ja poistaa esineitä, renderöidään käyttöliittymäelementit uudelleen kuvaamaan uutta tilannetta. Kolmen pääelementin lisäksi ruudulla näytetään välillä Group-oliolla toteutettua esikatseluikkunaa.

Työssä käyttölittymä on pyritty eristämään mahdollisimman tehokkaasti sovelluslogiikasta siten, että käyttöliittymäluokka csgotuc.ui.Gui kutsuu sovelluslogiikkaolio ItemServicen metodeja.

## Sovelluslogiikka
Sovellus sisätää datamallin Item, joillaa kuvataan Counter-Strike: Global Offensive-pelissä esiintyviä esineitä, joita sovelluksessa asetetaan vaihtosopimuksen panokseksi ja saadaan sen lopputulemana. 

Sovelluksen toiminnallisesta kokonaisuudesta vastaa käyttöliittymän luokasta ItemService luoma olio. Luokka tarjoaa käyttöliittymälle Listausta tietokantaan tallennetuista esineistä Item-olioina, sekä Trade Up-sopimusten lopputuloksia käyttöliittymästä asetettujen tuotantopanosten perusteella, joiden pohjalta käyttöliittymä kuvantaa sopimuksen tilaa.

ItemService käyttää ItemDao-rajapinnan toteuttavaa DAO-luokkaa esineiden hakemiseen tietokannasta.

### Luokkakaavio
![Luokkakaavio](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/luokkakaavio.png )

_*PÄIVITÄ*_

## Tietojen tallentaminen

Sovellus säilyttää tietoa esineissä SQL-tietokannassa. Tietokannan rakenne ilmenee seuraavasta CREATE TABLE-lauseesta:
```
CREATE TABLE Item (
    id integer PRIMARY KEY,
    name varchar(200),
    design varchar(200),
    weapon varcar(200),
    collection varchar(200),
    grade integer,
    minwear double,
    maxwear double,
    image blob
);
```

## Päätoiminnallisuus
### Esineiden valitseminen tuotantopanokseksi ja tuotantotuloksen laskeminen
![Sekvenssikaavio](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/tradeupCalculation.png )

Kun käyttäjä valitsee käyttöliittymässä olevasta listasta esineen tuotantopanokseksi vaihtotapahtumaan, kutsuu käyttöliittymä ItemServicen addToInput-metodia. Tämän jälkeen käyttöliittymä muodostaa uuden piirakkadiagrammin käyttäjälle kutsumalla omaa metodiaan formChart. FormChart kutsuu jälleen ItemServiceä, joka lähettää säilyttämänsä input-esineet ItemDao:lle, joka palauttaa niitä vastaavat mahdolliset tuotantotulokset. ItemService välittää tämän listan eteenpäin käyttöliittymälle, joka piirtää kuvista piirakkadiagrammin käyttäjän nähtäväksi 

_*LISÄÄ KUNTOISUUDEN LASKEMINEN*_

## Sovellukseen jääneet rakenteelliset heikkoudet
