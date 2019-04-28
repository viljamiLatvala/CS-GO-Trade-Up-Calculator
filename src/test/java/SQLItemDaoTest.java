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
import java.util.Random;
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
    int startSize;
    Random random;

    public SQLItemDaoTest() {

    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        Database db = new Database("jdbc:sqlite:test_database.db");
        itemDao = new SQLItemDao(db);
        startSize = itemDao.getAll().size();
        random = new Random();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAllReturnsAllItems() throws SQLException {
        List<Item> list = itemDao.getAll();
        assertEquals(startSize, list.size());
    }
    
    @Test
    public void createItemWorks() throws SQLException {
        List<Item> list = itemDao.getAll();
        assertEquals(startSize, list.size());
        String name = "dummy" + this.random.nextInt();
        String design = "dummy" + this.random.nextInt();
        String collection = "dummy" + this.random.nextInt();
        int grade = this.random.nextInt() + 9;
        Item item = new Item(name, design, collection, grade, null);
        itemDao.create(item);
        list = itemDao.getAll();
        assertEquals(startSize + 1, list.size());
        this.startSize ++;
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
