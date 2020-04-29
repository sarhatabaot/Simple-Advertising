package com.Moshu.SimpleAdvertising;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;

public class Updater implements Listener {

	private static Main plugin;
	
	public Updater(Main plugin)
	{
		this.plugin = plugin;
	}
	

	private String url = "https://api.spigotmc.org/legacy/update.php?resource=";
    private String id = "40414";

    private boolean isAvailable;

    public Updater()
    {
    	
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("simplead.admin") && plugin.getConfig().getBoolean("enable.updater") == true)
        {
            if(isAvailable)
            {
        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fAn update is ready for you:"));
        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&chttps://www.spigotmc.org/resources/simple-advertising.40414/updates"));
        Utils.sendSound(event.getPlayer());
            }
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
            if(raw.contains("-")) {
                remoteVersion = raw.split("-")[0].trim();
            } else {
                remoteVersion = raw;
            }

            if(!localVersion.equalsIgnoreCase(remoteVersion))
            {
            	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fAn update is ready for you:"));
            	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&chttps://www.spigotmc.org/resources/simple-advertising.40414/updates"));
                return true;
            }
            else
            {
            	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fYour version is up to date"));
            	return true;
            }

        } catch (IOException e) 
        {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate: &fThere was a problem checking the updates."));
            return false;
        }
    }
	
	
	
	
}
