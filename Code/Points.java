package com.Moshu.SimpleAdvertising;

import java.io.File;
import java.io.IOException;

import org.bukkit.entity.Player;

public class Points {

	static int points;
	private static Main plugin;
	
	public Points(Main plugin)
	{
		this.plugin = plugin;
	}
	
	static File dataf;
	
	public static int lookPoints(Player p)
	{
	
		String uuid = p.getUniqueId().toString();
		points = plugin.getData().getInt(uuid + ".points");
		return points;
	}
	
	public static void takePoints(Player p, int pointsToTake)
	{
		String uuid = p.getUniqueId().toString();
		points = plugin.getData().getInt(uuid + ".points");
		
		int removePoints = points - pointsToTake;
		
		dataf = new File(plugin.getDataFolder(), "points.yml");
		
		plugin.getData().set(uuid + ".points", removePoints);
		
		 plugin.getData().options().copyDefaults(true);
			try {
				plugin.getData().save(dataf);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	
	public static void givePoints(Player p, int pointsToGive)
	{
		String uuid = p.getUniqueId().toString();
		
		dataf = new File(plugin.getDataFolder(), "points.yml");
		
		points = plugin.getData().getInt(uuid + ".points");
		
		int addPoints = points + pointsToGive;
		
		plugin.getData().set(uuid + ".points", addPoints);
		
		 plugin.getData().options().copyDefaults(true);
			try {
				plugin.getData().save(dataf);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	
	public static void setPoints(Player p, int points)
	{		
		
		String uuid = p.getUniqueId().toString();
		
		dataf = new File(plugin.getDataFolder(), "points.yml");
		
		plugin.getData().set(uuid + ".points", points);
		
		 plugin.getData().options().copyDefaults(true);
		 
			try {
				plugin.getData().save(dataf);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	
	
}
