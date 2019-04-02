/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.dao;

import csgotuc.dao.ItemDao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import csgotuc.domain.Item;

/**
 *
 * @author latvavil
 */
public class FileItemDao implements ItemDao {
    public List<Item> items;
    private String file;

    public FileItemDao(String file) throws Exception {
        items = new ArrayList<>();
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            while(reader.hasNextLine()) {
                int itemGrade = 0;
                String[] itemData = reader.nextLine().split(";");
                if(itemData[2].equals("Consumer Grade")) {
                    
                } else if(itemData[2].equals("Industrial Grade")) {
                    itemGrade = 1;
                } else if(itemData[2].equals("Mil-Spec")) {
                    itemGrade = 2;
                }else if(itemData[2].equals("Restricted")) {
                    itemGrade = 3;
                }else if(itemData[2].equals("Classified")) {
                    itemGrade = 4;
                } else if(itemData[2].equals("Covert")) {
                    itemGrade = 5;
                } else {
                    continue;
                }
                items.add(new Item(itemData[0],itemData[1],itemGrade));
            }
        } catch(Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    
    

    @Override
    public List<Item> getAll() {
        return this.items;
    }

    @Override
    public List<Item> getChildren(Item inputItem) {
        List<Item> children = new ArrayList<>();
        for (Item item : this.items) {
            if(item.getCollection().equals(inputItem.getCollection()) && 
               item.getGrade() == inputItem.getGrade() +1) {
                children.add(item);
            }
        }
        return children;
    }

    @Override
    public Item findById(int id) {
        return this.items.get(id);
    }
    
}
