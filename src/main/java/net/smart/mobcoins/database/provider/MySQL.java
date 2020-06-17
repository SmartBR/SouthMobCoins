package net.smart.mobcoins.database.provider;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.Database;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL implements Database {

    private Connection con;
    private Statement statement;

    @Override
    public String getName() {
        return "MySQL";
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
    public MySQL open() {
        String host = Main.getInstance().banco.getString("mysql.host");
        String user = Main.getInstance().banco.getString("mysql.user");
        String password = Main.getInstance().banco.getString("mysql.password");
        String database = Main.getInstance().banco.getString("mysql.database");
        Integer port = Main.getInstance().banco.getInt("mysql.port");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";

        try {
            if (con == null || (con.isClosed())) {
                con = DriverManager.getConnection(url, user, password);
                statement = con.createStatement();
            }
        }catch (SQLException e) {
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
        execute("create table if not exists `mobcoins` (`ID` int not null auto_increment, " +
                "`Jogador` varchar(20), `Coins` int not null, " +
                "primary key(ID))");
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
