package net.smart.mobcoins.database.listener;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.CoinsManager;
import net.smart.mobcoins.database.object.PlayerCoins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerCoinsListener implements Listener {

    @EventHandler
    public void join(AsyncPlayerPreLoginEvent e) {
        String player = e.getName();

        CoinsManager coinsManager = Main.getInstance().getCoinsManager();
        PlayerCoins coins = coinsManager.getPlayer(player);
        Integer default_value = Main.getInstance().getConfig().getInt("default-value");
        if (coins == null) {
            coinsManager.createPlayer(player, default_value);
        }
    }
}
