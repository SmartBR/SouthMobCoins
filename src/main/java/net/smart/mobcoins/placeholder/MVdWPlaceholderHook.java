package net.smart.mobcoins.placeholder;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.PlayerCoins;

import java.text.DecimalFormat;

public class MVdWPlaceholderHook {

    public void hook() {
        PlaceholderAPI.registerPlaceholder(Main.getInstance(), "{south-mcoins}", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                if (PlayerCoins.getPlayer(placeholderReplaceEvent.getPlayer().getName()) != null) {
                    return new DecimalFormat().format(PlayerCoins.getPlayer(placeholderReplaceEvent.getPlayer().getName()).getCoins());
                }
                return "0";
            }
        });
    }
}
