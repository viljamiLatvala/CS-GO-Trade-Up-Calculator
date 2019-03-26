# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjän on mahdollista simuloida Counter-Strike: Global Offensive pelissä tehtäviä vaihtosopimuksia. Simuloimalla näitä vaihtosopimuksia on käyttäjän mahdollista selvittää erilaisista input-yhdistelmistä syntyviä lopputulosmahdollisuuksia ja niiden todennäköisyyksiä. Näin käyttäjä voi ennustaa pelissä tapahtuvien sopimusten lopputulemaa ennen kuin tekee lopullisia ratkaisuja itse pelissä.

Koska sovelluksen tarkoitus ja toimintaperiaate, sekä käytettävä termistö voi olla vaikeasti avautuvaa itse peliä tuntemattomalle, on näitä seikkoja pyritty esittelemään [esitietoa.md](../master/dokumentaatio/esitietoa.md) -tiedostossa. Tiedostoa tullaan laajentamaan ja muokkaamaan työn edetessä.

## Käyttäjät
Sovelluksessa on vain yksi käyttäjärooli, nk. "normaali käyttäjä".

## Perusversion tarjoama toiminnallisuus
### Input-esineiden valitseminen
Käyttäjä voi valita listasta 10-esinettä joita käytetään vaihtokaupan tuotantopanoksena. Ohjelma pitää huolen että esineet täyttävät pelin asettamat vaatimukset niiden yhtäläisyydestä. Käyttäjä voi syöttää manuaalisesti esineiden kulutusasteen tai kulutustason. Aluksi ohjelma toteutetaan rajallisella esine-katalogilla ja kun sovelluslogiikka ja käyttöliittymä on saatu toimimaan, pyritään pelin koko katalogi saattamaan ohjelman käyttöön.

### Output-tulosten esittely
Kun käyttäjä on valinnut 10 esinettä tuotantopanokseksi, laskee ohjelma näiden esineiden tuottaman tuotantotuloksen ja esittää käyttäjälle mahdolliset lopputulemat, niiden todennäköisyydet sekä esineelle lasketun kulutusasteen.

## Jatkokehitysideoita
Kun ohjelman perusversio on saatu toimimaan, voidaan sitä täydentää esim. seuraavilla ominaisuuksilla
- Pelin koko esinekatalogin parsiminen verkosta sovelluksen käyttöön, käyttäjän mahdollisuus tarkistaa tiedon ajantasaisuus
- Sekä tuotantopanos, että -tulos esineiden arvon tarkastaminen Steam-kauppapaikalta
- Lasketun vaihtokaupan kannattavuuden laskeminen vaihdantahintojen perusteella
- Algoritmin kehittäminen kannattavien tuotantopanos-joukkojen löytämiseksi

## Tuntikirjanpito
Harjoitustyön tuntikirjanpito: [Tuntikirjanpito](../master/laskarit/viikko2/tuntikirjanpito.md)
