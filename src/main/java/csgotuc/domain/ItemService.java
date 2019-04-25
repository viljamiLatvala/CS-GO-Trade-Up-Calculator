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
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author latvavil
 */
public class ItemService {

    private ItemDao itemDao;
    private List<Item> input;
    private SortedMap<Integer, Item> inputMap;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
        this.input = new ArrayList<>();
        this.inputMap = new TreeMap<>();
    }

    public List<Item> getInput() {
        List<Item> inputList = new ArrayList<>();
        this.inputMap.values().forEach(item -> inputList.add(item));
        return inputList;
    }
    
    public void removeFromInput(Item item) {
        List<Integer> toRemove = new ArrayList<>();
        for (Integer integer : inputMap.keySet()) {
            if(inputMap.get(integer).equals(item)){
                System.out.println("EQUALS!");
                toRemove.add(integer);
            }
        }
        inputMap.remove(toRemove.get(0));
    }

    public void setInput(List<Item> input) {
        input.forEach(item -> addToInput(item));
    }

    public void addToInput(Item item) {
        System.out.println(item.toString());
        if (this.inputMap.size() >= 10) {
            throw new IllegalArgumentException("Maximum input size is 10!");
        } else if (this.inputMap.size() > 0 && this.inputMap.get(this.inputMap.firstKey()).getGrade() != item.getGrade()) {
            throw new IllegalArgumentException("All items must be of same grade!");
        } else if (item.getGrade() > 4) {
            throw new IllegalArgumentException("Item grade must be below 6");
        } else if (this.inputMap.isEmpty()){
            this.inputMap.put(0, item);
        }else {
            this.inputMap.put(this.inputMap.lastKey()+1, item);
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
            newInput.add((Item) itemDao.findById(id));
        }
        this.input = newInput;
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
