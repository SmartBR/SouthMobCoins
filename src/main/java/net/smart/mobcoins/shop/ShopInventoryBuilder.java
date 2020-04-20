package net.smart.mobcoins.shop;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.shop.object.ShopInventory;
import net.smart.mobcoins.shop.object.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopInventoryBuilder {

    public void open(Player p) {
        ShopInventory inventory = Main.getInstance().getShopInventory();
        Inventory inv = Bukkit.createInventory(null, inventory.getSize() * 9, inventory.getTitle());
        for (ShopItem item : Main.getInstance().getShopItens()) {
            ItemStack i = item.getItem();
            if (i.getAmount() >= 64) {
                i.setAmount(64);
            }
            inv.setItem(item.getSlot() + 1, i);
        }
        p.openInventory(inv);
    }
}
