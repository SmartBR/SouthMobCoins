package net.smart.mobcoins.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface Database {

    String getName();
    Connection getConnection();
    Statement getStatement();
    Database open();
    void close();
    void createTable();
    Boolean hasConnect();
    ResultSet query(String s);
    Boolean execute(String s);
}
