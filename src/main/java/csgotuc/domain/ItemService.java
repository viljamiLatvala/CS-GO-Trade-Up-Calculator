/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.domain;

import csgotuc.dao.ItemDao;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author latvavil
 */
public class ItemService {

    private ItemDao itemDao;
    private SortedMap<Integer, Item> inputMap;
    private ArrayList<Integer> freeSlots;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
        this.inputMap = new TreeMap<>();
        this.freeSlots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            freeSlots.add(i);
        }
        Collections.sort(freeSlots);
    }

    public List<Item> getInput() {
        List<Item> inputList = new ArrayList<>();
        this.inputMap.values().forEach(item -> inputList.add(item));
        return inputList;
    }

    public List<Item> getPossibleInputs() throws SQLException {
        return this.itemDao.getPossibleInputs();
    }

    public Item getInputItem(int key) {
        Item item = this.inputMap.get(key);
        return item;
    }

    public void removeFromInput(int slot) {
        this.inputMap.remove(slot);
        this.freeSlots.add(slot);
        Collections.sort(this.freeSlots);
    }

    public void addToInput(Item item) {
        System.out.println(item.toString());
        if (this.inputMap.size() >= 10) {
            throw new IllegalArgumentException("Maximum input size is 10!");
        } else if (this.inputMap.size() > 0 && this.inputMap.get(this.inputMap.firstKey()).getGrade() != item.getGrade()) {
            throw new IllegalArgumentException("All items must be of same grade!");
        } else if (item.getGrade() > 4) {
            throw new IllegalArgumentException("Item grade must be below 6");
        } else {
            this.inputMap.put(this.freeSlots.get(0), item);
            this.freeSlots.remove(0);
            Collections.sort(this.freeSlots);
        }

    }

    public List<Item> getAll() throws SQLException {
        return this.itemDao.getAll();
    }

    public List<Item> getByGrade(int grade) throws SQLException {
        return this.itemDao.getByGrade(grade);
    }

    public void setInputWithIds(int[] ids) throws SQLException {
        List<Item> newInput = new ArrayList<>();
        for (int id : ids) {
            addToInput((Item) itemDao.findById(id));
        }
    }

    public List<Item> calculateTradeUp() throws SQLException {
        List<Item> output = new ArrayList<>();
        for (Item item : this.inputMap.values()) {
            for (Item outputItem : (List<Item>) itemDao.getChildren(item)) {
                output.add(outputItem);
            }
        }
        return output;
    }
}
