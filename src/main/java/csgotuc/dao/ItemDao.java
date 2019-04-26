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
 * @author latvavil
 * @param <Item>
 * @param <Integer>
 */
public interface ItemDao<Item, Integer> {

    void create(Item item) throws SQLException;

    List<Item> getAll() throws SQLException;

    List<Item> getPossibleInputs() throws SQLException;

    List<Item> getChildren(Item inputItem) throws SQLException;

    Item findById(Integer key) throws SQLException;

    List<Item> getByGrade(int grade) throws SQLException;

}
