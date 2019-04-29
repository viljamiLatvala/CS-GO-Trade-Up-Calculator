/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.dao;

import java.util.List;
import csgotuc.domain.Item;
import java.sql.*;
import java.util.*;

/**
 *
 * Interface for DAO-classes
 * @param <Item>
 * @param <Integer>
 */
public interface ItemDao<Item, Integer> {

    /**
     *
     * @param item
     */
    void create(Item item);

    /**
     *
     * @return
     */
    List<Item> getAll();
    
    void delete(Item item);

    /**
     *
     * @return
     */
    List<Item> getPossibleInputs();

    /**
     *
     * @param inputItem
     * @return
     */
    List<Item> getChildren(Item inputItem);

    /**
     *
     * @param key
     * @return
     */
    Item findById(Integer key);
    
    Item findByName(String name);

    /**
     *
     * @param grade
     * @return
     */
    List<Item> getByGrade(int grade);

}
