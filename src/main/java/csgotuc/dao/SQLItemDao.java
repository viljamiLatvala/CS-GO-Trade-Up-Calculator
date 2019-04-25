/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.dao;

import csgotuc.domain.Item;
import java.sql.SQLException;
import java.util.List;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Viljami
 */
public class SQLItemDao implements ItemDao<Item, Integer> {

    private Database database;

    public SQLItemDao(Database database) {
        this.database = database;
    }

    @Override
    public void create(Item item) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Item"
                + " (name, weapon, design, collection, grade, minwear, maxwear, image)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, item.getName());
        stmt.setString(2, item.getWeapon());
        stmt.setString(3, item.getDesign());
        stmt.setString(4, item.getCollection());
        stmt.setInt(5, item.getGrade());
        stmt.setDouble(6, item.getMinWear());
        stmt.setDouble(7, item.getMaxWear());
        stmt.setBytes(8, item.getImage());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Item findById(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Item fetchedItem = new Item(rs.getString("weapon"), rs.getString("design"), rs.getString("collection"), rs.getInt("grade"), rs.getBytes("image"));

        stmt.close();
        rs.close();
        connection.close();

        return (Item) fetchedItem;
    }

    @Override
    public List<Item> getAll() throws SQLException {
        List<Item> fetchedItems = new ArrayList<>();

        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Item fetchedItem = new Item(rs.getString("weapon"), rs.getString("design"), rs.getString("collection"), rs.getInt("grade"), rs.getBytes("image"));
            fetchedItems.add(fetchedItem);
        }

        stmt.close();
        rs.close();
        connection.close();

        return fetchedItems;
    }

    @Override
    public List<Item> getChildren(Item inputItem) throws SQLException {
        List<Item> fetchedItems = new ArrayList<>();
        String collection = inputItem.getCollection();
        int grade = inputItem.getGrade() + 1;

        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE collection = ? AND grade = ?");
        stmt.setString(1, collection);
        stmt.setInt(2, grade);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Item fetchedItem = new Item(rs.getString("weapon"), rs.getString("design"), rs.getString("collection"), rs.getInt("grade"), rs.getBytes("image"));
            fetchedItems.add(fetchedItem);
        }

        stmt.close();
        rs.close();
        connection.close();
        return fetchedItems;
    }

    @Override
    public List<Item> getByGrade(int grade) throws SQLException {
        List<Item> fetchedItems = new ArrayList<>();

        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE grade = ?");
        stmt.setInt(1, grade);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Item fetchedItem = new Item(rs.getString("weapon"), rs.getString("design"), rs.getString("collection"), rs.getInt("grade"), rs.getBytes("image"));
            fetchedItems.add(fetchedItem);
        }

        stmt.close();
        rs.close();
        connection.close();

        return fetchedItems;
    }

    @Override
    public List<Item> getPossibleInputs() throws SQLException {
        List<Item> fetchedItems = new ArrayList<>();

        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE grade < ?");
        stmt.setInt(1, 5);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Item fetchedItem = new Item(rs.getString("weapon"), rs.getString("design"), rs.getString("collection"), rs.getInt("grade"), rs.getBytes("image"));
            fetchedItems.add(fetchedItem);
        }

        stmt.close();
        rs.close();
        connection.close();

        return fetchedItems;
    }

}
