package net.smart.mobcoins.placeholder;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.CoinsManager;
import net.smart.mobcoins.database.object.PlayerCoins;

import java.text.DecimalFormat;

public class MVdWPlaceholderHook {

    public void hook() {
        PlaceholderAPI.registerPlaceholder(Main.getInstance(), "{south-mcoins}", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                CoinsManager coinsManager = Main.getInstance().getCoinsManager();
                PlayerCoins playerCoins = coinsManager.getPlayer(placeholderReplaceEvent.getPlayer().getName());
                if (playerCoins != null) {
                    return new DecimalFormat().format(playerCoins.getCoins());
                }
                return "0";
            }
        });
    }
}
