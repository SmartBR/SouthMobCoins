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

    protected PlayerCoins(String player, Integer coins) {
        this.player = player;
        this.coins = coins;
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
