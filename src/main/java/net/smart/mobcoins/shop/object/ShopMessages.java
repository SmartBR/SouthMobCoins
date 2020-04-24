package net.smart.mobcoins.shop.object;

import lombok.Data;
import net.smart.mobcoins.Main;

import java.util.List;

@Data
public class ShopMessages {

    private List<String> erroBuy, sucessBuy;

    public static ShopMessages load() {
        List<String> erroBuy = Main.getInstance().shop.getStringList("messages.erro-buy");
        List<String> sucessBuy = Main.getInstance().shop.getStringList("messages.sucess-buy");
        ShopMessages shopMessages = new ShopMessages(erroBuy, sucessBuy);
        return shopMessages;
    }
    public ShopMessages(List<String> erroBuy, List<String> sucessBuy) {
        this.erroBuy = erroBuy;
        this.sucessBuy = sucessBuy;
    }
}
