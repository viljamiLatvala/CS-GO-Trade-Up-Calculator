/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.dao;

import java.util.List;
import csgotuc.domain.Item;

/**
 *
 * @author latvavil
 */
public interface ItemDao {
    List<Item> getAll();
    List<Item> getChildren(Item inputItem);
    Item findById(int id);
    
}
