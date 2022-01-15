package com.moshu.simpleadvertising.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.moshu.simpleadvertising.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Updater implements Listener {

    private static Main plugin;
    private final String url = "https://api.spigotmc.org/legacy/update.php?resource=";
    private final String id = "40414";

    private boolean isAvailable;
    public Updater(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("simplead.admin") && plugin.getConfig().getBoolean("enable.updater") && isAvailable) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fAn update is ready for you:"));
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&chttps://www.spigotmc.org/resources/simple-advertising.40414/updates"));
            Utils.sendSound(event.getPlayer());
        }
    }

    public void check() {
        isAvailable = checkUpdate();
    }

    private boolean checkUpdate() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fChecking for updates.."));

        try {
            String localVersion = plugin.getDescription().getVersion();
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url + id).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String remoteVersion;
            if (raw.contains("-")) {
                remoteVersion = raw.split("-")[0].trim();
            } else {
                remoteVersion = raw;
            }

            if (!localVersion.equalsIgnoreCase(remoteVersion)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fAn update is ready for you:"));
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&chttps://www.spigotmc.org/resources/simple-advertising.40414/updates"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fYour version is up to date"));
            }
            return true;

        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fThere was a problem checking the updates."));
            return false;
        }
    }


}
