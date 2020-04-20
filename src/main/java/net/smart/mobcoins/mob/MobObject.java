package net.smart.mobcoins.mob;

import net.smart.mobcoins.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobObject {

    private static HashMap<String, MobObject> mobs = new HashMap<>();

    private String type;
    private Integer price;

    public MobObject(String type, Integer price) {
        this.type = type;
        this.price = price;
        mobs.put(type, this);
    }
    public static MobObject getMob(String mobType) {
        if (mobs.containsKey(mobType)) {
            return mobs.get(mobType);
        }
        return null;
    }
    public static List<MobObject> loadMobObjects() {
        List<MobObject> list = new ArrayList<>();
        for (String mob : Main.getInstance().getConfig().getConfigurationSection("mob-list").getKeys(false)) {
            MobObject mobObject = new MobObject(mob, Main.getInstance().getConfig().getInt("mob-list." + mob));
            list.add(mobObject);
        }
        return list;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
}
