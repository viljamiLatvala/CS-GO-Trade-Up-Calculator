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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Viljami
 */
public class SQLItemDao implements ItemDao<Item, Integer> {

    private Database database;

    /**
     *
     * @param database
     */
    public SQLItemDao(Database database) {
        this.database = database;
    }

    /**
     *
     * @param item
     */
    @Override
    public void create(Item item) {
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to get one item with specific id from the database.
     *
     * @param key desired id
     * @return Item with corresponding id.
     */
    @Override
    public Item findById(Integer key) {
        Item fetchedItem = null;
        try {
            Connection connection = database.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE id = ?");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            fetchedItem = fetchItem(rs);

            stmt.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (Item) fetchedItem;
    }

    @Override
    public Item findByName(String name) {
        Item fetchedItem = null;
        try {
            Connection connection = database.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            fetchedItem = fetchItem(rs);

            stmt.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Item) fetchedItem;
    }

    /**
     * Method for getting all items in the database
     *
     * @return list of all items in the database.
     */
    @Override
    public List<Item> getAll() {
        List<Item> fetchedItems = new ArrayList<>();
        try {
            Connection connection = database.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                fetchedItems.add(fetchItem(rs));
            }

            stmt.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fetchedItems;
    }

    /**
     * Method looks for items frome the same collection as the input item, but
     * with one grade higher rarity.
     *
     * @param inputItem
     * @return List of items with grade one level higher than that of the input
     * item.
     */
    @Override
    public List<Item> getChildren(Item inputItem) {
        List<Item> fetchedItems = new ArrayList<>();
        try {
            String collection = inputItem.getCollection();
            int desiredGrade = inputItem.getGrade() + 1;

            Connection connection = database.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE collection = ? AND grade = ?");
            stmt.setString(1, collection);
            stmt.setInt(2, desiredGrade);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                fetchedItems.add(fetchItem(rs));
            }

            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fetchedItems;
    }

    /**
     * Returns all items that have a matching grade to the parameter.
     *
     * @param grade desired grade of returned items
     * @return
     */
    @Override
    public List<Item> getByGrade(int grade) {
        List<Item> fetchedItems = new ArrayList<>();
        try {

            Connection connection = database.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE grade = ?");
            stmt.setInt(1, grade);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                fetchedItems.add(fetchItem(rs));
            }

            stmt.close();
            rs.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fetchedItems;
    }

    /**
     * Method used to get a list of items that are suitable for a Trade
     * Up-contract. This method is used instead of the getAll()-method to avoid
     * getting Items that have no child-items.
     *
     * @return
     */
    @Override
    public List<Item> getPossibleInputs(){
        List<Item> fetchedItems = new ArrayList<>();
        try {
            
            Connection connection = database.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Item WHERE grade < ?");
            stmt.setInt(1, 5);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                fetchedItems.add(fetchItem(rs));
            }

            stmt.close();
            rs.close();
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fetchedItems;
    }

    private Item fetchItem(ResultSet rs) throws SQLException {
        Item fetchedItem = new Item(rs.getString("weapon"), rs.getString("design"),
                rs.getString("collection"), rs.getInt("grade"),
                rs.getBytes("image"), rs.getDouble("minwear"),
                rs.getDouble("maxwear"));

        return fetchedItem;
    }

}
