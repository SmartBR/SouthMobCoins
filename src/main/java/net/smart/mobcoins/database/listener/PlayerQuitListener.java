package net.smart.mobcoins.database.listener;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.PlayerCoins;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PlayerCoins coins = Main.getInstance().getCoinsManager().getPlayer(p.getName());

        if (coins != null) coins.save();
    }
}
