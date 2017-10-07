package com.SimpleAd.MoshuAd;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DateClass extends Ad implements CommandExecutor {


	
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
    Date date = new Date();
	
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
        {
        	
    		if (cmd.getName().equalsIgnoreCase("date") && sender.hasPermission("simplead.datecheck")) 
    		{    
    			
    			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The time: &a" + format.format(date)));	
            	
				    						
    			} else 
    			{
    				Player p = (Player) sender;
    				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to view the date"));
    			}
    		return true;
        }
    }
	
	
