package csgotuc.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import csgotuc.dao.Database;
import csgotuc.dao.ItemDao;
import csgotuc.dao.SQLItemDao;
import csgotuc.domain.Item;
import csgotuc.domain.ItemService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ItemServiceTest {
    ItemService itemService;
    
    public ItemServiceTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:test_database.db");
        ItemDao itemDao = new SQLItemDao(db);
        itemService = new ItemService(itemDao);
    }
    
    @Test
     public void inputIsEmptyInTheBeginning() {
         assertEquals(0, this.itemService.getInput().size());
     }
     
     @Test
     public void calculateTradeUpWorks1() throws SQLException {
         this.itemService.addToInput(this.itemService.findByName("AK-47 | Safety Net"));
         List<Item> result = this.itemService.calculateTradeUp();
         assertEquals(2, result.size());
         assertTrue(result.contains(this.itemService.findByName("SG 553 | Integrale")));
     }
     
    @Test
    public void calculateTradeUpWorks2() throws SQLException {
        for (int i = 0; i < 4; i++) {
             this.itemService.addToInput(this.itemService.findByName("AK-47 | Safety Net"));
        }
         List<Item> result = this.itemService.calculateTradeUp();
         assertEquals(8, result.size());
         result.forEach((item) -> {
             assertTrue(item.equals(this.itemService.findByName("SG 553 | Integrale")) || item.equals(this.itemService.findByName("Dual Berettas | Twin Turbo")));
        });
     }
    
    @Test
    public void addToInputWorks() {
        Item item = this.itemService.findByName("AK-47 | Safety Net");
        this.itemService.addToInput(item);
        assertTrue(this.itemService.getInput().contains(item));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addToInputThrowsError() {
        Item item = this.itemService.findByName("AK-47 | Safety Net");
        for (int i = 0; i < 11; i++) {
            this.itemService.addToInput(item);
        }
    }
    
     @Test
    public void getByGradeReturnsCorrectAmount() {
        List<Item> list = this.itemService.getByGrade(2);
        assertEquals(9, list.size());
    }
    
    @Test
    public void getInputItemWorks() {
        Item item1 = this.itemService.findByName("AK-47 | Safety Net");
        this.itemService.addToInput(item1);
        assertEquals(item1, this.itemService.getInputItem(0));
        Item item2 = this.itemService.findByName("SG 553 | Integrale");
        this.itemService.addToInput(item2);
        assertEquals(item1, this.itemService.getInputItem(0));
        assertEquals(item2, this.itemService.getInputItem(1));
    }
    
    @Test
    public void removeFromInputWorks() {
        Item item1 = this.itemService.findByName("AK-47 | Safety Net");
        this.itemService.addToInput(item1);
        Item item2 = this.itemService.findByName("SG 553 | Integrale");
        this.itemService.addToInput(item2);
        Item item3 = this.itemService.findByName("P250 | Vino Primo");
        this.itemService.addToInput(item3);
        
        this.itemService.removeFromInput(1);
        assertTrue(this.itemService.getInput().size() == 2);
        assertTrue(this.itemService.getInputItem(0) == item1);
        assertTrue(this.itemService.getInputItem(1) == null);
        assertTrue(this.itemService.getInputItem(2) == item3);
    }
    
    @Test
    public void getPossibleInputsWorks() {
        assertEquals(35, this.itemService.getPossibleInputs().size());
    }
}
