package com.SimpleAd.MoshuAd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.SimpleAd.MoshuAd.Ad;

public class Points extends Ad implements CommandExecutor {

	
	private PlayerPoints playerPoints;

	private boolean hookPlayerPoints() {
	    final Plugin plugin1 = plugin.getServer().getPluginManager().getPlugin("PlayerPoints");
	    playerPoints = PlayerPoints.class.cast(plugin1);
	    return playerPoints != null; 
	}

	public PlayerPoints getPlayerPoints() {
	    return playerPoints;
	}
	
	
	
	 ArrayList<Player> cooldown = new ArrayList<Player>();
	
	 Ad plugin;
     
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
    Date date = new Date();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("adp"))
		{
		
		Player player = (Player)sender;
		
		if (cooldown.contains(player)) 
    	{
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.cd ));
        return true; 
    
        }
    	
    	  Player plaier = (Player)sender;
          UUID uuid = plaier.getUniqueId();
    	  System.out.println(uuid);
          int balance = playerPoints.getAPI().look(uuid);
    	  
          if(balance < plugin.points)
    	{
        	  
    		StringBuilder sb = new StringBuilder();
    		sb.append("");
    		sb.append(balance);
    		String strI = sb.toString();
    	    player.sendMessage(ChatColor.translateAlternateColorCodes('&', strI));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.failurePoints));
    	}
    	  playerPoints.getAPI().take(uuid, plugin.points);		
          Player bahoi2 = (Player)sender;
  	      int i1 = 0;
          if (playerPoints.getAPI().take(uuid, plugin.points)) 
          {           
      	String mesaj = " ";
      	int lungime = args.length;
          while (i1 < lungime) {
          	mesaj = String.valueOf(mesaj) + args[i1] + " ";
              ++i1;
             }
          mesaj = mesaj.trim();
          for (@SuppressWarnings("unused") Player p : Bukkit.getOnlinePlayers()) {
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.mcolor + mesaj + plugin.contact + player.getName()));
              
          }
          player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.succesPoints + plugin.points + plugin.pointssign));
          cooldown.add(player);
          
          if(plugin.getConfig().getString("debug").equalsIgnoreCase("true"))
      	{
      	System.out.println("Player Points part working");	
      	}
          
          Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
          	public void run() {
          	}
          }, plugin.cdint);                
                         
          return true;
          
          } else {
              
          	player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.prefix + plugin.failurePoints));
          	playerPoints.getAPI().give(uuid, plugin.points); //Traiasca spookie
          return false;
		
          }
	}

		return true;
	}
		
	}
