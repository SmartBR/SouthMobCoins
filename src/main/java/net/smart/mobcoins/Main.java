package net.smart.mobcoins;

import lombok.Getter;
import net.smart.mobcoins.command.MobCoinsCMD;
import net.smart.mobcoins.database.Database;
import net.smart.mobcoins.database.object.CoinsManager;
import net.smart.mobcoins.database.provider.MySQL;
import net.smart.mobcoins.database.provider.SQLite;
import net.smart.mobcoins.database.listener.PlayerCoinsListener;
import net.smart.mobcoins.database.listener.PlayerQuitListener;
import net.smart.mobcoins.database.object.PlayerCoins;
import net.smart.mobcoins.listener.EntityDamageListener;
import net.smart.mobcoins.mob.MobObject;
import net.smart.mobcoins.placeholder.LegendchatHook;
import net.smart.mobcoins.placeholder.MVdWPlaceholderHook;
import net.smart.mobcoins.shop.listener.InventoryClickListener;
import net.smart.mobcoins.shop.object.ShopInventory;
import net.smart.mobcoins.shop.object.ShopItem;
import net.smart.mobcoins.shop.object.ShopMessages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public Database database;
    @Getter private CoinsManager coinsManager;
    public Boolean damageTick;
    @Getter public List<PlayerCoins> topCoins = new ArrayList<>();

    @Getter public List<MobObject> mobsCache;
    @Getter private List<ShopItem> shopItens;
    @Getter private ShopInventory shopInventory;

    private File banco2 = new File(getDataFolder(), "banco-de-dados.yml");
    public YamlConfiguration banco;

    private File shop2 = new File(getDataFolder(), "shop.yml");
    public YamlConfiguration shop;

    public static Main getInstance() {
        return getPlugin(Main.class);
    }
    public void onEnable() {
        database = (banco.getBoolean("mysql.enable")) ? new MySQL() : new SQLite();
        database.createTable();
        coinsManager = new CoinsManager();
        loadCommands(); loadListeners();
        saveDefaultConfig();
        loadFiles();

        loadObjects();
        Bukkit.getConsoleSender().sendMessage("§a[SouthMobCoins] §fPlugin habilitado! §a[" + getDescription().getVersion() + "]");
        Bukkit.getConsoleSender().sendMessage("§a[SouthMobCoins] §fBanco de dados: §a" + database.getName());
        hook();
    }
    public void onDisable() {
        database.close();
    }
    private void loadObjects() {
        if (database != null && database.hasConnect()) {
            shopItens = ShopItem.load();
            shopInventory = ShopInventory.load();
            damageTick = getConfig().getBoolean("mob-message.tick-message.enable");
            mobsCache = MobObject.load();
            ShopMessages.load();
        }
    }
    private void loadFiles() {
        if (!banco2.exists()) {
            saveResource("banco-de-dados.yml", false);
        }
        if (!shop2.exists()) {
            saveResource("shop.yml", false);
        }
        banco = YamlConfiguration.loadConfiguration(banco2);
        shop = YamlConfiguration.loadConfiguration(shop2);
    }
    private void hook() {
        PluginManager pm = Bukkit.getPluginManager();

        if (pm.getPlugin("Legendchat") != null) {
            pm.registerEvents(new LegendchatHook(), this);
            Bukkit.getConsoleSender().sendMessage("§e[SouthMobCoins] §fHook §e'Legendchat' §ffuncionando!");
        }
        if (pm.getPlugin("MVdWPlaceholderAPI") != null) {
            new MVdWPlaceholderHook().hook();
            Bukkit.getConsoleSender().sendMessage("§e[SouthMobCoins] §fHook §e'MVdWPlaceholderAPI' §ffuncionando!");
        }
    }
    private void loadCommands() {
        getCommand("mobcoins").setExecutor(new MobCoinsCMD());
    }
    private  void loadListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerCoinsListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
    }
}
