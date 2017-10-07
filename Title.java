package com.SimpleAd.MoshuAd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Title extends Ad implements CommandExecutor {

	Ad plugin;
	String sent = plugin.getConfig().getString("titleSent");
	
	
	boolean sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player)sender;
		
		if(cmd.getName().equalsIgnoreCase("adp") && player.hasPermission("simplead.abt"))
		{
			


			 int lungime = args.length;
			 int i = 0;
			 
			 
			 String mesaj1 = " ";
			 while (i < lungime) {
			 	mesaj1 = String.valueOf(mesaj1) + args[i] + " ";
			     ++i;
			    }
			 mesaj1 = mesaj1.trim();
			 
			 String mesaj2 = " ";
			 while (i < lungime) {
			 	mesaj2 = String.valueOf(mesaj2) + args[i] + " ";
			     ++i;
			    }
			 mesaj2 = mesaj2.trim();
			
			 
			 args[1] = mesaj1;
			 args[2] = mesaj2;
			 
					 for(Player p : Bukkit.getOnlinePlayers())
					 {
					 
			p.sendTitle(mesaj1, mesaj2);
			
		
					 }
					 player.sendMessage(ChatColor.translateAlternateColorCodes('&', sent));
			
				
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "No permission."));
		}
		return true;
		
		
		
	}
	
	
}
