package net.smart.mobcoins.shop.object;

import net.smart.mobcoins.Main;

public class ShopInventory {

    private String title;
    private Integer size;
    private Boolean enable;

    public ShopInventory(String title, Integer size, Boolean enable) {
        this.title = title;
        this.size = size;
        this.enable = enable;
    }
    public static ShopInventory loadShopInventory() {
        String title = Main.getInstance().shop.getString("gui.title").replace("&", "§");
        int size = Main.getInstance().shop.getInt("gui.size");
        Boolean enable = Main.getInstance().shop.getBoolean("gui.enable");
        ShopInventory shopInventory = new ShopInventory(title, size, enable);
        return shopInventory;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Boolean isEnable() {
        return enable;
    }
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
