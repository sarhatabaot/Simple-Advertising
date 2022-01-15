package com.moshu.simpleadvertising;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {

    Economy econ;

    Advertising ad = new Advertising(this);

    public Advertising getAdvertisingClass() {
        return ad;
    }

    Broadcast bc = new Broadcast(this);

    public Broadcast getBroadcastClass() {
        return bc;
    }

    Events evn = new Events(this);

    public Events getEventsClass() {
        return evn;
    }

    Utils ut = new Utils(this);

    public Utils getUtilsClass() {
        return ut;
    }

    Updater up = new Updater(this);

    public Updater getUpdaterClass() {
        return up;
    }

    AutoMessage am = new AutoMessage(this);

    public AutoMessage getAutoMessageClass() {
        return am;
    }

    AutoTitles at = new AutoTitles(this);

    public AutoTitles getAutoTitlesClass() {
        return at;
    }

    AdvertisingPoints pts = new AdvertisingPoints(this);

    public AdvertisingPoints getPointsClass() {
        return pts;
    }

    Debug dbg = new Debug(this);

    public Debug getDebugClass() {
        return dbg;
    }

    Placeholders pls = new Placeholders(this);

    public Placeholders getPlaceholdersClass() {
        return pls;
    }


    public boolean hasDependencies() {


        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else if (Bukkit.getPluginManager().getPlugin("EssentialsX") == null && Bukkit.getPluginManager().getPlugin("Essentials") == null) {
            return false;
        } else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return false;
        }

        return true;
    }


    public static void consoleMessage(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void enableCheck(PluginEnableEvent e) {
        if (e.getPlugin().getName().equals("EssentialsLite")) {

            if (!hasDependencies()) {

                if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
                    consoleMessage("&c&lError: &fYou don't have Vault installed, plugin is shutting down");
                } else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
                    consoleMessage("&6&lWarning: &fYou don't have PAPI installed, placeholders disabled");
                    return;
                } else if (Bukkit.getPluginManager().getPlugin("EssentialsX") == null) {
                    consoleMessage("&6&lWarning: &FYou don't have Essentials installed, you may not have Economy Support.");
                    return;
                }

                Bukkit.getPluginManager().disablePlugin(this);

            }

        }
    }


	@Override
    public void onEnable() {

        PluginDescriptionFile pdfFile = getDescription();

        String msg = ChatColor.translateAlternateColorCodes('&', "&cSimpleAdvertising &fstarted successfuly");
        String msg1 = ChatColor.translateAlternateColorCodes('&', "&fVersion: &c" + pdfFile.getVersion());
        String msg2 = ChatColor.translateAlternateColorCodes('&', "&cFor news: &7https://www.spigotmc.org/resources/simple-advertising.40414/");
        String msg4 = ChatColor.translateAlternateColorCodes('&', "&fMade by &cMoshu&f, this is the real deal.");

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* " + msg));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* " + msg1));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* " + msg2));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* " + msg4));


        up.check();

        createFiles();
        new Metrics(this, 7360);

        if (getConfig().getBoolean("auto-advertiser.chat") == true) {
            am.start();
        }

        if (getConfig().getBoolean("auto-advertiser.titles") == true) {
            at.start();
        }

        setupEconomy();
        ut.createConfig();

        getCommand("ad").setExecutor(ad);
        getCommand("broadcast").setExecutor(bc);
        getCommand("points").setExecutor(pts);

        getCommand("ad").setTabCompleter(new TabComplete());
        getCommand("points").setTabCompleter(new TabComplete());

        Bukkit.getServer().getPluginManager().registerEvents(evn, this);
        Bukkit.getServer().getPluginManager().registerEvents(up, this);

        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);

        econ = (Economy) rsp.getProvider();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }

    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSimpleAdvertising &fis disabling"));
    }

    public boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy) rsp.getProvider();
        if (econ != null) {
            return true;
        }
        return false;
    }

    public FileConfiguration getData() {
        return this.data;
    }

    private File dataf;
    private FileConfiguration data;

    public void createFiles() {

        dataf = new File(getDataFolder(), "data.yml");

        if (!dataf.exists()) {
            dataf.getParentFile().mkdirs();
            saveResource("data.yml", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l* &cData.yml &fnot found, creating."));
        }

        data = new YamlConfiguration();

        try {

            try {
                data.load(dataf);
            } catch (InvalidConfigurationException e) {

                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadFiles() {
        Plugin pl = Bukkit.getPluginManager().getPlugin("SimpleAdvertising");

        File configf = new File(pl.getDataFolder(), "config.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configf);

        try {

            config.save(configf);

        } catch (IOException e) {
            e.printStackTrace();
        }

        config = YamlConfiguration.loadConfiguration(configf);


    }

}
