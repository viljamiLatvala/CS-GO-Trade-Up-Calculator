
# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjän on mahdollista simuloida Counter-Strike: Global Offensive pelissä tehtäviä vaihtosopimuksia. Simuloimalla näitä vaihtosopimuksia on käyttäjän mahdollista selvittää erilaisista yhdistelmistä syntyviä lopputulosvaihtoehtoja ja niiden todennäköisyyksiä. Näin käyttäjä voi ennustaa pelissä tapahtuvien sopimusten lopputulemaa ennen kuin tekee lopullisia ratkaisuja itse pelissä.

Koska sovelluksen tarkoitus ja toimintaperiaate, sekä käytettävä termistö voi olla vaikeasti avautuvaa itse peliä tuntemattomalle, on näitä seikkoja pyritty esittelemään [esitietoa.md](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/esitietoa.md) -tiedostossa.

## Käyttäjät
Sovelluksessa on vain yksi käyttäjärooli, nk. "normaali käyttäjä".

## Perusversion tarjoama toiminnallisuus
### Input-esineiden valitseminen
- Käyttäjä voi valita listasta 10-esinettä joita käytetään vaihtokaupan tuotantopanoksena.
- Ohjelma pitää huolen että esineet täyttävät pelin asettamat vaatimukset niiden yhtäläisyydestä.
- Käyttäjä voi syöttää manuaalisesti esineiden kulutusasteen.
- Ohjelma tarjoaa tietokannan johon on koottu pelissä esiintyvät esineet ja niiden sovelluksen kannalta oleelliset tiedot.

### Output-tulosten esittely
Jokaisen lisätyn esineen jälkeen sovellus:
- laskee näiden esineiden tuottaman tuotantotuloksen
- esittää käyttäjälle diagrammin mahdollisista lopputulemista
- Jokaista mahdollista lopputulema-esinettä kohden esitetään niiden todennäköisyyys sekä esineelle laskettu kulutusaste.

## Jatkokehitysideoita
- Verkosta tietoa hakeva luokka, jolla käyttäjä voi luoda uuden, ajantasaisen tietokannan.
- Sekä tuotantopanos, että -tulos esineiden arvon tarkastaminen Steam-kauppapaikalta
- Lasketun vaihtokaupan kannattavuuden laskeminen vaihdantahintojen perusteella
