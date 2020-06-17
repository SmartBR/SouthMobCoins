package net.smart.mobcoins.database.object;

import lombok.Getter;
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
import java.util.stream.Collectors;

public class CoinsManager {

   @Getter protected HashMap<String, PlayerCoins> users;

    public CoinsManager() {
        users = new HashMap<>();
        load();
        saveAsync();
    }
    public PlayerCoins getPlayer(String player) {
        return users.getOrDefault(player, null);
    }
    public PlayerCoins createPlayer(String player, int coins) {
        PlayerCoins playerCoins = new PlayerCoins(player, coins);
        return playerCoins;
    }
    public List<Map.Entry<String, PlayerCoins>> getTopCoins() {
        List<Map.Entry<String, PlayerCoins>> top = users.entrySet().stream().sorted((x, y) -> y.getValue().getCoins().compareTo(x.getValue().getCoins())).collect(Collectors.toList());
        return top;
    }
    private void load() {
        Database db = Main.getInstance().database;
        try {
            if (db != null && (db.hasConnect())) {
                ResultSet rs = db.query("select * from `mobcoins`");
                while (rs.next()) {
                    String player = rs.getString("Jogador");
                    int coins = rs.getInt("Coins");
                    createPlayer(player, coins);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void saveAsync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                users.entrySet().forEach(user -> {
                    if (Bukkit.getPlayer(user.getValue().getPlayer()) == null) return;
                    user.getValue().save();
                });
            }
        }.runTaskTimer(Main.getInstance(), 0, 300*20L);
    }
}
