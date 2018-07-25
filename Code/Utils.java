package com.Moshu.SimpleAdvertising;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class Utils {

	private static Main plugin;
	
	public Utils(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public static void createInv(Player p)
	{
	
		String title = plugin.getConfig().getString("gui.inventory-name");
		String item = plugin.getConfig().getString("gui.item");
		String name = plugin.getConfig().getString("gui.item-name");
		int price = plugin.getConfig().getInt("advertising.price");
		
		if(Material.matchMaterial(item) == null)
		{
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError: &fItem material name is invalid"));
		return;
		}

		Material m = Material.matchMaterial(item);
		
		if(!p.hasPermission("simplead.admin"))
		{
		
		Inventory inv = Bukkit.createInventory(null, 9, title);
		
		ItemStack itm = new ItemStack(m);
		ItemMeta meta = itm.getItemMeta();
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		
		List<String> strings = new ArrayList<>();
		
		for (String string : plugin.getConfig().getStringList("gui.item-lore")) 
				{
			String pret = Integer.toString(price);
			string = string.replace("{price}", pret);
		    strings.add(ChatColor.translateAlternateColorCodes('&', string));
		}
		
		meta.setLore(strings);
		
		itm.setItemMeta(meta);
		
		inv.setItem(4, itm);
		
		p.openInventory(inv);
		
		return;
		}
		else
		{
			
			Inventory inv = Bukkit.createInventory(null, 9, title);
			
			ItemStack itm = new ItemStack(m);
			ItemMeta meta = itm.getItemMeta();
			
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

			List<String> strings = new ArrayList<>();
			for (String string : plugin.getConfig().getStringList("gui.item-lore")) 
					{
				String pret = Integer.toString(price);
				string = string.replace("{price}", pret);
			    strings.add(ChatColor.translateAlternateColorCodes('&', string));
			}
			
			meta.setLore(strings);
			
			itm.setItemMeta(meta);
			
			inv.setItem(4, itm);
			
			p.openInventory(inv);
			
			//Insert admin panel here
		}
		
	}
		
	public static void addToData(UUID uuid, String ad)
	{
		int ads = plugin.getData().getInt(uuid + ".ads-created") + 1;
		plugin.getData().set(uuid + ".lastest-ad", ad);
		plugin.getData().set(uuid + ".ads-created", ads);
	}
	
	public static void sendSound(Player p)
	{
		if(plugin.getConfig().getString("enable.sounds").equalsIgnoreCase("true"))
        {
			try {
						
            p.playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("advertising.sound")), 1, 1F);
        
			}
			catch(Exception e)
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError: &fInvalid sound. Use one from here: &chttps://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lOops! &7The sound in your config is invalid, check again."));	
			}
        }
      
	}
	
	public static String getIp(Player p) {
        String ipAddr = p.getAddress().getHostName();
        return ipAddr;
    }
	
	public static void sendNoAccess(Player p)
	{
		int fadein = plugin.getConfig().getInt("titles.fade-in");
		int stay = plugin.getConfig().getInt("titles.stay");
		int fadeout = plugin.getConfig().getInt("titles.fade-out");
		
		String i = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission.title"));
		String m = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission.subtitle"));
		sendSound(p);
		p.sendTitle(i, m, fadein, stay, fadeout);
	}
	
	public static void sendNotPlayer(CommandSender sender)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* &fYou need to be a player to acces this command"));
	}
	
	public static void sendTargetNull(Player p)
	{
		String k = plugin.getConfig().getString("messages.target-null.title");
		String c = plugin.getConfig().getString("messages.target-null.subtitle");
		
		int fadein = plugin.getConfig().getInt("titles.fade-in");
		int stay = plugin.getConfig().getInt("titles.stay");
		int fadeout = plugin.getConfig().getInt("titles.fade-out");
		
		String i = ChatColor.translateAlternateColorCodes('&', k);
		String m = ChatColor.translateAlternateColorCodes('&', c);
		sendSound(p);
		p.sendTitle(i, m, fadein, stay, fadeout);
	}
	
	public void createConfig() { 
	    try {
	        if (!plugin.getDataFolder().exists()) {
	        	plugin.getDataFolder().mkdirs();
	        }
	        File file = new File(plugin.getDataFolder(), "config.yml");
	        if (!file.exists()) {
	        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* &cConfig.yml &fnot found, creating"));
	        	plugin.saveDefaultConfig();
	        } else {
	        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* &cConfig.yml &ffound, loading"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();

	    }

	}
 
 public static void logToFile(String message)
    
    {
 if (plugin.getConfig().getString("enable.logging").equalsIgnoreCase("true")) {
        try
        {
            File dataFolder = plugin.getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }
 
            File saveTo = new File(plugin.getDataFolder(), "logs.txt");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }
 
 
            FileWriter fw = new FileWriter(saveTo, true);
 
            PrintWriter pw = new PrintWriter(fw);
 
            pw.println(message);
 
            pw.flush();
 
            pw.close();
 
        } catch (IOException e)
        {
 
            e.printStackTrace();
        } 
        	
                   
        }
 
    }
 
	
}
