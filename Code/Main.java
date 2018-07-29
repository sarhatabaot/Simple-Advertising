package com.Moshu.SimpleAdvertising;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	Economy econ;
	
	Advertising ad = new Advertising(this);
	
	public Advertising getAdvertisingClass()
	{
		return ad;
	}
	
	Broadcast bc = new Broadcast(this);
	
	public Broadcast getBroadcastClass()
	{
		return bc;
	}
	
	Events evn = new Events(this);
	
	public Events getEventsClass()
	{
		return evn;
	}
	
	Utils ut = new Utils(this);
	
	public Utils getUtilsClass()
	{
		return ut;
	}
	
	Updater up = new Updater(this);
	
	public Updater getUpdaterClass()
	{
		return up;
	}
	
	AutoMessage am = new AutoMessage(this);
	
	public AutoMessage getAutoMessageClass()
	{
		return am;
	}
	
	AutoTitles at = new AutoTitles(this);
	
	public AutoTitles getAutoTitlesClass()
	{
		return at;
	}
	
	AdvertisingPoints pts = new AdvertisingPoints(this);
	
	public AdvertisingPoints getPointsClass()
	{
		return pts;
	}
	
	public void onEnable()
	{
		
		PluginDescriptionFile pdfFile = getDescription();

		String msg =  ChatColor.translateAlternateColorCodes('&', "&cSimpleAdvertising &fstarted successfuly");
	    String msg1 = ChatColor.translateAlternateColorCodes('&', "&fVersion: &c" + pdfFile.getVersion());
	    String msg2 = ChatColor.translateAlternateColorCodes('&', "&cFor news: &7https://www.spigotmc.org/resources/simple-advertising.40414/" );
	    String msg4 = ChatColor.translateAlternateColorCodes('&', "&fMade by &cMoshu&f, this is the real deal." );
	    
	    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg));
	    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg1));
	    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg2));
	    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l* " + msg4));
		
	    
	    up.check();
	    
	    createFiles();
	   
	    if(getConfig().getString("auto-advertiser.chat").equalsIgnoreCase("true"))
	    {
	    am.start();
	    }
	    
	    if(getConfig().getString("auto-advertiser.titles").equalsIgnoreCase("true"))
	    {
	    at.start();
	    }
	    
	    setupEconomy();
	    ut.createConfig();
	    
	    getCommand("ad").setExecutor(ad);
	    getCommand("broadcast").setExecutor(bc);
	    getCommand("points").setExecutor(pts);
	    
	    Bukkit.getServer().getPluginManager().registerEvents(evn, this);
	    Bukkit.getServer().getPluginManager().registerEvents(up, this);
	    
	    RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
      	 
        econ = (Economy)rsp.getProvider();
	    
	}
	
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSimpleAdvertising &fis disabling")); 
	}
	
	 public boolean setupEconomy() 
	 {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) 
	        {
	            return false;
	        }
	        
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	         econ = (Economy)rsp.getProvider();
	        if (econ != null) {
	            return true;
	        }
	        return false;
	 }
	 
	 public FileConfiguration getData() {
	        return this.data;
	    }
	 
	    private File dataf;
		private FileConfiguration data;
	 
	 public void createFiles() {
        
	        dataf = new File(getDataFolder(), "data.yml");
	        
	        if (!dataf.exists()) {
	            dataf.getParentFile().mkdirs();
	            saveResource("data.yml", false);
	            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l* &cData.yml &fnot found, creating."));
	         }
	        
	        data = new YamlConfiguration();
	        
	        try {
          
	            try {
					data.load(dataf);
				} catch (InvalidConfigurationException e) {
			
					e.printStackTrace();
				}

	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}
