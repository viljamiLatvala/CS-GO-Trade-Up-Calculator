/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import csgotuc.dao.Database;
import csgotuc.dao.ItemDao;
import csgotuc.dao.SQLItemDao;
import csgotuc.domain.Item;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
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
public class SQLItemDaoTest {

    ItemDao itemDao;

    public SQLItemDaoTest() {

    }

    @Before
    public void setUp() throws ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:test_database.db");
        itemDao = new SQLItemDao(db);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAllReturnsAllItems() throws SQLException {
        List<Item> list = itemDao.getAll();
        assertEquals(42, list.size());
    }
    
    @Test
    public void createItemWorks() throws SQLException {
        List<Item> list = itemDao.getAll();
        assertEquals(42, list.size());
        Item item = new Item("dummy", "design", "rare", 5, null);
        itemDao.create(item);
        list = itemDao.getAll();
        assertEquals(43, list.size());
    }
    
    @Test
    public void getPossibleInputsWorks() throws SQLException {
        List<Item> list = itemDao.getPossibleInputs();
        assertEquals(42, list.size());
        Item item = new Item("dummy", "design", "rare", 5, null);
        list = itemDao.getPossibleInputs();
        assertEquals(42, list.size());
    }

    @Test
    public void getByIdRetunsCorrectObject() throws SQLException {
        Item received = (Item)itemDao.findById(2);
        assertEquals(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null), received);
    }

    @Test
    public void getChildrenReturnsCorrectItems() throws SQLException {
        Item parent = (Item)itemDao.findById(4);
        List<Item> children = itemDao.getChildren(parent);
        assertTrue(children.contains(new Item("SG 553", "Integrale", "The 2018 Inferno Collection", 4, null)));
        assertTrue(children.contains(new Item("Dual Berettas", "Twin Turbo", "The 2018 Inferno Collection", 4, null)));
        assertEquals(2, children.size());
    }
    
    @Test
    public void getByGradeReturnsCorrectAmount() throws SQLException {
        List<Item> list = itemDao.getByGrade(2);
        assertEquals(9, list.size());
    }

    @Test
    public void itemGradeIsSetCorrectly() throws SQLException {
        Item item = (Item)itemDao.findById(15);
        assertTrue(item.getGrade() == 0);
        item = (Item)itemDao.findById(11);
        assertTrue(item.getGrade() == 1);
        item = (Item)itemDao.findById(7);
        assertTrue(item.getGrade() == 2);
        item = (Item)itemDao.findById(1);
        assertTrue(item.getGrade() == 3);
        item = (Item)itemDao.findById(2);
        assertTrue(item.getGrade() == 4);
    }

}
