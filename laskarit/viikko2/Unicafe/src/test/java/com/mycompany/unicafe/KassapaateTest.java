/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author latvavil
 */
public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void uusiKassapaateLuodaanOikeallaRahamaaralla() {
        assertEquals(100000, this.paate.kassassaRahaa());
    }
    
    @Test
    public void uusiKassapaateLuodaanOikeallaMyytyjenEdullistenLounaidenMaaralla() {
        assertEquals(0, this.paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void uusiKassapaateLuodaanOikeallaMaukkaidenLounaidenMaaralla() {
        assertEquals(0, this.paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoKasvattaaMyytyjenLounaidenMaaraa() {
        this.paate.syoEdullisesti(240);
        assertEquals(1, this.paate.edullisiaLounaitaMyyty());
        this.paate.syoMaukkaasti(400);
        assertEquals(1, this.paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoKasvattaaKassaa() {
        this.paate.syoEdullisesti(240);
        assertEquals(100240, this.paate.kassassaRahaa());
        this.paate.syoMaukkaasti(400);
        assertEquals(100640, this.paate.kassassaRahaa());
    }
    
    @Test
    public void katesostoilleAnnetaanOikeaVaihtoraha() {
        assertEquals(0, this.paate.syoEdullisesti(240));
        assertEquals(0, this.paate.syoMaukkaasti(400));
        assertEquals(260, this.paate.syoEdullisesti(500));
        assertEquals(100, this.paate.syoMaukkaasti(500));
    }
    
    @Test
    public void liianPieniaKateismaksujaEiHyvaksyta() {
        assertEquals(230, this.paate.syoEdullisesti(230));
        assertEquals(0, this.paate.edullisiaLounaitaMyyty());
        assertEquals(390, this.paate.syoMaukkaasti(390));
        assertEquals(0, this.paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoKasvattaaMyytyjenLounaidenMaaraa() {
        this.paate.syoEdullisesti(this.kortti);
        assertEquals(1, this.paate.edullisiaLounaitaMyyty());
        this.paate.syoMaukkaasti(this.kortti);
        assertEquals(1, this.paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoEiKasvataKassaa() {
        this.paate.syoEdullisesti(this.kortti);
        assertEquals(100000, this.paate.kassassaRahaa());
        
        this.paate.syoMaukkaasti(this.kortti);
        assertEquals(100000, this.paate.kassassaRahaa());
    }
    
    @Test
    public void onnistunutKorttiostoPalauttaaTotuusmuuttujanTrue() {
        assertEquals(true, this.paate.syoEdullisesti(this.kortti));
        assertEquals(true, this.paate.syoMaukkaasti(this.kortti));
    }
    
    @Test
    public void epaonnistunutKorttiostoPalauttaaTotuusmuuttujanFalse() {
        this.kortti.otaRahaa(900);
        assertEquals(false, this.paate.syoEdullisesti(this.kortti));
        assertEquals(false, this.paate.syoMaukkaasti(this.kortti));
    }
    
    @Test
    public void epäonnistunutKorttiostoEiKasvataMyytyjenLounaidenMaaraa() {
        this.kortti.otaRahaa(900);
        this.paate.syoEdullisesti(this.kortti);
        assertEquals(0, this.paate.edullisiaLounaitaMyyty());
        this.paate.syoMaukkaasti(this.kortti);
        assertEquals(0, this.paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void epaonnistunutKorttiostoEiVahennaKortinSaldoa() {
        this.kortti.otaRahaa(900);
        this.paate.syoEdullisesti(this.kortti);
        assertEquals(100, this.kortti.saldo());
        this.paate.syoMaukkaasti(this.kortti);
        assertEquals(100, this.kortti.saldo());
    }
    
    @Test
    public void kortinLataaminenKasvattaaKassaa() {
        this.paate.lataaRahaaKortille(this.kortti, 1000);
        assertEquals(101000, this.paate.kassassaRahaa());
    }
    
    @Test
    public void kortinLataaminenKasvattaaKortinSaldoa() {
        this.paate.lataaRahaaKortille(this.kortti, 1000);
        assertEquals(2000, this.kortti.saldo());
    }
    
    @Test
    public void kortinLataaminenNollallaEiMuutaKortinSaldoa() {
        this.paate.lataaRahaaKortille(this.kortti, 0);
        assertEquals(1000, this.kortti.saldo());
    }
    
    @Test
    public void kortinLataaminenNollallaEiMuutaKassaa() {
        this.paate.lataaRahaaKortille(this.kortti, 0);
        assertEquals(100000, this.paate.kassassaRahaa());
    }
}
