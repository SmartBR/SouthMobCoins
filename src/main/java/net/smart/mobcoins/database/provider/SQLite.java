package net.smart.mobcoins.database.provider;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.Database;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.*;

public class SQLite implements Database {

    private Connection con;
    private Statement statement;

    @Override
    public String getName() {
        return "SQLite";
    }
    @Override
    public Connection getConnection() {
        return con;
    }
    @Override
    public Statement getStatement() {
        return statement;
    }
    @Override
    public SQLite open() {
        File file = new File(Main.getInstance().getDataFolder(), "database.db");
        String url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            if (con == null || (con.isClosed())) {
                con = DriverManager.getConnection(url);
                statement = con.createStatement();
            }
        }catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c[SouthMobCoins] §fHouve um erro ao conectar-se com o mysql!");
            Bukkit.getConsoleSender().sendMessage("§c[SouthMobCoins] §fDetalhes: §c" + e.getLocalizedMessage());
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
        return this;
    }
    @Override
    public void close() {
        try {
            if (con != null || (!con.isClosed())) {
                statement.close();
                con.close();
                con = null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void createTable() {
        execute("create table if not exists `mobcoins` (`Jogador` text, " +
                "`Coins` text)");
    }
    @Override
    public Boolean hasConnect() {
        return con != null;
    }
    @Override
    public ResultSet query(String s) {
        try {
            return statement.executeQuery(s);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Boolean execute(String s) {
        try {
            return statement.execute(s);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
