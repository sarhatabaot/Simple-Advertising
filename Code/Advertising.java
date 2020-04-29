package com.Moshu.SimpleAdvertising;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.earth2me.essentials.Essentials;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Advertising implements CommandExecutor {
	
	private static Main plugin;
	
	public Advertising(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public static String currentad = "none";
	
	public static String getCurrentAd()
	{
		return currentad;
	}
	
	public static String currentpl = "none";
	
	public static String getCurrentAdPlayer()
	{
		return currentpl;
	}
	
	public static boolean activead = false;
	
	public static boolean isAnAdLive()
	{
		return activead;
	}
	
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
	String prefix; 
	public static ArrayList<String> cooldown = new ArrayList<String>();
	Economy econ;
	int cooldownTime;
	int price;
	String noMoney;
	String cooldownMessage;
	String succes;
	String advertisingFormat;
	String noPoints;
	
	int id;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		RegisteredServiceProvider rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);  
	    econ = (Economy)rsp.getProvider();
		prefix = plugin.getConfig().getString("messages.prefix");
	    
		if(!(sender instanceof Player))
		{
			Utils.sendNotPlayer(); 
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.hasPermission("simplead.ad"))
		{
			Utils.sendNoAccess(p);
			Utils.sendSound(p);
			return true;
		}
		
		if(plugin != null)
		{
			
			cooldownTime = plugin.getConfig().getInt("advertising.cooldown") * 20;
			price = plugin.getConfig().getInt("advertising.price");
			noMoney = plugin.getConfig().getString("messages.no-money");
			noPoints = plugin.getConfig().getString("messages.no-points");
			cooldownMessage = plugin.getConfig().getString("messages.cooldown-message");
			succes = plugin.getConfig().getString("messages.succes");
			advertisingFormat = plugin.getConfig().getString("messages.format");
			
		}
		
		if(args.length == 0)
		{
		    Utils.createInv(p);	
		    Utils.sendSound(p);
			return true;
		}
		
		if(args.length >= 1)
		{
			
			if(args[0].equalsIgnoreCase("help"))
       	    {
				
            if(!p.hasPermission("simplead.help"))
            {
            	Utils.sendNoAccess(p);
            	Utils.sendSound(p);
            	return true;
            }
            
            if(args.length > 1)
            {
            	return true;
            }
            
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "       &8&l---------- &f&l( &bSimpleAdvertising &f&l) &8&l----------"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l- &bCommands:"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8* &c/ad (Message) &l&8» &bMakes a public ad"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8* &c/ad reload &l&8» &bReloads the plugin"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8* &c/ad help &l&8» &bShows this page"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8* &c/ad debug &l&8» &bShows debug info"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8* &c/broadcast &l&8» &bBroadcast using this plugin"));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFor further help check out this page: &6https://www.spigotmc.org/resources/simple-advertising.40414/"));
       		  Utils.sendSound(p);
       		  return true;
       	  }
			
			else if(args[0].equalsIgnoreCase("debug"))
			{
				
				if(!p.hasPermission("simplead.debug"))
				{
					Utils.sendNoAccess(p);
					Utils.sendSound(p);
					return true;
				}
				
				Debug.sendServerInfo(p);
				
			}
  
		
		else if(args[0].equalsIgnoreCase("bal") || args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("points"))
		{
			if(!p.hasPermission("simplead.points"))
            {
            	Utils.sendNoAccess(p);
            	Utils.sendSound(p);
            	return true;
            }
            
            if(args.length > 1)
            {
            	return true;
            }
            
            String str = plugin.getConfig().getString("messages.balance");
            int bl = AdvertisingPoints.lookPoints(p);
            String s = Integer.toString(bl);
            str = str.replace("{points}", s);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + str));
            return true;
            
		}

        	
      	 else if(args[0].equalsIgnoreCase("reload"))
               {
        		 if(!p.hasPermission("simplead.reload"))
                 {
                 	Utils.sendNoAccess(p);
                 	Utils.logToFile( format.format(date) + " - " + "Reload > " +  sender.getName() + " tried to reload but didn't have permission");
                 	return true;
                 }
        		 
                 if(args.length != 1)
                 {
                 	return true;
                 }
                 
                 plugin.getPluginLoader().disablePlugin(plugin); 
                 plugin.getPluginLoader().enablePlugin(plugin);
                 plugin.saveDefaultConfig();
                 plugin.reloadConfig();
               
                 
                Main.reloadFiles();
              
                plugin.saveConfig();  
                
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l(&cAdvertising&f&l) &fSuccesfully reloaded."));
                
                Utils.logToFile(format.format(date) + " - " + "Reload > " +  sender.getName() + " has reloaded the plugin.");
                Utils.sendSound(p);
                
                    return true;
               }
               	                        	
        if(plugin.getConfig().getString("advertising.economy").equalsIgnoreCase("money"))
        {
        	
        if(Bukkit.getPluginManager().getPlugin("Vault") == null)
        {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7&oYou don't own a required dependency: &fVault"));
        	return true;
        }
           
      	if(cooldown.contains(p.getName())) 
      	{
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cooldownMessage));
          Utils.sendSound(p);
          return true; 
        }
           	
      	double b = (econ.getBalance(p.getName()));
      	
      	if(b < price)
      	{
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noMoney));
          	Utils.logToFile(format.format(date) + " - " + "Warn > " + sender.getName() + " didn't have enough money.");
            return true;
        } 
      	
      	if(activead && !plugin.getConfig().get("advertising.stay").equals("permanent"))
      	{
      		p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.active-ad")));
      		return true;
      	}
		
      	   @SuppressWarnings("deprecation")
      	   
			EconomyResponse r = econ.withdrawPlayer(p.getName(), price);	
      	   
              if (r.transactionSuccess()) 
              {           
          	  
            	  final 
            	  String mesajf;
            	  String mesaj;
            	  mesaj = "";
            		for (String a : args)
            		{
            		     mesaj += a + " ";
            		}
            		
            	  mesaj = mesaj.trim(); 
                 
            	  mesajf = mesaj;
            	  
            	  
            	  if(Utils.isInt(plugin.getConfig().getString("advertising.stay")))
            	  {
            	  if(plugin.getConfig().getInt("advertising.stay") > 0 && plugin.getConfig().getInt("advertising.repeat") > 0)
        	  	  {
        	  	
        	  		  id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        	  		 
        	  		  {
        	  			  
        	  		    	int i = 0;
        	  		
        	  			  public void run()
        	  			  {
        	  			  
        	  		
        		  			  if(i <= plugin.getConfig().getInt("advertising.stay") / plugin.getConfig().getInt("advertising.repeat"))
        		  			  {
        		  			  
        		      			  for (Player player : Bukkit.getOnlinePlayers()) 
        				              {
        					            
        				                advertisingFormat = advertisingFormat.replace("{message}", mesajf);
        				                advertisingFormat = advertisingFormat.replace("{player}", p.getName());
        				                advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
        				                player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
        				                
        				              }
        		      			  i++;
        		  			  }
        		  			  else
        		  			  {
        		  				  Bukkit.getScheduler().cancelTask(id);
        		  			  }
        	  			  }
        	  			  
        	  		  }, 0, plugin.getConfig().getInt("advertising.repeat") * 20); 
            		  
            	  }
            	  else
            	  {
            		  
		              for (Player player : Bukkit.getOnlinePlayers()) 
		              {
			            
		                advertisingFormat = advertisingFormat.replace("{message}", mesaj);
		                advertisingFormat = advertisingFormat.replace("{player}", p.getName());
		                advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
		                player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
		                
		              }
              
            	  }
            	  }
            		else
        	      	{
        	      		for (Player player : Bukkit.getOnlinePlayers()) 
        	            {
        		            
        	              advertisingFormat = advertisingFormat.replace("{message}", mesaj);
        	              advertisingFormat = advertisingFormat.replace("{player}", p.getName());
        	              advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
        	              player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
        	              
        	            }
        	      	}
              
              currentad = mesaj;
              currentpl = p.getName();
                  
              UUID uuid = p.getUniqueId();
              
              Utils.addToData(uuid, mesaj);
              
                  String priceToString = Integer.toString(price);
                  succes = succes.replace("{price}", priceToString);
                  p.sendMessage(ChatColor.translateAlternateColorCodes('&', succes));
                  
                  if(plugin.getConfig().getString("enable.logging").equalsIgnoreCase("true"))
                  {
                  Utils.logToFile(format.format(date) + " (Advertising) " + mesaj + " . Made by: " + p.getName());
                  }
                  
	              cooldown.add(p.getName());
       
	                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
	                {
	                	public void run() {
	                		cooldown.remove(p.getName());		
	                	}
	                }, cooldownTime);      
	                
	                activead = true;
	                
	                if(plugin.getConfig().getInt("advertising.stay") > 0)
	                {
	                	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
		                {
		                	public void run() {
		                		activead = false;
		                	}
		                }, plugin.getConfig().getInt("advertising.stay") * 20);    
	                }
                 
                  Utils.sendSound(p);
              
              } 
              else 
              {
                  
              	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSomething went wrong.."));
              	econ.depositPlayer(p.getName(), price); //Traiasca spookie
                  return true;
      
              }
        }
        else if(plugin.getConfig().getString("advertising.economy").equalsIgnoreCase("points"))
        {
        	if(cooldown.contains(p.getName())) 
          	{
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + cooldownMessage));
              Utils.sendSound(p);
              return true; 
            }
        	
        	UUID uuid = p.getUniqueId();
        	
        	int bal = plugin.getData().getInt(uuid + ".points");
        	
        	if(price > bal)
        	{
        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noMoney));
              	Utils.logToFile(format.format(date) + " - " + "Warn > " + sender.getName() + " didn't have enough points.");
        		return true;
        	}
        	
        	if(activead && !plugin.getConfig().get("advertising.stay").equals("permanent"))
          	{
          		p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.active-ad")));
          		return true;
          	}
        	
        	AdvertisingPoints.takePoints(p, price);
        	final String mesajf;
        	String mesaj;
      	     mesaj = "";
      		for (String a : args)
      		{
      		     mesaj += a + " ";
      		}
      		
      	  mesaj = mesaj.trim(); 
	           
	      	mesajf = mesaj;
	  	  
	      	if(Utils.isInt(plugin.getConfig().getString("advertising.stay")))
      	  {
	  	  if(plugin.getConfig().getInt("advertising.stay") > 0 && plugin.getConfig().getInt("advertising.repeat") > 0)
	  	  {
	  	
	  		  id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	  		 
	  		  {
	  			  
	  		    	int i = 0;
	  		
	  			  public void run()
	  			  {
	  			  
	  		
		  			  if(i <= plugin.getConfig().getInt("advertising.stay") / plugin.getConfig().getInt("advertising.repeat"))
		  			  {
		  			  
		      			  for (Player player : Bukkit.getOnlinePlayers()) 
				              {
					            
				                advertisingFormat = advertisingFormat.replace("{message}", mesajf);
				                advertisingFormat = advertisingFormat.replace("{player}", p.getName());
				                advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
				                player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
				                
				              }
		      			  i++;
		  			  }
		  			  else
		  			  {
		  				  Bukkit.getScheduler().cancelTask(id);
		  				  return;
		  			  }
	  			  }
	  			  
	  		  }, 0, plugin.getConfig().getInt("advertising.repeat") * 20); 
	  		  
	  	  }
	  	  else
	  	  {
	  		  
	            for (Player player : Bukkit.getOnlinePlayers()) 
	            {
		            
	              advertisingFormat = advertisingFormat.replace("{message}", mesaj);
	              advertisingFormat = advertisingFormat.replace("{player}", p.getName());
	              advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
	              player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
	              
	            }
	    
	            
	  	  }
	  	  }
	      	else
	      	{
	      		for (Player player : Bukkit.getOnlinePlayers()) 
	            {
		            
	              advertisingFormat = advertisingFormat.replace("{message}", mesaj);
	              advertisingFormat = advertisingFormat.replace("{player}", p.getName());
	              advertisingFormat = advertisingFormat.replace("{prefix}", prefix);
	              player.sendMessage(ChatColor.translateAlternateColorCodes('&', advertisingFormat));
	              
	            }
	      	}
	    
	    currentad = mesaj;
	    currentpl = p.getName();
        
        Utils.addToData(uuid, mesaj);
        
            String succesPuncte = plugin.getConfig().getString("messages.succes-points");
            String priceToString = Integer.toString(price);
            succesPuncte = succesPuncte.replace("{price}", priceToString);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', succesPuncte));
            if(plugin.getConfig().getString("enable.logging").equalsIgnoreCase("true"))
            {
            Utils.logToFile(format.format(date) + " (Advertising) " + mesaj + " . Made by: " + p.getName());
            }
            cooldown.add(p.getName());
 
              Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
              {
              	public void run() {
              		cooldown.remove(p.getName());		
              	}
              }, cooldownTime);        
              
              activead = true;
              
              if(plugin.getConfig().getInt("advertising.stay") > 0)
              {
              	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
	                {
	                	public void run() {
	                		activead = false;
	                	}
	                }, plugin.getConfig().getInt("advertising.stay"));    
              }
           
            Utils.sendSound(p);
        	
        	return true;
        }
        
        else
        {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7&oValid economy types are &fpoints &7&oand &fmoney"));
        	return true;
        }
        
		}
		
		return true;
	}

}
