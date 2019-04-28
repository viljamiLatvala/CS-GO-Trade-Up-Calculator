# Käyttöohje

Lataa tiedosto *Linkki releaseen*

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar *pakkauksen nimi*.jar
```

## Näkymä
Sovelluksen käynnistyessä luodaan käyttäjälle seuraavanlainen näkymä:
*nakyma1.png*
Näkymä jakautuu kolmeen osaan. Tarkastellaan näitä osia vasemmalta oikealla

## Esinelistaus - käytettävien esineiden valitseminen
*nakyma2.png*
Vasemmanpuolimmaisin osa on lista, jossa listataan kaikki pelissä olevat esineet, joita on mahdollista käyttää pelin Trade Up-sopimuksessa. Asettamalla hiiren osoittimen listan elementin päälle, näkee käyttäjä kuvan esineestä (tällähetkellä referenssikuvan). Kaksoisnapsauttamalla elementtiä, lisätään esine käyttäjän input-valintaan.
## Input-valinta - sopimukseen valittujen esineiden listaus
*nakyma3.png*
Keskimmäinen paneeli sisältää kymmenen ruutua, jotka kuvastavat Trade Up-sopimukseen valittuja esineitä. Valitsemalla esineen vasemman puolen listasta, siirtyy esine keskimmäisen paneelin vapaaseen ruutuun, osaksi sopimuksessa käytettävää työpanosta. Esineen voi poistaa valinnasta oikeasta hiirennapista avautuvasta valikosta.

## Output-esittely - sopimuksesta saatavien mahdollisten tulosten esittely
*nakyma4.png*
Oikeanpuoleisin paneeli on sovelluksen käynnistyessä tyhjä. Kun käyttäjä valitsee ensimmäisen esineen esinelistaukseen, piirtyy paneeliin piirakkadiagrammi (Kunhan valitulla esineellä on olemassa korkeamman luokituksen omaavia "vanhempia"). Piirakkadiagrammi kuvastaa sopimuksen lopputulemaa. Koska itse pelissä sopimuksen lopputulema arvotaan tuotantopanoksen esineiden perusteella, esittää piirakkadiagrammi kunkin esineen todennäköisyyttä tulla lopputulemaksi. Diagrammi piirretään uudelleen jokaisen esineen valitsemisen tai poistamisen jälkeen.