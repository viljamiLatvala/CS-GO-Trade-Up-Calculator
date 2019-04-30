# Käyttöohje

Lataa viimeisimmän releasen .jar-tiedosto sekä .db-tiedosto: [Viikon 6 deadline-release](https://github.com/viljamiLatvala/ohjelmistotekniikka/releases/tag/Viikko6)

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar CSGOTradeUpCalculator.jar
```

Varmista että jar-pakkauksen kanssa samassa kansiossa sijaitsee tiedosto database.db

## Näkymä
Sovelluksen käynnistyessä luodaan käyttäjälle seuraavanlainen näkymä:

![yleisnäkymä](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/nakyma1.PNG)

Näkymä jakautuu kolmeen osaan. Tarkastellaan näitä osia vasemmalta oikealla

## Esinelistaus - käytettävien esineiden valitseminen

![esinelistaus](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/nakyma2.png)

Vasemmanpuolimmaisin osa on lista, jossa listataan kaikki pelissä olevat esineet, joita on mahdollista käyttää pelin Trade Up-sopimuksessa. Asettamalla hiiren osoittimen listan elementin päälle, näkee käyttäjä kuvan esineestä. Kaksoisnapsauttamalla elementtiä lisätään esine käyttäjän input-valintaan.

## Input-valinta - sopimukseen valittujen esineiden listaus

![input-valinta](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/nakyma3.png)

Keskimmäinen paneeli sisältää kymmenen ruutua, jotka kuvastavat Trade Up-sopimukseen valittuja esineitä. Valitsemalla esineen vasemman puolen listasta, siirtyy esine keskimmäisen paneelin vapaaseen ruutuun, osaksi sopimuksessa käytettävää työpanosta. Esineen kunnon määrittävän FloatValuen voi määrittää oikeasta hiirennapista avautuvasta valikosta. Myös esineen poistaminen valintaruudukosta onnistuu samasta valikosta.

![floatValuen asettamienn](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/nakyma4.png)

## Output-esittely - sopimuksesta saatavien mahdollisten tulosten esittely

![output-esittely](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/nakyma5.png)

Oikeanpuoleisin paneeli on sovelluksen käynnistyessä tyhjä. Kun käyttäjä valitsee ensimmäisen esineen esinelistaukseen, piirtyy paneeliin piirakkadiagrammi (Kunhan valitulla esineellä on olemassa korkeamman luokituksen omaavia "vanhempia"). Piirakkadiagrammi kuvastaa sopimuksen lopputulemaa. Koska itse pelissä sopimuksen lopputulema arvotaan tuotantopanoksen esineiden perusteella, esittää piirakkadiagrammi kunkin esineen todennäköisyyttä tulla lopputulemaksi. Diagrammi piirretään uudelleen jokaisen esineen valitsemisen tai poistamisen jälkeen. Asettamalla hiiren piirakkadiagrammin lohkon kohdalle, näytetään käyttäjälle esineen kuva ja tietoa siitä. Näytettävään tietoon kuuluu paitsi esineen nimi ja sen todennäköisyys tulla sopimuksen lopputulemaksi, myös sille input-esineiden perusteella muodostettu floatValue sekä siihen perustuva sanallinen kuntoluokitus.
