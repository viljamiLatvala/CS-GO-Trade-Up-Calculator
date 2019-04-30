# Testausdokumentti

## Yksikkö ja integraatiotestaus

### Sovelluslogiikka
[ItemService](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/src/main/java/csgotuc/domain/ItemService.java)-luokkaa testataan [ItemServiceTest](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/src/test/java/ItemServiceTest.java) -luokkaan toteutetuilla testeillä joilla simuloidaan käyttöliittymästä ItemServiceltä kutsuttavia toimintoja. Testit käyttävät testaamiseen tarkoitettua test_database.db -tietokantatiedostoa 
[Item] https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/src/test/java/Item.java -luokkan equal() ja getCondition()-metodeja sekä parametrina annetusta Item-oliosta syväkopion luovaa konstruktoria on testattu yksikkötesteillä luokassa [ItemTest](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/src/test/java/ItemTest.java).
### SQLItemDao
Database Access Object-luokkaa [SQLItemDao](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/src/main/java/csgotuc/dao/SQLItemDao.java) on testattu käyttämällä testaamiseen tarkoitettua test_database.db -tietokantatiedostoa. Muutamia toimintoja, joita ItemServiceTest ei kata, on testattu erikseen [SQLItemDaoTest](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/src/test/java/SQLItemDaoTest.java) -luokassa.

### Testikattavuus
Testauksen rivikattavuus on 87% ja haarautumakattavuus 94%.

![Testikattavuus](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/testikattavuus.png )

Testikattavuuteen ei ole laskettu mukaan käyttöliittymäluokkaa.

## Järjestelmätestaus
Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asentaminen
Sovelluksen lataaminen, käynnistäminen ja käyttäminen on testattu Linux- ja Windows-ympäristöissä. Sovellus on testattu sekä suoritustiedostossa sijaitsevan database.db-tiedoston ollessa läsnä sekä sen poissaollessa, jolloin sovellus lataa tiedoston tästä GitHub-repositoriosta.

### Toiminnallisuus
Toiminnallisuutta on testattu käymällä läpi määrittelydokumentin määrittelemät toiminnallisuudet sekä käyttöohjeen opastamat vaiheet. Sovelluksen antamia tuloksia on verrattu [sovelluksen toiminnallisuuden pohjana olleen kuvauksen](https://www.reddit.com/r/GlobalOffensiveTrade/comments/7wam74/psa_xcobalts_complete_guide_to_the_trade_up/?depth=1) Trade Up-sopimuksen toiminnasta kuvailemaan kaavaan sopimuksen lopputuloksen muodostamiseksi. 
