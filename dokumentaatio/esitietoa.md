# Trade Up -sopimuksen toiminta Counter-Strike: Global Offensive pelissä
## Mikä Counter-Strike, mikä Trade Up-sopimus?
Counter-Strike: Global Offensive on First-person shooter-tyyppinen tietokonepeli, jonka kehittäjä on Valve Corporation. Pelin PC-versio jaellaan saman yrityksen Steam-palvelun kautta. Osana pelin ansaintalogiikkaa, on siinä oleville aseille kehitetty erilaisia kosmeettisia muutoksia, "skinejä", joita pelaajan on mahdollista saada mm. avaamalla pelistä saatavia aselaatikoita tai ostamalla toisilta pelaajilta Steam-kauppapaikalla.

Pelissä oleviin skineihin liittyy muutamia ominaisuuksia. Näitä ovat asekokoelma johon ase kuuluu, aseen laatuluokitus, kulutusluokitus ja kulutustaso. Peli tarjoaa pelaajille mahdollisuuden tarttua Trade Up-vaihtosopimukseen, jossa pelaaja valitsee tuotantopanokseksi 10 laatuluokitukseltaan samanarvoista skiniä ja saa näiden esineiden tilalle yhden laatuluokitukseltaan seuraavaa tasoa olevan esineen. Tämän tuotantotuloksen määrittää vaihtosopimuksessa tuotantopanoksena käytettyjen kymmenen esineen laatuluokitus, asekokoelma josta ne ovat, sekä kulutustaso(ja sen pohjalta määräytyvä kulutusluokitus).

Mekanismin toiminnasta on tarjolla runsaasti pelaaja-yhteisön tuottamaa tietoa, joihin myös tämän sovelluksen toiminta pohjautuu. Tässä sovelluksessa toteutettu sovelluslogiikka pohjautuu tähän [kirjoitukseen järjestelmän toiminnasta](https://www.reddit.com/r/GlobalOffensiveTrade/comments/7wam74/psa_xcobalts_complete_guide_to_the_trade_up/?depth=1).



## Trade Up -sopimuksen tarkempi toimintaperiaate
### Tehdäkseen Trade Up -sopimuksen tulee pelaajan antaa panokseksi 10 esinettä jotka:
- Ovat keskenään samaa laatuluokitusta
- Kuuluvat kokoelmaan johon kuuluu myön annettavan esineen laatuluokitusta korkeampia esineitä.

### Antamiaan kymmentä esinettä vastaan pelaaja saa yhden esineen joka määräytyy seuraavalla tavalla:
- Sopimuksesta saatava esine on _yhden laatuluokituksen korkeampi_ kuin panokseksi annetut esineet
- Sopimuksesta saatava esine _kuuluu johonkin niistä kokoelmista, joista panokseksi annetut esineet ovat_
- Sopimuksesta saatava esine arvotaan valitsemalla satunnaisesti esine joukosta, johon kuuluu jokaisen panokseksi annetun esineen lapset (esineen kanssa samaan kokoelmaan kuuluvat esineet joiden laatuluokitus on yhden ylempi)
- Mikäli sopimuksen panoksena monta esinettä samasta kokoelasta, voi saatavan esineen määrittävään joukkoon kuulua sama esine useamman kerran.

### Saatavan esineen lisäksi Trade Up -sopimuksen tekemisen yhteydessä tälle esineelle määritetään kuntoluokitus, joka muodostuu seuraavalla tavalla:
- Jokaisella esineellä on oma floatValue, joka kuvaa esineen maalipinnan eheyttä. Mitä pienempi floatValue on, sitä eheämpi sen maalipinta, kullakin esineellä on olemassa suurin ja pienin mahdollinen floatValue (maxWear & minWear).
- FloatValuen perusteella esineelle annetaan sanallinen kuntoluokitus seuraavasti:

| floatValue | Condition |
| ---------- | --------- |
| 0 - 0.069 | Factory New |
| 0.07 - 0.149 | Minimal Wear |
| 0.15 - 0.379 | Field-Tested |
| 0.38 - 0.449 | Well-Worn |
| 0.45 - 1.00 | Battle-Scarred |

- Trade Up sopimuksen yhteydessä saatavan esineen floatValue on sopimukseen annettujen esineiden floatValueiden keskiarvo (floatAvg), joka painotetaan saatavan esineen maxWear ja minWear arvoilla seuraavasti:
```
floatValue = floatAvg * (maxWear-minWear) + minWear
```
