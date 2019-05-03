/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.dao;

import java.sql.*;

/**
 * Class that contains the database connection to database specified by the constructor parameter.
 * @author Viljami
 */
public class Database {
    private String databaseAddress;

    /**
     *
     * @param databaseAddress   location of the database
     * @throws ClassNotFoundException
     */
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    /**
     *
     * @return  the connection to the database.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
}
