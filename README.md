# CS:GO TradeUp Calculator

Sovelluksen käyttäjien on mahdollista simuloida Counter-Strike Global Offensice-pelissä tavaroille tehtäviä Trade Up sopimuksia. Sopimuksissa pelaajan on mahdollista vaihtaa 10 omistamaansa esinettä yhteen ylemmän harvinaisuusluokan esineeseen. Saatava uusi esine pohjautuu vaihtokaupaan sijoitettaviin alemman harvinaisuusluokan esineisiin.

Sovellukselle on toteutettu luokka, joka parsii pelissä olevat esineet ja niiden tiedot niitä listaavalta [CS:GO Stash](https://csgostash.com)-sivustolta. Toistaiseksi luokkaa ei voi käyttää käyttöliittymästä, vaan sovelluksen käyttöön on muodostettu valmis tietokanta luokan avulla. Sovellus tarjoaa graafisen käyttöliittymän, mutta joitain ilmoituksia annetaan toistaiseksi konsoliin.

## Dokumentaatio
[Vaatimusmäärittely](../master/dokumentaatio/vaatimusmaarittely.md)

[Käyttöohje](../master/dokumentaatio/kayttoohje.md)

[Esitietoa](../master/dokumentaatio/esitietoa.md)

[Arkkitehtuurikuvaus](../master/dokumentaatio/arkkitehtuuri.md)

[Tuntikirjanpito](../master/dokumentaatio/tuntikirjanpito.md)

## Releaset
[Viikko 5](https://github.com/viljamiLatvala/ohjelmistotekniikka/releases/tag/viikko5)

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

### Jar-pakkaukasen luominen

Jar-pakkaus luodaan komennolla

```
mvn jacoco:package
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Checkstyle

Checkstyle-tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät tiedostosta _target/site/checkstyle.html_
