# Arkkitehtuurikuvaus

## Rakenne
Ohjelma käytettä seuraavanlaista, kolmitasoista pakkausrakennetta:

![Pakkausrakenne](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/pakkausrakenne.png )

## Luokkakaavio

![Luokkakaavio](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/luokkakaavio.png )

## Päätoiminnallisuudet
### Esineiden valitseminen tuotantopanokseksi
![Sekvenssikaavio](https://github.com/viljamiLatvala/ohjelmistotekniikka/blob/master/dokumentaatio/tradeupCalculation.png )

Kun käyttäjä valitsee käyttöliittymässä olevasta listasta esineen tuotantopanokseksi vaihtotapahtumaan, kutsuu käyttöliittymä ItemServicen addToInput-metodia. Tämän jälkeen käyttöliittymä muodostaa uuden piirakkadiagrammin käyttäjälle kutsumalla omaa metodiaan formChart. FormChart kutsuu jälleen ItemServiceä, joka lähettää säilyttämänsä input-esineet ItemDao:lle, joka palauttaa niitä vastaavat mahdolliset tuotantotulokset. ItemService välittää tämän listan eteenpäin käyttöliittymälle, joka piirtää kuvista piirakkadiagrammin käyttäjän nähtäväksi 
