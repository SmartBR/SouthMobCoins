package net.smart.mobcoins.listener;

import net.smart.mobcoins.Main;
import net.smart.mobcoins.database.object.PlayerCoins;
import net.smart.mobcoins.mob.MobObject;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.text.DecimalFormat;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if (!Main.getInstance().damageTick) return;

        String type = e.getEntityType().toString();
        if (Main.getInstance().mobsCache.contains(type)) {
            Integer coins = MobObject.getMob(type).getPrice();
            PlayerCoins.getPlayer(p.getName()).addCoins(coins);
            Integer total = PlayerCoins.getPlayer(p.getName()).getCoins();
            for (String msg : Main.getInstance().getConfig().getStringList("mob-message.tick-message.message")) {
                p.sendMessage(msg.replace("&", "§").replace("{value}", coins.toString())
                    .replace("{total}", new DecimalFormat().format(total)));
            }
        }else {
            if (Main.getInstance().getConfig().getBoolean("default-mob.enable")) {
                Integer coins = Main.getInstance().getConfig().getInt("default-mob.value");
                PlayerCoins.getPlayer(p.getName()).addCoins(coins);
                Integer total = PlayerCoins.getPlayer(p.getName()).getCoins();
                for (String msg : Main.getInstance().getConfig().getStringList("mob-message.tick-message.message")) {
                    p.sendMessage(msg.replace("&", "§").replace("{value}", coins.toString())
                            .replace("{total}", new DecimalFormat().format(total)));
                }
            }
        }
    }
    @EventHandler
    public void death(EntityDeathEvent e) {
        if (!(e.getEntity().getKiller() instanceof Player)) return;
        Player p = (Player) e.getEntity().getKiller();

        if (Main.getInstance().damageTick) return;

        String type = e.getEntityType().toString();
        if (Main.getInstance().mobsCache.contains(type)) {
            Integer coins = MobObject.getMob(type).getPrice();
            PlayerCoins.getPlayer(p.getName()).addCoins(coins);
            Integer total = PlayerCoins.getPlayer(p.getName()).getCoins();
            for (String msg : Main.getInstance().getConfig().getStringList("mob-message.tick-message.message")) {
                p.sendMessage(msg.replace("&", "§").replace("{value}", coins.toString())
                        .replace("{total}", new DecimalFormat().format(total)));
            }
        }else {
            if (Main.getInstance().getConfig().getBoolean("default-mob.enable")) {
                Integer coins = Main.getInstance().getConfig().getInt("default-mob.value");
                PlayerCoins.getPlayer(p.getName()).addCoins(coins);
                Integer total = PlayerCoins.getPlayer(p.getName()).getCoins();
                for (String msg : Main.getInstance().getConfig().getStringList("mob-message.tick-message.message")) {
                    p.sendMessage(msg.replace("&", "§").replace("{value}", coins.toString())
                            .replace("{total}", new DecimalFormat().format(total)));
                }
            }
        }
    }
}
