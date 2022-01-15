package com.moshu.simpleadvertising;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

	public static Main plugin;
	
	public Placeholders(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public @NotNull String getAuthor() {
	
		return "Moshu";
	}

	@Override
	public String getIdentifier() {
	
		return "simplead";
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion().toString();
	}
	
	@Override
    public boolean persist()
	{
        return true;
    }
	
	@Override
    public boolean canRegister(){
        return true;
    }

	@Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        if(identifier.equals("price"))
        {

        	return Integer.toString(plugin.getConfig().getInt("advertising.price"));
        
        }

 
        if(identifier.equals("cooldown"))
        {
        	return Integer.toString(plugin.getConfig().getInt("advertising.cooldown"));
        }
        
        if(identifier.equals("currentad"))
        {
            return Advertising.getCurrentAd();
        }
        
        if(identifier.equals("currentplayer"))
        {
            return Advertising.getCurrentAdPlayer();
        }
        
 

        return null;
    }
	
}
