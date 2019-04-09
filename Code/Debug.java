package com.Moshu.SimpleAdvertising;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class Debug {
	
	private static Main plugin;
	
	public Debug(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public static void sendServerInfo(Player p)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
        {
            public void run()
            {
            	
            	PluginDescriptionFile pdf = plugin.getDescription();
            	
                p.sendMessage("");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aServer name: &7" + Bukkit.getServerName()));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGamemode: &7" + Bukkit.getDefaultGameMode().toString()));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion: &7" + Bukkit.getVersion().replace("-SNAPSHOT", "")));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlayers: &7" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers()));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPort: &7" + Bukkit.getPort()));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlugins &8(&7" + Bukkit.getPluginManager().getPlugins().length + "&8) "));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlugin Version: &7" + pdf.getVersion()));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDependencies: &7" + plugin.hasDependencies()));

                int loadedChunks = 0;
                int entities = 0;

                for (World w : Bukkit.getWorlds())
                {
                    loadedChunks += w.getLoadedChunks().length;
                    entities += w.getEntities().size();
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aChunks &8(&7" + loadedChunks + "&8) "));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aEntities &8(&7" + entities + "&8) "));

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aWorlds &8(&7" + Bukkit.getWorlds().size() + "&8) "));

                for (World w : Bukkit.getWorlds())
                {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  - &7" + w.getName() + ", &f" + w.getLoadedChunks().length + "&7 Chunks, &f" + w.getEntities().size() + "&7 Entities."));
                }
                try
                {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCPU: &7" + Utils.getUsedCPU() + "%"));
                }
                catch (Exception e)
                {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCPU: &7Error"));
                }
                try
                {
                    long memUsed = Utils.getMemoryUsed();
                    long maxMem = Utils.getMaxMemory();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRAM: &7" + memUsed + "MB/" + maxMem + "MB"));
                }
                catch (Exception e)
                {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRAM: &7Error"));
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
                p.sendMessage("");
            }
        });
    }
	
	

}
