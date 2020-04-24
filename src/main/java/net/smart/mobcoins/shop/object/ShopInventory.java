package net.smart.mobcoins.shop.object;

import lombok.*;
import net.smart.mobcoins.Main;

@AllArgsConstructor @Data
public class ShopInventory {

    private String title;
    private Integer size;
    private Boolean enable;

   public static ShopInventory load() {
        String title = Main.getInstance().shop.getString("gui.title").replace("&", "§");
        int size = Main.getInstance().shop.getInt("gui.size");
        Boolean enable = Main.getInstance().shop.getBoolean("gui.enable");
        return new ShopInventory(title, size, enable);
    }
}
