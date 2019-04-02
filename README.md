# CS:GO TradeUp Calculator

Sovelluksen käyttäjien on mahdollista simuloida Counter-Strike Global Offensice-pelissä tavaroille tehtäviä Trade Up sopimuksia. Sopimuksissa pelaajan on mahdollista vaihtaa 10 omistamaansa esinettä yhteen ylemmän harvinaisuusluokan esineeseen. Saatava uusi esine pohjautuu vaihtokaupaan sijoitettaviin alemman harvinaisuusluokan esineisiin.

Tällä hetkellä sovellus lukee käyttöönsä esineet /src/main/resources sijainnissa olevasta .csv tiedostosta. Työhön toteutettu yksinkertainen tekstikäyttöliittymä mahdollistaa esineiden lisäämisen sopimuksen inputtiin antamalla edineen ID-numeron, joka on siis käytännössä esineen rivinumero .csv-tiedostossa nollasta alkaen. Tämän jälkeen ohjelma suorittaa laskun ja palauttaa listan esineistä joista yhden käyttäjän on mahdollista saada vaihtokaupassa. Esineen yhteydessä on ilmoitettu myös todennäköisyys tuon esineen saamiselle.

## Dokumentaatio
[Vaatimusmäärittely](../master/dokumentaatio/vaatimusmaarittely.md)

[Esitietoa](../master/dokumentaatio/esitietoa.md)

[Tuntikirjanpito](../master/dokumentaatio/tuntikirjanpito.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_
