/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.domain;

import csgotuc.domain.Item;
import java.util.ArrayList;
import java.util.List;
import csgotuc.dao.ItemDao;

/**
 *
 * @author latvavil
 */
public class ItemService {
    private ItemDao itemDao;
    private List<Item> input;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
        this.input = new ArrayList<>();
    }

    public List<Item> getInput() {
        return input;
    }

    public void setInput(List<Item> input) {
        this.input = input;
    }
    
    
    public void setInputWithIds(int[] ids) {
        List<Item> newInput = new ArrayList<>();
        for (int id : ids) {
            newInput.add(itemDao.findById(id));
        }
        this.input = newInput;
    }
    
    public List<Item> calculateTradeUp() {
        List<Item> output = new ArrayList<>();
        for (Item item : this.input) {
            for (Item outputItem : itemDao.getChildren(item)) {
                output.add(outputItem);
            }
        }
        return output;
    }
}
