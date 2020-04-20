package net.smart.mobcoins.database.listener;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.PlayerCoins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerCoinsListener implements Listener {

    @EventHandler
    public void join(AsyncPlayerPreLoginEvent e) {
        String player = e.getName();

        PlayerCoins coins = PlayerCoins.getPlayer(player);
        Integer default_value = Main.getInstance().getConfig().getInt("default-value");
        if (coins != null) {
            coins.saveAsync();
        }else {
            new PlayerCoins(player, default_value).saveAsync();
        }
    }
}
