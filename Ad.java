
//ATTENTION! THE FOLLOWING CODE IS NOT WORKING PROPERLY

package com.SimpleAd.MoshuAd;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.earth2me.essentials.Essentials;

import me.clip.placeholderapi.PlaceholderAPI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import com.SimpleAd.MoshuAd.DateClass;
import com.SimpleAd.MoshuAd.Broadcast;
import com.SimpleAd.MoshuAd.Title;
import com.SimpleAd.MoshuAd.Points;

import org.bukkit.plugin.Plugin;


public class Ad extends JavaPlugin implements Listener, CommandExecutor



{ 
	        public static Plugin plugin;
		    Economy econ;
	        int price;	  	   
	        double bizprice;
	        Essentials e;
	        int bizbal;
            String prefix;
            String succes;
            String failure;
            String failure2;
            String contact;
            String mcolor;
            String dolar;
            String reloadName;
            String reloadPhrase;
            String acolor;
            String noperm;
            String cd;
            int cdint;
            String TNP;
            Date date = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
			String you;
			String afterYou;
			String msgColor;
			String newMsg;
			String beforeName;
			String nopermMsg;
			String puncte;
			String usage;
			int points;
			String pointssign;
			String failurePoints;
			String succesPoints;
			String joinMessage;
			
			final FileConfiguration config = this.getConfig();
			
			String helpHeader;
			String helpCommands;
			String help1;
			String help2;
			String help3;
			String help4;
			String help5;
			String help6;
			String helpSpigot;

	    public void Business() 
	    { 
	            this.price = this.getConfig().getInt("price");	     
	            this.e = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	            this.bizbal = this.getConfig().getInt("bizbal");
                this.prefix = this.getConfig().getString("prefix");
                this.succes = this.getConfig().getString("succes");
                this.failure = this.getConfig().getString("failure");
                this.failure2 = this.getConfig().getString("nomessage");
                this.contact = this.getConfig().getString("aftermessage");
                this.mcolor = this.getConfig().getString("messagecolor");
                this.dolar = this.getConfig().getString("aftermoney");
                this.reloadName = this.getConfig().getString("reloadName");
                this.reloadPhrase = this.getConfig().getString("reloadPhrase");
                this.acolor = this.getConfig().getString("authorColor");
                this.noperm = this.getConfig().getString("noPermissionMessage");
                this.cd = this.getConfig().getString("cooldownMessage");
                this.cdint = this.getConfig().getInt("cooldown");             
                this.TNP = this.getConfig().getString("TimeNoPermission");
                this.you = this.getConfig().getString("you");
                this.afterYou = this.getConfig().getString("afterYou");
                this.msgColor = this.getConfig().getString("msgColor");
                this.newMsg = this.getConfig().getString("customMessage");
                this.beforeName = this.getConfig().getString("beforeName");
                this.nopermMsg = this.getConfig().getString("NoPermissionMessage");
                this.puncte = this.getConfig().getString("separatorForMessage");
                this.usage = this.getConfig().getString("usage");
                this.points = this.getConfig().getInt("points");
                this.pointssign = this.getConfig().getString("pointsName");
                this.failurePoints = this.getConfig().getString("failurePoints");
                this.succesPoints = this.getConfig().getString("succesPoints");
                this.joinMessage = this.getConfig().getString("joinMessage");
                this.help1 = this.getConfig().getString("helpCommand");
                this.helpCommands = this.getConfig().getString("helpCommands");
                this.helpHeader = this.getConfig().getString("helpHeader");
                this.help2 = this.getConfig().getString("helpCommand2");
                this.help3 = this.getConfig().getString("helpCommand3");
                this.help4 = this.getConfig().getString("helpCommand4");
                this.help5 = this.getConfig().getString("helpCommand5");
                this.help6 = this.getConfig().getString("helpCommand6");
                this.helpSpigot = this.getConfig().getString("helpSpigot");

	    }
        
	     
	public void onEnable() //Cand se porneste pluginu
	{
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

	        	logger.info(pdfFile.getName() + " was enabled! (Version " + pdfFile.getVersion() + ")");
                System.out.println("Made By Moshu, this is the real deal!");
	           	
                RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
	           	 
                econ = (Economy)rsp.getProvider();
		       
                //Calling the methods
                 
                  Business();
                  createConfig();
                  hookPlayerPoints();
                  
                //Get Commands   
                 
                  if(this.getConfig().getString("enableDate").equalsIgnoreCase("true"))
                  {
                  this.getCommand("date").setExecutor(new DateClass());
                  } else {
                	  logger.info("Date functionality disabled");
                  }
                 
                  this.getCommand("adb").setExecutor(new Broadcast());
                  this.getCommand("adt").setExecutor(new Title());
                  this.getCommand("adp").setExecutor(new Points());
                  
                  //Looking for PlaceholderAPI                
                  
                  if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                  {
                	  Bukkit.getPluginManager().registerEvents(this, this);
                	  logger.info("Hooked into PlaceholderAPI");
                	  
                  } else {
                	  throw new RuntimeException("Could not hook into PlaceholderAPI. Sad");

                  }
                  
                 
                  if(this.getConfig().getString("usePlayerPoints").equalsIgnoreCase("true"))
                  {
                	  logger.info("Hooked into PlayerPoints");
                  } else {
                	  logger.info("Didn't hook into PlayerPoints");
                  }
                  
                  
                  if (this.getConfig().getString("logging").equalsIgnoreCase("false")) {               	
                  logger.info("Logging is disabled");           
                  } 
                 
	}
	

	public void onDisable() //Cand se opreste pluginu
	{
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " was disabled! (Version " + pdfFile.getVersion() + ")");
		 logToFile(format.format(date) + " - " +  " Plugin is disabling.");

	}

	//Hook for player points
	
	private PlayerPoints playerPoints;
    Ad plugin123;
	
	private boolean hookPlayerPoints() {
	    final Plugin plugin1 = plugin123.getServer().getPluginManager().getPlugin("PlayerPoints");
	    playerPoints = PlayerPoints.class.cast(plugin1);
	    if(plugin123 == null) 
	    {
	    	System.out.println("Plugin is not null");
	    }
	    if (plugin123.getConfig().getString("debug").equalsIgnoreCase("true"))
	    		{
	    	logToFile(format.format(date) + "DEBUG: PlayerPoints working fine.");
	   
	    }
	    return playerPoints != null;
	}

	public PlayerPoints getPlayerPoints() {
	    return playerPoints;
	}
	
	
	//Join Event for messages
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event)
	{
		
		
		if(this.getConfig().getString("toEveryone").equalsIgnoreCase("true"));
		{
		
		for(Player all : Bukkit.getServer().getOnlinePlayers())
		{
			
			String withoutPlaceholdersSet1 = this.joinMessage;
			String PlaceholdersSet2 = PlaceholderAPI.setPlaceholders(event.getPlayer(), withoutPlaceholdersSet1);
			String PlaceholdersSet = PlaceholderAPI.setPlaceholders(event.getPlayer(), PlaceholdersSet2);
			all.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholdersSet));
		
			
			if(this.getConfig().getString("debug").equalsIgnoreCase("true"))
        	{
				logToFile(format.format(date) + "DEBUG: Join Message working (Sent to everyone)");
        	}
			   

		}

		
	   if(this.getConfig().getString("toEveryone").equalsIgnoreCase("false"))
	  {
		
		String noPlaceholdersSet = this.joinMessage;
		String PlaceholdersSet = PlaceholderAPI.setPlaceholders(event.getPlayer(), noPlaceholdersSet);
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', PlaceholdersSet));
		event.setJoinMessage(PlaceholdersSet);
		
		if(this.getConfig().getString("debug").equalsIgnoreCase("true"))
    	{
			logToFile(format.format(date) + "DEBUG: Join Message working (Sent to player)");
    	}
		
		
	  }
	}
	}
			
    
	public boolean hasTime(Player player) {
        if (player.hasPermission("simplead.datecheck")) {
            return true;
        } else if  (player.hasPermission("simplead.*")) {
            return true;
        }
        return false;
    }

	//Reload Command
	

	        
	         //The Permission
	         
	         public boolean hasReload(Player player) {
	             if (player.hasPermission("simplead.reload")) {
	                 return true;
	             } else if  (player.hasPermission("simplead.*")) {
	                 return true;
	             }
	             return false;
	         }
	         
	         //Help Permission
	         
	         public boolean hasHelp(Player player) {
	             if (player.hasPermission("simplead.help")) {
	                 return true;
	             } else if  (player.hasPermission("simplead.*")) {
	                 return true;
	             }
	             return false;
	         }

	         //Permission for ad
	         
	         public boolean hasAd(Player player) {
	             if (player.hasPermission("simplead.ad")) {
	                 return true;
	             } else if  (player.hasPermission("simplead.*")) {
	                 return true;
	             }
	             return false;
	         }	
	
	         //Permission for broadcast
	         
	         public boolean hasBc(Player player) {
	             if (player.hasPermission("simplead.bc")) {
	                 return true;
	             } else if  (player.hasPermission("simplead.*")) {
	                 return true;
	             }
	             return false;
	         }	
	         
	         //Permission for titles
	         
	         public boolean hasTitle(Player player) {
	             if (player.hasPermission("simplead.adt")) {
	                 return true;
	             } else if  (player.hasPermission("simplead.*")) {
	                 return true;
	             }
	             return false;
	         }	
	
	//Creation of Config
	
        private void createConfig() { 
    try {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            getLogger().info("Config.yml not found, creating!");
            saveDefaultConfig();
        } else {
            getLogger().info("Config.yml found, loading!");
        }
    } catch (Exception e) {
        e.printStackTrace();

    }

}

   //Creation of log file
    
        public void logToFile(String message)
        
        {
     if (this.getConfig().getString("logging").equalsIgnoreCase("true")) {
            try
            {
                File dataFolder = getDataFolder();
                if(!dataFolder.exists())
                {
                    dataFolder.mkdir();
                }
     
                File saveTo = new File(getDataFolder(), "logs.txt");
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
        //The main command
        
	 @SuppressWarnings("unused")
	private boolean setupEconomy()  //Aici fac economia
	 {
	        if (this.getServer().getPluginManager().getPlugin("Vault") == null) 
	        {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	         econ = (Economy)rsp.getProvider();
	        if (econ != null) {
	            return true;
	        }
	        return false;
	 } 
	 
	 //ArrayList for cooldowns
	 
	 ArrayList<Player> cooldown = new ArrayList<Player>();
	
	 //Listener Class
	 
	 public boolean hasBroadcast(Player player) {
         if (player.hasPermission("simplead.bc")) {
             return true;
         } else if  (player.hasPermission("simplead.*")) {
             return true;
         }
         return false;
	 }
	
     @SuppressWarnings("unused")
	@Override
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) //Aci de /ad-ul, adica comanda initiala
	
	 { 	     	 
    	int lungime = args.length;
		Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("ad") && sender instanceof Player) 
        {
        	
        	logToFile(format.format(date) + "DEBUG: Main Command registering");
        	//Help Command
        	
        	if(args[0].equalsIgnoreCase("help") && sender.hasPermission("simplead.help"))
       	  {

       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.helpHeader));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.helpCommands));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help1));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help2));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help3));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help4));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help5));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help6));
       		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.helpSpigot));
       		  
       	  }
        	
        	//Reload Command
        	
        	   if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("simplead.reload"));
               {
              
               	                         Player plaier = (Player)sender;
               	                         if (hasReload(plaier)) {
               	                        	 this.getPluginLoader().disablePlugin(this); 
               	                        	 this.getPluginLoader().enablePlugin(this);
               	                        	 this.saveDefaultConfig();
               	                        	 this.reloadConfig();
               	                 	         
               	                 	        if (this == null)
                                      		 {
                                      	     System.out.println("Plugin is null");
                                      		 }
               	                 	        
               	                         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f &aSimple Advertising (By Moshu) 1.7 was reloaded!"));
               	                         logToFile(format.format(date) + " - " + "Reload > " +  sender.getName() + " has reloaded the plugin.");
               	                      

               	                     } else {
               	                         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f> &cYou don't have permission!"));
               	                         logToFile( format.format(date) + " - " + "Reload > " +  sender.getName() + " tried to reload but didn't have permission");

               	                 
               	                 }
               	                 
               	         
               	         }
               	      	
        	if (cooldown.contains(player)) 
        	{
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.cd ));
            return true; 
        
        }
        
            OfflinePlayer p2 = (OfflinePlayer) sender;        	
        	double b = (econ.getBalance(sender.getName()));
        	if(b < this.price)
        	{
        		StringBuilder sb = new StringBuilder();
        		sb.append("");
        		sb.append(b);
        		String strI = sb.toString();
        	    player.sendMessage(ChatColor.translateAlternateColorCodes('&', strI));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.failure));
            	logToFile(format.format(date) + " - " + "Warn > " + sender.getName() + " didn't have enough money.");
        
            } 
		
        	   @SuppressWarnings("deprecation")
        	   
			EconomyResponse r = econ.withdrawPlayer(sender.getName(), this.price);		
                Player bahoi2 = (Player)sender;
        	    int i = 0;    
                if (r.transactionSuccess() && 1 < lungime) 
                {           
            	String mesaj = " ";
                while (i < lungime) {
                	mesaj = String.valueOf(mesaj) + args[i] + " ";
                    ++i;
                   }
                mesaj = mesaj.trim();
                for (@SuppressWarnings("unused") Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.mcolor + mesaj + this.contact + player.getName()));
            		String noPlaceholdersSet = this.joinMessage;
            		String PlaceholdersSet = PlaceholderAPI.setPlaceholders(player, noPlaceholdersSet);
                    logToFile(format.format(date) + " - " + mesaj + " . Made by: " + player.getName());
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.succes + this.price + this.dolar));
                logToFile(format.format(date) + " - " + player.getName() + " has succesfully made an Ad." );
                cooldown.add(player);
                
                if(this.getConfig().getString("debug").equalsIgnoreCase("true"))
            	{
               	logToFile(format.format(date) + "DEBUG: Economy part working");	
            	}
                
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                	public void run() {
                		cooldown.remove(bahoi2);
                	}
                }, this.cdint);                
                               
                return true;
                } else {
                    
                	player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.failure2));
                	econ.depositPlayer(sender.getName(), this.price); //Traiasca spookie
                return false;
        
                }     
        	
      
        	} else {
        		
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noperm));
        	
        	}
      
      
    	return true; 
    	  
	 }
}
        
       

 


   
   







//Made by Moshu with help from MosLaDatorie.
//Version Beta 1.7 (Not working)
