package com.SimpleAd.MoshuAd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SimpleAd.MoshuAd.Ad;

public class Broadcast extends Ad implements CommandExecutor {
 
	Ad plugin;
 
 String bprefix = plugin.getConfig().getString("broadcastPrefix");

 
 
 
 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
 {
	 if(cmd.getName().equalsIgnoreCase("adb") && sender.hasPermission("simplead.bc"))
	 {
		 int lungime = args.length;
		 int i = 0;
		 
		 
		 String mesaj = " ";
		 while (i < lungime) {
		 	mesaj = String.valueOf(mesaj) + args[i] + " ";
		     ++i;
		    }
		 mesaj = mesaj.trim();
		 
		 
		 
		 for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', bprefix + mesaj));
         }
		 
	 } else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "No permission."));
		}
	 
	 
	 
	 return true;
 }
 
}
