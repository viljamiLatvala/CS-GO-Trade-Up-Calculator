package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoOikeinAlussa() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test 
    public void saldonLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(1000);
        assertEquals(2000, kortti.saldo());
        kortti.lataaRahaa(500);
        assertEquals(2500, kortti.saldo());
        kortti.lataaRahaa(700);
        assertEquals(3200, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenToimiiKunKortillaOnSaldoa() {
        kortti.otaRahaa(250);
        assertEquals(750, kortti.saldo());
        kortti.otaRahaa(250);
        assertEquals(500, kortti.saldo());
    }
        
    @Test
    public void rahanOttaminenEiOnnistuLiianPienellaSaldolla() {
        kortti.otaRahaa(1001);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaOikeanTotuusarvomuuttjuan() {
        assertEquals(true, kortti.otaRahaa(500));
        assertEquals(false, kortti.otaRahaa(501));
    }
    
    @Test
    public void toStringToimiiOikein(){
        assertEquals("saldo: 10.0", kortti.toString());
        kortti.otaRahaa(250);
        assertEquals("saldo: 7.50", kortti.toString());
    }
}
