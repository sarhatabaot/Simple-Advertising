package com.SimpleAd.MoshuAd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.command.CommandExecutor;
import com.earth2me.essentials.Essentials;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.plugin.Plugin;


public class Ad extends JavaPlugin 



{ 
	        public static Plugin plugin;
	        public static ReloadCommand ReloadCommand;
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

	    }
        
	     
	public void onEnable() //Cand se porneste pluginu
	{
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

	        	logger.info(pdfFile.getName() + " was enabled! (Version " + pdfFile.getVersion() + ")");
                System.out.println("Made By Moshu, this is the real deal!");
	           	RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
	              //new Bungee().Cacaness();
	           	  econ = (Economy)rsp.getProvider();
		          Business();
                  createConfig();
                  getCommand("adreload").setExecutor(new ReloadCommand());
                  getCommand("date").setExecutor(new TimeClass());
                  getCommand("msg").setExecutor(new Bungee());
                  plugin = this;
                  registerEvents(this, new ListenerClass()); 
                  this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");                 
                 
                  if (this.getConfig().getString("logging").equalsIgnoreCase("false")) {               	
                  logger.info("Logging is disabled");           
                  }
                 
                  if(this.getConfig().getString("bungeecordMsg").equalsIgnoreCase("true"))
                  {
                	  logger.info("BungeeCord Messaging enabled!");
                	  logger.info("Turn to false if you want to message only on one server");
                  }
	}
	

	public void onDisable() //Cand se opreste pluginu
	{
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " was disabled! (Version " + pdfFile.getVersion() + ")");
		 logToFile( format.format(date) + " - " +  " Plugin is disabling.");

	}

        private void createConfig() { //The creation of the config folder
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
    public class TimeClass implements CommandExecutor {  
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
        {
    		if (cmd.getName().equalsIgnoreCase("date") && sender.hasPermission("simplead.datecheck")) 
    		{    
    			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The time: &a" + format.format(date)));	
            	logToFile( format.format(date) + " - "  + sender.getName() + " viewed the date.");
				    						
    			} else 
    			{
    				Player p = (Player) sender;
    				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to view the date"));
                	logToFile( format.format(date) + " - " + "Warn > " + sender.getName() + " tried to view the date, but didn't have permission.");
    				
    			}
    		return true;
        }
    }
    
   
    
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


        public void registerEvents(org.bukkit.plugin.Plugin plugin, ListenerClass...ListenerClass ) { 
        	
            for (ListenerClass listener : ListenerClass) {
                Bukkit.getServer().getPluginManager(); 
            }
        }
      
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
	 
	 
	 ArrayList<Player> cooldown = new ArrayList<Player>();
	
	 
	 public class ListenerClass implements Listener { //Aici e clasa de Listener
		 		 		 
	 }
	 
	 class ReloadCommand implements CommandExecutor {  //Clasa de ReloadCommand ca sa mearga onCommand
	          
         public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        
        	 if(cmd.getName().equalsIgnoreCase("adreload")) {
                 if (args.length > 0) {
                     sender.sendMessage(ChatColor.RED + "To many arguments."); 
                     return false;
                 }
                 if (sender instanceof Player) {
                     Player player = (Player)sender;
                     if (hasReload(player)) {
                    	 plugin.getPluginLoader().disablePlugin(plugin); //Alta eroare
                         plugin.getPluginLoader().enablePlugin(plugin);
                    	 plugin.reloadConfig();
                         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f &aSimple Advertising (By Moshu) 1.5 was reloaded!"));
                         logToFile( format.format(date) + " - " + "Reload > " +  sender.getName() + " has reloaded the plugin.");
                     } else {
                         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f> &cYou don't have permission!"));
                         logToFile( format.format(date) + " - " + "Reload > " +  sender.getName() + " tried to reload but didn't have permission");
                         
                     }
                 } else {
                	 plugin.getPluginLoader().disablePlugin(plugin);
                     plugin.getPluginLoader().enablePlugin(plugin);
                	 plugin.reloadConfig();
                     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f &aSimple Advertising (By Moshu) 1.5 was reloaded!"));
                     logToFile( format.format(date) + " - " + "Reload > " +  sender.getName() + " has reloaded the plugin.");
                 
                 
                 }
                 
                 return false;
             }
             return false;
         }
      
         public boolean hasReload(Player player) {
             if (player.hasPermission("simplead.reload")) {
                 return true;
             } else if  (player.hasPermission("simplead.*")) {
                 return true;
             }
             return false;
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
	 
	 public boolean hasBungee(Player player) {
         if (player.hasPermission("simplead.pm")) {
             return true;
         } else if  (player.hasPermission("simplead.*")) {
             return true;
         }
         return false;
	 }	 
  
     @SuppressWarnings("deprecation")
	@Override
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) //Aci de /ad-ul, adica comanda initiala
	{
     
    	 
    	  	    	 
    	int lungime = args.length;
		Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("ad") && sender instanceof Player) 
        	
        {
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
            	logToFile( format.format(date) + " - " + "Warn > " + sender.getName() + " didn't have enough money.");
        
            }
		
        	   @SuppressWarnings("deprecation")
			EconomyResponse r = econ.withdrawPlayer(sender.getName(), this.price);		
                Player bahoi = (Player)sender;
        	    int i = 0;    
                if (r.transactionSuccess() && i < lungime  ) 
                {           
            	String mesaj = " ";
                while (i < lungime) {
                	mesaj = String.valueOf(mesaj) + args[i] + " ";
                    ++i;
                   }
                mesaj = mesaj.trim();
                for (@SuppressWarnings("unused") Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.mcolor + mesaj + this.contact + player.getName()));
                    int s = this.price;
                    int m = this.getConfig().getInt("bizbal");
                    int muia = s + m;
                    ++s;
                    this.getConfig().set("bizbal", muia);
                    logToFile( format.format(date) + " - " + mesaj + " . Made by: " + player.getName());
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.succes + this.price + this.dolar));
                logToFile( format.format(date) + " - " + player.getName() + " has succesfully made an Ad." );
                cooldown.add(player);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                	public void run() {
                		cooldown.remove(bahoi);
                	}
                }, this.cdint);                
                               
                return true;
                } else {
                    
                	player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.failure2));
                	econ.depositPlayer(sender.getName(), this.price); //Traiasca spookie
                return false;
                }
        
        
        }
        
	
        return false;     
	
	}


     public class Bungee implements CommandExecutor
     {
     		String you;
     	    String afterYou;
     		String msgColor;
     		String newMsg;
     		String beforeName;
     		String nopermMsg;
     		String prefix;
     		String puncte;
     		String usage;
     		
     		void Cacaness() {
                  
                // this.you = plugin.getConfig().getString("you");
                // this.afterYou = plugin.getConfig().getString("afterYou");
                // this.msgColor = plugin.getConfig().getString("msgColor");
                // this.newMsg = plugin.getConfig().getString("customMessage");
                 //this.beforeName = plugin.getConfig().getString("beforeName");
                // this.nopermMsg = plugin.getConfig().getString("NoPermissionMessage");
                 //this.prefix = plugin.getConfig().getString("prefix");

         }
     		   
     	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
     	    if (!channel.equals("BungeeCord")) {
     	      return;
     	    }
     	    ByteArrayDataInput in = ByteStreams.newDataInput(message);
     	    String subchannel = in.readUTF();
     	    if (subchannel.equals("SomeSubChannel")) {
     	      
     	    }
     	String subChannel = in.readUTF();
     	short len = in.readShort();
     	byte[] msgbytes = new byte[len];
     	in.readFully(msgbytes);
     try {
     	DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
     	String somedata = msgin.readUTF(); 
     	short somenumber = msgin.readShort();
     } catch (IOException e) {

     }
     }

     public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
     {
     //pm <Player> <Message>
     	 Player player = (Player) sender;
     if (command.getName().equalsIgnoreCase("msg") && sender instanceof Player && plugin.getConfig().getString("bungeecordMsg").equalsIgnoreCase("true") && hasBungee(player))
     {
     	 
     		
     		 
     		
     	Player p = (Player) sender;
     	if (args.length < 1)
     	{		
     	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7/msg [Player] [Text]")); //this.usage
     	return false;
     			
     	}
     	
     	String playerToSend = args[0];
     	String msg = "";
     	
     	for (int i = 1; i < args.length; i++)
     	    msg += args[i] + " ";

     				
     	ByteArrayOutputStream b = new ByteArrayOutputStream();
     	DataOutputStream out = new DataOutputStream(b);
     	
     	try
     	{
     	
     		
     		//out.writeUTF("Forward");
     		String prefixPEX = PermissionsEx.getUser(sender.getName()).getGroups()[0].getPrefix();              	
            String message = plugin.getConfig().getString("beforeName");
     		 message = message.replace("{prefix}", prefixPEX);
     		
     		//out.writeUTF("ALL");
     		out.writeUTF("Message");
     		out.writeUTF(playerToSend);
     		//out.writeUTF(ChatColor.translateAlternateColorCodes('&', message + sender.getName() + this.afterYou + message + this.you + this.puncte + this.msgColor + msg));
     		out.writeUTF(ChatColor.translateAlternateColorCodes('&', "&l&8» " + message + sender.getName() + " &l&8»  " + "&aYou&7: " + msg ));
     		logToFile( format.format(date) + sender.getName() + " >> " + args[0] + msg);
     		//p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.you + this.afterYou + message + playerToSend + this.puncte + this.msgColor + msg));
     		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8»  &aYou " + " &l&8»  " + message + args[0] + "&7: "+ msg));
     		
     		//if(plugin.getConfig().getString("enableCustomMsg").equalsIgnoreCase("true"))
     		//{
         		//sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.newMsg ));
     		//	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8» &7Message sent" ));
     		//}
     	} catch (IOException e) {				
     	}		

     	p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());

     } else {
     	 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "You don't have permission" )); //this.nopermMsg
     }



     if(command.getName().equalsIgnoreCase("mesaj") && sender instanceof Player && plugin.getConfig().getString("bungeecordMsg").equalsIgnoreCase("false") && hasBungee(player))
     {
     		Player p = (Player) sender;
         	if (args.length < 1)
         	{		
         	p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.usage));
         	return false;
         			
         	}
         	
         	String msg = "";
         	Player pl=Bukkit.getPlayer(args[0]);
         	
         	for (int i = 1; i < args.length; i++)
         	    msg += args[i] + " ";
         	String prefixPEX = PermissionsEx.getUser(sender.getName()).getGroups()[0].getPrefix();              	
            String message = plugin.getConfig().getString("beforeName");
     		 message = message.replace("{prefix}", prefixPEX);
     		//pl.sendMessage(ChatColor.translateAlternateColorCodes('&', message + sender.getName() + this.afterYou + this.you + this.puncte + this.msgColor + msg));
     		pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8» " + message + sender.getName() + " &l&8»  " + "&aYou&7: " + msg ));
     		//p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.you + this.afterYou + message + args[0] + this.puncte + this.msgColor + msg));
     		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&8»  &aYou " + " &l&8»  " + message + ": " + args[0] + msg));
     		logToFile( format.format(date) + sender.getName() + this.afterYou + args[0] + msg);
     		//if(plugin.getConfig().getString("enableCustomMsg").equalsIgnoreCase("true"))
     		//{
         	//	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.newMsg ));
     		//}
     	    
     		
     		
     	
     	 
     } else {
     	 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.nopermMsg));
     }



     return false;
  
	}

     }
}

   
   







//Made by Moshu with help from MosLaDatorie.
// (C): mc.b-zone.ro
// B-Zone Community
// All rights reserved. ( 2017 )
// Any unauthorised copy or modification will be punished by laws in Romania.
// Version 1.5