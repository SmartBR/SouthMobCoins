package net.smart.mobcoins.placeholder;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.CoinsManager;
import net.smart.mobcoins.database.object.PlayerCoins;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class LegendchatHook implements Listener {

    @EventHandler
    public void chat(ChatMessageEvent e) {
        Player p = e.getSender();

        if (e.getTags().contains("south-mcoins")) {
            CoinsManager coinsManager = Main.getInstance().getCoinsManager();
            PlayerCoins playerCoins = coinsManager.getPlayer(p.getName());
            e.setTagValue("south-mcoins", new DecimalFormat().format(playerCoins.getCoins()));
        }
    }
}
