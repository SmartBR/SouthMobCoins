package net.smart.mobcoins.database.object;

import lombok.Data;
import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PlayerCoins {

    private static HashMap<String, PlayerCoins> playerMap = new HashMap<>();

    private String player;
    private Integer coins;

    public static void loadCaches() {
        Database db = Main.getInstance().database;
        try {
            ResultSet rs = db.query("select * from `mobcoins`");
            while (rs.next()) {
                String player = rs.getString("Jogador");
                Integer coins = rs.getInt("Coins");
                new PlayerCoins(player, coins);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadSQL() {
        Database db = Main.getInstance().database;
        try {
            for (Map.Entry<String, PlayerCoins> caches : playerMap.entrySet()) {
                PlayerCoins pc = caches.getValue();
                ResultSet rs = db.query("select * from `mobcoins` where `Jogador` = '" + pc.getPlayer() + "'");
                if (rs.next()) {
                    db.execute("update `mobcoins` set `Coins` = '" + pc.getCoins() + "' where `Jogador` = '" + pc.getPlayer() + "'");
                }else {
                    db.execute("insert into `mobcoins` (`Jogador`, `Coins`) values ('" + pc.getPlayer() + "', '" + pc.getCoins() + "')");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<PlayerCoins> getTopCoins() {
        Database db = Main.getInstance().database;
        List<PlayerCoins> top = new ArrayList<>();
        try {
            ResultSet rs = db.query("select * from `mobcoins` order by `Coins` DESC");
            while (rs.next()) {
                String player = rs.getString("Jogador");
                Integer coins = rs.getInt("Coins");
                PlayerCoins pCoins = new PlayerCoins(player, coins);
                top.add(pCoins);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return top;
    }
    public PlayerCoins(String player, Integer coins) {
        this.player = player;
        this.coins = coins;
        playerMap.put(player.toLowerCase(), this);
    }
    public static PlayerCoins getPlayer(String player) {
        return playerMap.getOrDefault(player.toLowerCase(), null);
    }
    public void saveAsync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getPlayer(player) != null) {
                    save();
                }else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 300*20L);
    }
    public void save() {
        Database db = Main.getInstance().database;
        try {
            ResultSet rs = db.query("select * from `mobcoins` where `Jogador` = '" + this.player + "'");
            if (rs.next()) {
                db.execute("update `mobcoins` set `Coins` = '" + this.coins + "' where `Jogador` = '" + this.player + "'");
            }else {
                db.execute("insert into `mobcoins` (`Jogador`, `Coins`) values ('" + this.player + "', '" + this.coins + "')");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCoins(Integer coins) {
        setCoins(getCoins() + coins);
    }
    public void removeCoins(Integer coins) {
        setCoins(getCoins() - coins);
    }
}
