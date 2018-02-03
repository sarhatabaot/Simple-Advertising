package com.Moshu.SimpleAdReloaded;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin implements Listener {
	
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
		
		String jm;
		String qm;
		String ajm;
		String aqm;
		
		String prefixMsg;
		String noPermMsg;
		String notSelfMsg;
		String notOnMsg;
		String usageMsg;
		String nameColorTargetNotOn;
		
		String senderName;
		String targetPrefixName;
		String afterTarget;
		String yourName;
		String senderPrefixName;
		
		String news;
		String afterName;
		
		   public void Business() 
		    { 
			  
			   this.jm = this.getConfig().getString("joinMessage");
               this.qm = this.getConfig().getString("quitMessage");
               this.ajm = this.getConfig().getString("afterNameJoin");
               this.aqm = this.getConfig().getString("afterNameQuit");
               this.prefixMsg = this.getConfig().getString("messagePrefix");
               this.noPermMsg = this.getConfig().getString("noPerm");
               this.notSelfMsg = this.getConfig().getString("usageMsg");
               this.notOnMsg = this.getConfig().getString("targetNotOn");
               this.usageMsg = this.getConfig().getString("usageMsg");
               this.nameColorTargetNotOn = this.getConfig().getString("nameColorTargetNotOn");
               
			        this.afterName = this.getConfig().getString("afterName");
		         	this.news = this.getConfig().getString("news");
		    	    this.yourName = this.getConfig().getString("yourName");
		    	    this.senderPrefixName = this.getConfig().getString("senderPrefixName");
					this.senderName = this.getConfig().getString("senderName");
					this.targetPrefixName = this.getConfig().getString("targetPrefixName");
					this.afterTarget = this.getConfig().getString("afterTarget"); 
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
	                this.help6 = this.getConfig().getString("helpCommand6");
	                this.helpSpigot = this.getConfig().getString("helpSpigot");

		    }

			public void onEnable() //Cand se porneste pluginu
			{
				PluginDescriptionFile pdfFile = getDescription();

				String msg =  ChatColor.translateAlternateColorCodes('&', "&cSimpleAdvertising &fstarted sucessfuly");
			    String msg1 = ChatColor.translateAlternateColorCodes('&', "&fVersion: &c" + pdfFile.getVersion());
			    String msg2 = ChatColor.translateAlternateColorCodes('&', "&cFor news: &7https://www.spigotmc.org/resources/simple-advertising.40414/" );
			    String msg3 = ChatColor.translateAlternateColorCodes('&', "&7List of changes: Quit / Join message, chat and messaging");
			    String msg4 = ChatColor.translateAlternateColorCodes('&', "&fMade by &cMoshu&f, this is the real deal." );
			    
			    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l* " + msg));
			    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg1));
			    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg2));
			    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg3));
			    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg4));
			           	
		                RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
			           	 
		                econ = (Economy)rsp.getProvider();		       
		                               
		                  Business();
		                  createConfig();
		                  setupEconomy();
		                  //hookPlayerPoints();

		                //Get Commands   
		                 
		                  getServer().getPluginManager().registerEvents(this, this);
		                  
		                  if(getConfig().getString("enableDate").equalsIgnoreCase("true"))
		                  {
		                  getCommand("date").setExecutor(new DateClass());
		                  } else {
		                	  Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDate &ffunctionality disabled"));
		                  }
		              


		                  if (this.getConfig().getString("logging").equalsIgnoreCase("false")) {               	
		                	  Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cLogging &fis disabled"));        
		                  } 
		                 
			}
			

			public void onDisable() //Cand se opreste pluginu
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSimpleAdvertising &fis disabling")); 


			}
	
		    @EventHandler
			public void onPlayerJoin(PlayerJoinEvent e)
			{
		    	if(getConfig().getString("enable").equalsIgnoreCase("true"))
		    	{
				Player p = e.getPlayer();
						p.setAllowFlight(true);
				 e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', this.jm + e.getPlayer().getName() + this.ajm));
				 Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.jm + e.getPlayer().getName() + this.ajm));
		    	} else
		    	{
		    		return;
		    	}
				
			}
			@EventHandler
			 public void onPlayerQuit(PlayerQuitEvent e)
			 {
				if(getConfig().getString("enable").equalsIgnoreCase("true"))
		    	{
		 	 e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', this.qm + e.getPlayer().getName() + this.aqm));
		 	 Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.qm + e.getPlayer().getName() + this.aqm));
		    	} else
		    	{
		    		return;
		    	}
			 
			}
			
			@EventHandler
			public void chatFormat(AsyncPlayerChatEvent e)
			
			{
				if(getConfig().getString("enableChat").equalsIgnoreCase("true"))
				{
				e.getFormat();
				Player p = e.getPlayer();
				
				if(getServer().getPluginManager().getPlugin("PermissionsEx") != null)
				{
				String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
				e.setFormat(ChatColor.translateAlternateColorCodes('&', " " + prefix + p.getDisplayName() + " &l&8» &f" + e.getMessage()));
			
				} else
				{
					e.setFormat(ChatColor.translateAlternateColorCodes('&', " &c" + p.getDisplayName() + "&l&8» &f" + e.getMessage()));
				}
				} else
				{
					return;
				}
				
			}
			
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

		     
		     ArrayList<Player> cooldown = new ArrayList<Player>();
		 	
		     private boolean setupEconomy() 
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
		     private PlayerPoints playerPoints;
		     

		     @SuppressWarnings({ "unused", "deprecation" })
			@Override
			 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) //Aci de /ad-ul, adica comanda initiala
			
			 { 	     	 
		    	
				
		        if (cmd.getName().equalsIgnoreCase("ad")) 
		        {
		        	if(!sender.hasPermission("simplead.ad"))
		        	{
		        		Player player = (Player) sender;
		        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noperm));
		        		return true;
		        	}
		        	if(args.length == 0)
		        	{
		        		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("usageAd")));
		        		return true;
		        	}
		        		
		        	 if(args[0].equalsIgnoreCase("help"))
	             	  {
	                  if(!sender.hasPermission("simplead.help"))
	                  {
	                  	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noperm));
	                  	return true;
	                  }
	                  
	                  if(args.length != 1)
	                  {
	                  	return true;
	                  }
	                  
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.helpHeader));
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.helpCommands));
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help1));
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help2));
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help3));
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help4));

	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.help6));
	             		  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.helpSpigot));
	             		  return true;
	             	  }
	        

	              	
		        	 else if(args[0].equalsIgnoreCase("reload"))
	                     {
	              		 if(!sender.hasPermission("simplead.reload"))
	                       {
	                       	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noperm));
	                       	return true;
	                       }
	                       if(args.length != 1)
	                       {
	                       	return true;
	                       }
	                    
	                     	                         Player plaier = (Player)sender;
	                     	                         if (plaier.hasPermission("simplead.reload")) {
	                     	                        	 this.getPluginLoader().disablePlugin(this); 
	                     	                        	 this.getPluginLoader().enablePlugin(this);
	                     	                        	 this.saveDefaultConfig();
	                     	                        	 this.reloadConfig();
	                     	                 	         
	                     	                 	    
	                     	                 	        
	                     	                         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f &aSimple Advertising (By Moshu) 1.8 was reloaded!"));
	                     	                         logToFile(format.format(date) + " - " + "Reload > " +  sender.getName() + " has reloaded the plugin.");
	                     	                         return true;
	                     	                      

	                     	                     } else {
	                     	                         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f> &cYou don't have permission!"));
	                     	                         logToFile( format.format(date) + " - " + "Reload > " +  sender.getName() + " tried to reload but didn't have permission");
                                                        return true;
	                     	                 
	                     	                 }
                                             
	                     	         }
	                 
		        	Player player = (Player) sender;
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
		                //return true;
		            } 
				
		        	   @SuppressWarnings("deprecation")
		        	   
					EconomyResponse r = econ.withdrawPlayer(sender.getName(), this.price);		
		                Player bahoi2 = (Player)sender;
		        	    int i = 0;    
		        	    int lungime = args.length;
		                if (r.transactionSuccess()) 
		                {           
		            	String mesaj = " ";
		                for (Player p : Bukkit.getOnlinePlayers()) {
		                    
		                	mesaj = String.valueOf(mesaj) + args[i] + " ";
		                    mesaj = mesaj.trim();
		                	
		                	p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.mcolor + mesaj + this.contact + player.getName()));
		                	player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.succes + this.price + this.dolar));
			                logToFile(format.format(date) + " - " + player.getName() + " has succesfully made an Ad." );
			                cooldown.add(player);
	             
			                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			                	public void run() {
			                		cooldown.remove(bahoi2);
			                	}
			                }, this.cdint);                
		               
		            	
		                    logToFile(format.format(date) + " - " + mesaj + " . Made by: " + player.getName());
		                   
		                    if(getConfig().getString("sounds").equalsIgnoreCase("true"))
		                    {
		                    p.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1F);
		                    return true;
		                    } else
		                    {
		                    return true;
		                    }
		                    
		                }
		                
		                
		                               
		                return true;
		                } else {
		                    
		                	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSomething went wrong.."));
		                	econ.depositPlayer(sender.getName(), this.price); //Traiasca spookie
		                    return true;
		        
		                }
		        }
		        
		        		

		        else if(cmd.getName().equalsIgnoreCase("msg"))
		        		{
		        			if(args.length == 0)
		        			{
		        				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.usageMsg));
		        				return true;
		        			}
		        			
		        			if(args.length == 1)
		        			{
		        				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.usageMsg));
		        				return true;	
		        			}
		        			
		        			if(!sender.hasPermission("simplead.msg"))
		        			{
		        				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.noPermMsg));
		        				return true;
		        			}
		        			Player target = Bukkit.getPlayer(args[0]);
		        			if(!target.isOnline())
		        			{
		        				Player p = (Player) sender;
		        				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.nameColorTargetNotOn + args[0] + this.notOnMsg));
		        				return true;
		        			}
		        		Player p = (Player) sender;
		        		if(p == target)
		        		{
		        			p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.notSelfMsg));
		        			return true;
		        		}
		        		
		        		 String message = "";

		                 for(int i = 1; i != args.length; i++)
                               
		                     message += args[i] + " ";

		        		    /**String prefixSENDER = PermissionsEx.getUser(p).getGroups()[0].getPrefix();
		        		    String prefixRECIVER = PermissionsEx.getUser(target).getGroups()[0].getPrefix();
		        		    message = message.replace("{prefixSENDER}", prefixSENDER);
		        		    message = message.replace("{prefixTARGET}", prefixRECIVER);*/
		        			p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.senderName  + target.getName() + afterTarget + message));
		        			target.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("beforeTarget") + p.getName() + this.yourName + afterTarget + message));
		        			
		        			if(getConfig().getString("sounds").equalsIgnoreCase("true"))
		        			{
		        			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 1, 1F);
		        			target.playSound(target.getLocation(), Sound.AMBIENT_CAVE, 1, 1F);
		        			return true;
		        			} else
		        			{
		        				return true;
		        			}
		        			
		        		
		        		
		        		
		        		
		        		}
		                
		        else if(cmd.getName().equalsIgnoreCase("news"))
		        		{
		        	if(!(sender instanceof Player))
		        	{
		        		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You need to execute this command as a player");
		        	}
		        	if(!sender.hasPermission("simplead.news"))
		        	{
		        		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.noPermMsg));
		        	}
		        	Player p = (Player) sender;
		        	p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.news));
		        	
		        		return true;
		        		}
		        		
     
		     else if(cmd.getName().equalsIgnoreCase("adb"))
		        	{
		        	String broadcastMsg = "";
				for (String a : args){
				     broadcastMsg += a+" ";
				}
	
					 String bprefix = getConfig().getString("broadcastPrefix");
					
		         if(!sender.hasPermission("simplead.bc"))
		         {
		        	 Player player = (Player) sender;
		         	player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + this.noPermMsg));
		         	return true;
		         }
		         	if(args.length == 0)
		         	{
		         		Player player = (Player) sender;
		         		player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + getConfig().getString("broadcastUsage")));
		         		return true;
		         	}
		         		
		         		for(Player p : Bukkit.getOnlinePlayers())
		         		{
		         			p.sendMessage(ChatColor.translateAlternateColorCodes('&', bprefix + broadcastMsg));
		         		}
		         	
		   
		         	
		         } 
			return true;
			 }
}
