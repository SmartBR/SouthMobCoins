package net.smart.mobcoins.shop.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.smart.mobcoins.Main;

import java.util.List;

@AllArgsConstructor
public enum ShopMessages {

    ERRO_BUY(null),
    SUCESS_BUY(null);

    @Getter private List<String> msg;

    public static void load() {
        ERRO_BUY.msg = Main.getInstance().shop.getStringList("messages.erro-buy");
        SUCESS_BUY.msg = Main.getInstance().shop.getStringList("messages.sucess-buy");
    }
}
