/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import csgotuc.dao.FileItemDao;
import csgotuc.dao.ItemDao;
import csgotuc.domain.Item;
import csgotuc.domain.ItemService;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void setUp() {
        Path path = Paths.get(".", "/src/test/java/FileItemDaoTestData.csv");
        try {
            ItemDao fileItemDao = new FileItemDao(path.normalize().toString());
            itemService = new ItemService(fileItemDao);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Test
     public void inputIsEmptyInTheBeginning() {
         assertEquals(0, this.itemService.getInput().size());
     }
     
    @Test
     public void setInputWithIdsWorks() {
         int[] ids = {0};
         this.itemService.setInputWithIds(ids);
         assertEquals(1, this.itemService.getInput().size());
         assertEquals(new Item("UMP-45 | Caramel", "Kokoelma1", 0), this.itemService.getInput().get(0));
         int[] ids2 = {0,1,2};
         this.itemService.setInputWithIds(ids2);
         assertEquals(3, this.itemService.getInput().size());
         assertEquals(new Item("Glock-18 | Fade", "Kokoelma2", 0), this.itemService.getInput().get(2));   
     }
     
     @Test
     public void calculateTradeUpWorks1() {
         int[] ids = {0};
         this.itemService.setInputWithIds(ids);
         List<Item> result = this.itemService.calculateTradeUp();
         assertEquals(1, result.size());
         assertEquals(new Item("AUG | Hot Rod", "Kokoelma1", 1), result.get(0));
     }
     
    @Test
    public void calculateTradeUpWorks2() {
         int[] ids = {2,2,2,2};
         this.itemService.setInputWithIds(ids);
         List<Item> result = this.itemService.calculateTradeUp();
         assertEquals(8, result.size());
         result.forEach((item) -> {
             assertTrue(item.equals(new Item("MP9 | Bulldozer", "Kokoelma2", 1)) ||item.equals(new Item("SG 553 | Tornado", "Kokoelma2", 1)));
        });
     }
    
    @Test
    public void addToInputWorks() {
        Item item = new Item("Item","Testipakkaus",10);
        this.itemService.addToInput(item);
        assertTrue(this.itemService.getInput().contains(item));
    }
    
    @Test
    public void setInputWorks() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Item(Integer.toString(i), Integer.toString(i), i));
        }
        this.itemService.setInput(items);
        for (int i = 0; i < 10; i++) {
            assertTrue(this.itemService.getInput().get(i).equals(items.get(i)));
        }
    }
    
    @Test
    public void getAllWorks() {
        assertTrue(this.itemService.getAll().size() == 13);
    }
}
