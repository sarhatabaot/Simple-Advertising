package com.Moshu.SimpleAdvertising;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Events implements Listener {

	public static Main plugin;
	
	public Events(Main plugin)
	{
		this.plugin = plugin;
	}
	
	static File dataf;
	Economy econ;
	
	String joinMessage;
	String quitMessage;
	String prefix;
	String ad;
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
	ArrayList<String> in = new ArrayList<String>();
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
    	if(plugin.getConfig().getString("enable.welcomers").equalsIgnoreCase("true"))
    	{
    	 joinMessage = plugin.getConfig().getString("messages.join");
    	 joinMessage = joinMessage.replace("{player}", e.getPlayer().getName());
		 e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
		 Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
    	} else
    	{
    		return;
    	}
    	
    	if(!e.getPlayer().hasPlayedBefore() || plugin.getData().getString(e.getPlayer().getUniqueId().toString()) == null)
		{	
			
			String uuid = e.getPlayer().getUniqueId().toString();
			plugin.getData().addDefault(uuid, "");	   
			plugin.getData().addDefault(uuid + ".default-name", e.getPlayer().getName());
			plugin.getData().addDefault(uuid + ".ip", Utils.getIp(e.getPlayer()));    
			plugin.getData().addDefault(uuid + ".points", plugin.getConfig().getInt("points.default-balance"));  
			plugin.getData().addDefault(uuid + ".latest-ad", "");
			plugin.getData().addDefault(uuid + ".ads-created", 0);
						
			dataf = new File(plugin.getDataFolder(), "data.yml");

				try {
					plugin.getData().save(dataf);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
             return;
				
		}
		
	}
	
	@EventHandler
	 public void onPlayerQuit(PlayerQuitEvent e)
	 {
		if(plugin.getConfig().getString("enable.welcomers").equalsIgnoreCase("true"))
    	{
			quitMessage = plugin.getConfig().getString("messages.quit");
	    	quitMessage = quitMessage.replace("{player}", e.getPlayer().getName());
 	 e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitMessage));
 	 Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', quitMessage));
    	} else
    	{
    		return;
    	}
	 
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		RegisteredServiceProvider rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);  	 
	    econ = (Economy)rsp.getProvider();
	    
	    String tf = plugin.getConfig().getString("messages.transaction-cancelled");
	    int price = plugin.getConfig().getInt("advertising.price");
	    String succes = plugin.getConfig().getString("messages.succes");
	    int cooldownTime = plugin.getConfig().getInt("advertising.cooldown") * 20;
		
		if(in.contains(p.getName()))
		{
			e.setCancelled(true);
			
			if(e.getMessage().equalsIgnoreCase("cancel"))
			{
				in.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + tf));
				Utils.sendSound(p);
				return;
			}
			
			if(plugin.getConfig().getString("advertising.economy").equalsIgnoreCase("money"))
			{
			
			EconomyResponse r = econ.withdrawPlayer(p.getName(), price);		  
            if (r.transactionSuccess()) 
            {     
			
			String mesaj = e.getMessage();
			String advertisingFormat = plugin.getConfig().getString("messages.format");
			
			for(Player k : Bukkit.getOnlinePlayers())
			{
				advertisingFormat = advertisingFormat.replace("{message}", mesaj);
				advertisingFormat = advertisingFormat.replace("{player}", p.getName());
				advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
				k.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
			}
			
			String priceToString = Integer.toString(price);
            succes = succes.replace("{price}", priceToString);
            
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));
            
            if(plugin.getConfig().getString("enable.logging").equalsIgnoreCase("true"))
            {
            Utils.logToFile(format.format(date) + " (Advertising) " + mesaj + " . Made by: " + p.getName());
            }
            
            Advertising.cooldown.add(p.getName());
            in.remove(p.getName());
 
              Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
              {
              	public void run() {
              		Advertising.cooldown.remove(p.getName());		
              	}
              }, cooldownTime * 20);                  
           
              if(plugin.getConfig().getString("enable.sounds").equalsIgnoreCase("true"))
              {
              Utils.sendSound(p);
              }
        
        } else 
        {
            
        	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSomething went wrong.."));
        	econ.depositPlayer(p.getName(), price); //Traiasca spookie
            return;

        }
			
            }
			else if(plugin.getConfig().getString("advertising.economy").equalsIgnoreCase("points"))
			{
				UUID uuid = p.getUniqueId();
	        	
	        	AdvertisingPoints.takePoints(p, price);
	        	
	        	String advertisingFormat = plugin.getConfig().getString("messages.format");
	        	String mesaj = e.getMessage();
	           
	        for (Player player : Bukkit.getOnlinePlayers()) 
	        {
	          
	          advertisingFormat = advertisingFormat.replace("{message}", mesaj);
	          advertisingFormat = advertisingFormat.replace("{player}", p.getName());
	          advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
	          player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
	          
	        }

	        
	        Utils.addToData(uuid, mesaj);
	        
	        String succesPuncte = plugin.getConfig().getString("messages.succes-points");
            String priceToString = Integer.toString(price);
            succesPuncte = succesPuncte.replace("{price}", priceToString);
	            p.sendMessage(ChatColor.translateAlternateColorCodes('&', succesPuncte));
	            if(plugin.getConfig().getString("enable.logging").equalsIgnoreCase("true"))
	            {
	            Utils.logToFile(format.format(date) + " (Advertising) " + mesaj + " . Made by: " + p.getName());
	            }
	            Advertising.cooldown.add(p.getName());
	            in.remove(p.getName());
	 
	              Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
	              {
	              	public void run() {
	              		Advertising.cooldown.remove(p.getName());		
	              	}
	              }, cooldownTime);                  
	           
	            Utils.sendSound(p);
				
				
				return;
			}
		}
		}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
        
		RegisteredServiceProvider rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);  	 
	    econ = (Economy)rsp.getProvider();
		
		String title = plugin.getConfig().getString("gui.inventory-name");
		String dialog = plugin.getConfig().getString("messages.dialog");
		int price = plugin.getConfig().getInt("advertising.price");
		prefix = plugin.getConfig().getString("messages.prefix");
		String noMoney = plugin.getConfig().getString("messages.failure");
		String noPoints = plugin.getConfig().getString("messages.no-points");
	    Player p = (Player) e.getWhoClicked();
		
	    if(e.getClickedInventory() == null)
	    {
	    	return;
	    }
	    
		if(e.getClickedInventory().getTitle().equals(title))
		{
			e.setCancelled(true);
			
			if((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR)))
			{
		    return;
			}
			
			if(e.getSlot() == 4)
			{
				Utils.sendSound(p);
				if(Advertising.cooldown.contains(p.getName()))
				{
					String cooldownMessage = plugin.getConfig().getString("messages.cooldown-message");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cooldownMessage));
					return;
				}
				
				 if(plugin.getConfig().getString("advertising.economy").equalsIgnoreCase("money"))
			        {
			        	
			        if(Bukkit.getPluginManager().getPlugin("Vault") == null)
			        {
			        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7&oYou don't own a required dependency: &fVault"));
			        	return;
			        }
			        
			        double b = (econ.getBalance(p.getName()));
			      	
			      	if(b < price)
			      	{
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noMoney));
			          	Utils.logToFile(format.format(date) + " - " + "Warn > " + p.getName() + " didn't have enough money.");
			            return;
			        } 
	
				in.add(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + dialog));
				p.closeInventory();
				return;
				
			     }
				 
				 else if(plugin.getConfig().getString("advertising.economy").equalsIgnoreCase("points"))
					 
				 {
					 
					 if(AdvertisingPoints.lookPoints(p) < price)
					 {
						 p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPoints));
						 Utils.logToFile(format.format(date) + " - " + "Warn > " + p.getName() + " didn't have enough points.");
				         return;
					 }
					 
					    in.add(p.getName());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + dialog));
						p.closeInventory();
					 
					 return;
				 }
			}
			 
		}
		return;
	}
	
}
