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
import java.util.List;
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
        Database db = new Database("jdbc:sqlite:database.db");
        ItemDao itemDao = new SQLItemDao(db);
        itemService = new ItemService(itemDao);
    }
    
    @Test
     public void inputIsEmptyInTheBeginning() {
         assertEquals(0, this.itemService.getInput().size());
     }
     
//    @Test
//     public void setInputWithIdsWorks() throws SQLException {
//         int[] ids = {2};
//         this.itemService.setInputWithIds(ids);
//         assertEquals(1, this.itemService.getInput().size());
//         assertEquals(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null), this.itemService.getInput().get(0));
//         int[] ids2 = {2,3,4};
//         this.itemService.setInputWithIds(ids2);
//         assertEquals(3, this.itemService.getInput().size());
//         assertEquals(new Item("Dual Berettas", "Twin Turbo", "The 2018 Inferno Collection", 4, null), this.itemService.getInput().get(1));   
//     }
     
     @Test
     public void calculateTradeUpWorks1() throws SQLException {
         int[] ids = {4};
         this.itemService.setInputWithIds(ids);
         List<Item> result = this.itemService.calculateTradeUp();
         assertEquals(2, result.size());
         assertEquals(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null).toString(), result.get(0).toString());
     }
     
    @Test
    public void calculateTradeUpWorks2() throws SQLException {
         int[] ids = {4,4,4,4};
         this.itemService.setInputWithIds(ids);
         List<Item> result = this.itemService.calculateTradeUp();
         assertEquals(8, result.size());
         result.forEach((item) -> {
             assertTrue(item.equals(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null)) || item.equals(new Item("Dual Berettas", "Twin Turbo", "The 2018 Inferno Collection", 4, null)));
        });
     }
    
    @Test
    public void addToInputWorks() {
        Item item = new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null);
        this.itemService.addToInput(item);
        assertTrue(this.itemService.getInput().contains(item));
    }
    
//    @Test
//    public void setInputWorks() {
//        List<Item> items = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            items.add(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null));
//        }
//        this.itemService.setInput(items);
//        for (int i = 0; i < 10; i++) {
//            assertTrue(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null).equals(items.get(i)));
//        }
//    }
    
    @Test
    public void getAllWorks() throws SQLException {
        assertTrue(this.itemService.getAll().size() == 765);
    }
}
