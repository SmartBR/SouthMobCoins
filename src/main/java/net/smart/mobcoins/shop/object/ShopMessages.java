package net.smart.mobcoins.shop.object;

import net.smart.mobcoins.Main;

import java.util.List;

public class ShopMessages {

    private List<String> erroBuy;
    private List<String> sucessBuy;

    public ShopMessages(List<String> erroBuy, List<String> sucessBuy) {
        this.erroBuy = erroBuy;
        this.sucessBuy = sucessBuy;
    }
    public static ShopMessages loadShopMessages() {
        List<String> erroBuy = Main.getInstance().shop.getStringList("messages.erro-buy");
        List<String> sucessBuy = Main.getInstance().shop.getStringList("messages.sucess-buy");
        ShopMessages shopMessages = new ShopMessages(erroBuy, sucessBuy);
        return shopMessages;
    }
    public List<String> getErroBuy() {
        return erroBuy;
    }
    public void setErroBuy(List<String> erroBuy) {
        this.erroBuy = erroBuy;
    }
    public List<String> getSucessBuy() {
        return sucessBuy;
    }
    public void setSucessBuy(List<String> sucessBuy) {
        this.sucessBuy = sucessBuy;
    }
}
