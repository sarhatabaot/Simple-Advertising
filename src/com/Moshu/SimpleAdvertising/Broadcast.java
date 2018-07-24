package com.Moshu.SimpleAdvertising;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Broadcast implements CommandExecutor {
	
	String prefix = plugin.getConfig().getString("messages.prefix"); 
	
	public static Main plugin;
	
	public Broadcast(Main plugin)
	{
		this.plugin = plugin;
	}
   

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String broadcastMsg = "";
		for (String a : args){
		     broadcastMsg += a+" ";
		}
		
		broadcastMsg = ChatColor.translateAlternateColorCodes('&', broadcastMsg);
		
		if(cmd.getName().equalsIgnoreCase("bc"))
		{
			Player player = (Player) sender;
			
            if(!sender.hasPermission("simplead.broadcast"))
            {
            	Player p = (Player) sender;	
				Utils.sendNoAccess(p);
            	return true;
            }
            
            	if(args.length == 0)
            	{
            		player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getConfig().getString("messages.empty-message")));
            		return true;
            	}
            		
            	if(plugin.getConfig().getString("broadcast.enable-titles").equalsIgnoreCase("true"))
            	{
            	
            	if(broadcastMsg.length() > 42)
    			{
          		
            		for(Player p : Bukkit.getOnlinePlayers())
            		{
            			
            			List<String> strings = new ArrayList<>();
            			
            			for (String string : plugin.getConfig().getStringList("broadcast.chat")) 
            					{
            				string = string.replace("{message}", broadcastMsg);
            				strings.add(ChatColor.translateAlternateColorCodes('&', string));
            					}
            			
            			for(String s : strings)
            			{
            				p.sendMessage(s);
            			}
            			
            			//String f = ChatColor.translateAlternateColorCodes('&', "&8&m---&l&f(&cBroadcast&f&l)&8&m---");
            			//SendCenteredMessage scm = new SendCenteredMessage();	
            			//p.sendMessage("");
            			//scm.sendCenteredMessage(p, f);
            			//scm.sendCenteredMessage(p, broadcastMsg);
            			//p.sendMessage("");
            		}
           			}
            			else
            			{
            				
            				for(Player p : Bukkit.getOnlinePlayers())
                    		{
            					
            					int fadein = plugin.getConfig().getInt("titles.fade-in");
            					int stay = plugin.getConfig().getInt("titles.stay");
            					int fadeout = plugin.getConfig().getInt("titles.fade-out");
            					
            			String s = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("broadcast.title"));
            			String a = plugin.getConfig().getString("broadcast.subtitle");
            			a = a.replace("{message}", broadcastMsg);
            			a = ChatColor.translateAlternateColorCodes('&', a);
            			p.sendTitle(s, a, fadein, stay, fadeout);
            			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1F);
                    		}
            			}
            	}
            	else
            	{
            		for(Player p : Bukkit.getOnlinePlayers())
            		{
            			
            			List<String> strings = new ArrayList<>();
            			
            			for (String string : plugin.getConfig().getStringList("broadcast.chat")) 
            					{
            				string = string.replace("{message}", broadcastMsg);
            				strings.add(ChatColor.translateAlternateColorCodes('&', string));
            					}
            			
            			for(String s : strings)
            			{
            				p.sendMessage(s);
            			}
            			
            			//String f = ChatColor.translateAlternateColorCodes('&', "&8&m---&l&f(&cBroadcast&f&l)&8&m---");
            			//SendCenteredMessage scm = new SendCenteredMessage();	
            			//p.sendMessage("");
            			//scm.sendCenteredMessage(p, f);
            			//scm.sendCenteredMessage(p, broadcastMsg);
            			//p.sendMessage("");
            		}
            	}
	
            }
	
	return true;
	}

}
