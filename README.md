# CS:GO Trade Up Calculator

Sovelluksella käyttäjien on mahdollista simuloida Counter-Strike Global Offensice-pelissä tavaroille tehtäviä Trade Up sopimuksia. Sopimuksissa pelaajan on mahdollista vaihtaa 10 omistamaansa esinettä yhteen ylemmän harvinaisuusluokan esineeseen. Saatava uusi esine sekä sen kuntoluokitus pohjautuvat vaihtokaupaan sijoitettaviin alemman harvinaisuusluokan esineisiin.

## Dokumentaatio
[Vaatimusmäärittely](../master/dokumentaatio/vaatimusmaarittely.md)

[Käyttöohje](../master/dokumentaatio/kayttoohje.md)

[Esitietoa](../master/dokumentaatio/esitietoa.md)

[Arkkitehtuurikuvaus](../master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](../master/dokumentaatio/testaus.md)

[Tuntikirjanpito](../master/dokumentaatio/tuntikirjanpito.md)

## Releaset
[Loppupalautus](https://github.com/viljamiLatvala/ohjelmistotekniikka/releases/tag/loppupalautus)

[Viikko 6](https://github.com/viljamiLatvala/ohjelmistotekniikka/releases/tag/Viikko6)

[Viikko 5](https://github.com/viljamiLatvala/ohjelmistotekniikka/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus

Testien suorittaminen:

```
mvn test
```

Testikattavuusraportin generointi:

```
mvn jacoco:report
```
Kattavuusraportti löytyy tiedostosta _target/site/jacoco/index.html_

### Jar-pakkaukasen luominen

Jar-pakkauksen generointi:

```
mvn package
```
### JavaDoc
JavaDocin generointi:
```
mvn javadoc:javadoc
```

### Checkstyle

Checkstyle-tarkistus:
```
 mvn jxr:jxr checkstyle:checkstyle
```

Checkstyle-raportti löytyy tiedostosta _target/site/checkstyle.html_
