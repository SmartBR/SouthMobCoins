package net.smart.mobcoins.shop.listener;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.CoinsManager;
import net.smart.mobcoins.database.object.PlayerCoins;
import net.smart.mobcoins.shop.object.ShopInventory;
import net.smart.mobcoins.shop.object.ShopItem;
import net.smart.mobcoins.shop.object.ShopMessages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.DecimalFormat;
import java.util.List;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() != null && (e.getCurrentItem().getItemMeta() != null && (e.getCurrentItem().getItemMeta().getDisplayName() != null))) {
            CoinsManager coinsManager = Main.getInstance().getCoinsManager();
            PlayerCoins coins = coinsManager.getPlayer(p.getName());
            ShopInventory shopInv = Main.getInstance().getShopInventory();
            List<ShopItem> shopItem = Main.getInstance().getShopItens();
            DecimalFormat df = new DecimalFormat();

            if (e.getView().getTitle().equalsIgnoreCase(shopInv.getTitle())) {
                e.setCancelled(true);

                if (shopItem.contains(ShopItem.getItem(e.getCurrentItem()))) {
                    Integer amount = ShopItem.getItem(e.getCurrentItem()).getAmount();
                    Integer price = ShopItem.getItem(e.getCurrentItem()).getCoins();
                    if (coins.getCoins() >= price) {
                        coins.removeCoins(price);
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.0F, 15.0F);
                        for (String sucessBuy : ShopMessages.SUCESS_BUY.getMsg()) {
                            p.sendMessage(sucessBuy.replace("&", "§")
                                    .replace("{value}", df.format(price))
                                    .replace("{item}", e.getCurrentItem().getType().toString())
                                    .replace("{item-displayname}", e.getCurrentItem().getItemMeta().getDisplayName())
                                    .replace("{size}", df.format(ShopItem.getItem(e.getCurrentItem()).getAmount())));
                        }
                        for (String cmd : ShopItem.getItem(e.getCurrentItem()).getCommands()) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", p.getName())
                            .replace("{size}", ShopItem.getItem(e.getCurrentItem()).getAmount().toString()));
                        }
                    }else {
                        for (String erroBuy : ShopMessages.ERRO_BUY.getMsg()) {
                            p.sendMessage(erroBuy.replace("&", "§")
                                    .replace("{value}", df.format(price)));
                        }
                    }
                }
            }
        }
    }
}
