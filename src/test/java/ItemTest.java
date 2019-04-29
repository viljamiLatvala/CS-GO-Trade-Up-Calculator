
import csgotuc.domain.Item;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Viljami
 */
public class ItemTest {

    @Before
    public void setUp() {
    }

    @Test
    public void getConditionWorks() {
        Item item = new Item("TestName", "TestColl", 1, null, 0, 1);
        assertEquals("Factory New", item.getCondition());
        item.setFloatValue(0.1);
        assertEquals("Minimal Wear", item.getCondition());
        item.setFloatValue(0.2);
        assertEquals("Field-Tested", item.getCondition());
        item.setFloatValue(0.4);
        assertEquals("Well-Worn", item.getCondition());
        item.setFloatValue(1);
        assertEquals("Battle-Scarred", item.getCondition());
    }

    @Test
    public void deepCopyConstructorWorks() {
        Item item1 = new Item("TestName", "TestColl", 1, null, 0, 1);
        Item item2 = new Item(item1);
        item1.setFloatValue(3.0);
        assertTrue(item1.getFloatValue() != item2.getFloatValue());
    }
    
    @Test
    public void equalsWorks() {
        Item item1 = new Item("TestName", "TestColl", 1, null, 0, 1);
        assertTrue(item1.equals(item1));
        Item item2 = null;
        assertFalse(item1.equals(item2));
        String notItem = "This is not an item";
        assertFalse(item1.equals(notItem));
        item2 = new Item("TestName2", "TestColl", 1, null, 0, 1);
        assertFalse(item1.equals(item2));
        item2 = new Item("TestName", "TestColl2", 1, null, 0, 1);
        assertFalse(item1.equals(item2));
        item2 = new Item("TestName", "TestColl", 3, null, 0, 1);
        assertFalse(item1.equals(item2));
    }

}
