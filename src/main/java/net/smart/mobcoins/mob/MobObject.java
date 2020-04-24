package net.smart.mobcoins.mob;

import lombok.Data;
import net.smart.mobcoins.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class MobObject {

    private static HashMap<String, MobObject> mobs = new HashMap<>();

    private String type;
    private Integer price;

    public static MobObject getMob(String mobType) {
        return mobs.getOrDefault(mobType, null);
    }
    public static List<MobObject> load() {
        List<MobObject> list = new ArrayList<>();
        for (String mob : Main.getInstance().getConfig().getConfigurationSection("mob-list").getKeys(false)) {
            MobObject mobObject = new MobObject(mob, Main.getInstance().getConfig().getInt("mob-list." + mob));
            list.add(mobObject);
        }
        return list;
    }
    public MobObject(String type, Integer price) {
        this.type = type;
        this.price = price;
        mobs.put(type, this);
    }
}
