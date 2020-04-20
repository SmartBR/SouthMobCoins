package net.smart.mobcoins.shop.object;

import net.smart.mobcoins.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShopItem {

    private static HashMap<ItemStack, ShopItem> shopP = new HashMap<>();

    private ItemStack item;
    private Integer coins;
    private Integer slot;
    private Integer amount;
    private List<String> commands;

    public ShopItem(ItemStack item, Integer coins, Integer slot, Integer amount, List<String> commands) {
        this.item = item;
        this.coins = coins;
        this.slot = slot;
        this.amount = amount;
        this.commands = commands;
        shopP.put(item, this);
    }
    public static ShopItem getItem(ItemStack item) {
        if (shopP.containsKey(item)) {
            return shopP.get(item);
        }
        return null;
    }
    public static List<ShopItem> loadItens() {
        List<ShopItem> list = new ArrayList<>();
        for (String item : Main.getInstance().shop.getConfigurationSection("itens").getKeys(false)) {
            String displayname = Main.getInstance().shop.getString("itens." + item + ".displayname").replace("&", "§");
            List<String> lore = Main.getInstance().shop.getStringList("itens." + item + ".lore");
            List<String> cmd = Main.getInstance().shop.getStringList("itens." + item + ".commands");
            String[] id = Main.getInstance().shop.getString("itens." + item + ".id").split(":");
            int slot = Main.getInstance().shop.getInt("itens." + item + ".slot");
            Integer price = Main.getInstance().shop.getInt("itens." + item + ".price");
            Integer amount = Main.getInstance().shop.getInt("itens." + item + ".size");
            DecimalFormat df = new DecimalFormat();

            int idInt = Integer.valueOf(id[0]);
            int data = Integer.valueOf(id[1]);
            ItemStack stack = new ItemStack(idInt, amount, (short) data);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(displayname);

            List<String> loreFinal = new ArrayList<>();
            for (String l : lore) {
                loreFinal.add(l.replace("&", "§")
                        .replace("{price}", df.format(price))
                        .replace("{size}", df.format(amount)));
            }
            stackMeta.setLore(loreFinal);
            stack.setItemMeta(stackMeta);
            ShopItem shopItem = new ShopItem(stack, price, slot, amount, cmd);
            list.add(shopItem);
            Bukkit.broadcastMessage(shopItem.toString());
        }
        return list;
    }
    public ItemStack getItem() {
        return item;
    }
    public void setItem(ItemStack item) {
        this.item = item;
    }
    public Integer getCoins() {
        return coins;
    }
    public void setCoins(Integer coins) {
        this.coins = coins;
    }
    public void setSlot(Integer slot) {
        this.slot = slot;
    }
    public Integer getSlot() {
        return slot;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public List<String> getCommands() {
        return commands;
    }
    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
