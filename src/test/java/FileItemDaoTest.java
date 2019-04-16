/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import csgotuc.dao.ItemDao;
import csgotuc.domain.Item;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FileItemDaoTest {
    ItemDao fileItemDao;
    public FileItemDaoTest() {
        
    }
    
    @Before
    public void setUp() {
        Path path = Paths.get(".", "/src/test/java/FileItemDaoTestData.csv");
        try {
            fileItemDao = new FileItemDao(path.normalize().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getAllReturnsAllItems() {
        List<Item> list = fileItemDao.getAll();
        assertEquals(13, list.size());
    }
    
    @Test 
    public void getByIdRetunsCorrectObject() {
        Item received = fileItemDao.findById(2);
        assertEquals(new Item("Glock-18 | Fade", "Kokoelma2", 0), received);
    }
    
    @Test
    public void getChildrenReturnsCorrectItems() {
        Item parent = fileItemDao.findById(2);
        List<Item> children = fileItemDao.getChildren(parent);
        assertTrue(children.contains(new Item("MP9 | Bulldozer", "Kokoelma2",1)));
        assertTrue(children.contains(new Item("SG 553 | Tornado", "Kokoelma2",1)));
        assertEquals(2, children.size());
    }
    
    @Test
    public void itemGradeIsSetCorrectly() {
        assertTrue(fileItemDao.findById(0).getGrade() == 0);
        assertTrue(fileItemDao.findById(1).getGrade() == 1);
        assertTrue(fileItemDao.findById(9).getGrade() == 2);
        assertTrue(fileItemDao.findById(10).getGrade() == 3);
        assertTrue(fileItemDao.findById(11).getGrade() == 4);
        assertTrue(fileItemDao.findById(12).getGrade() == 5);
    }
    
    
}
