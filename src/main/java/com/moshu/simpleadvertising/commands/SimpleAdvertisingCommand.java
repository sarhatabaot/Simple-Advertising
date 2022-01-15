package com.moshu.simpleadvertising.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import com.moshu.simpleadvertising.Main;
import com.moshu.simpleadvertising.Permissions;
import com.moshu.simpleadvertising.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@CommandAlias("simpleadvertising|simplead")
public class SimpleAdvertisingCommand extends BaseCommand {
    private final Main plugin;
    private final String prefix;
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");

    public SimpleAdvertisingCommand(final Main plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getConfig().getString("messages.prefix");
    }

    @CommandAlias("ad")
    @Subcommand("ad")
    @CommandPermission(Permissions.AD_COMMAND)
    public class AdvertisementSubCommand extends BaseCommand{

    }

    @HelpCommand
    @CommandPermission(Permissions.HELP)
    public void onHelp(CommandHelp help) {
        help.showHelp();
    }

    @CommandPermission(Permissions.DEBUG)
    @Subcommand("debug")
    public void onDebug(final CommandSender sender) {
        final String HEADER = "&8&l&m-----------------------------";
        final String EMPTY = "";
        PluginDescriptionFile pdf = plugin.getDescription();

        sender.sendMessage(EMPTY);
        sender.sendMessage(Utils.color(HEADER));
        sender.sendMessage(Utils.color("&aServer name: &7" + Bukkit.getServer().getName()));
        sender.sendMessage(Utils.color("&aGamemode: &7" + Bukkit.getDefaultGameMode()));
        sender.sendMessage(Utils.color("&aVersion: &7" + Bukkit.getVersion().replace("-SNAPSHOT", "")));
        sender.sendMessage(Utils.color("&aPlayers: &7" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers()));
        sender.sendMessage(Utils.color("&aPlugins &8(&7" + Bukkit.getPluginManager().getPlugins().length + "&8) "));
        sender.sendMessage(Utils.color("&aPlugin Version: &7" + pdf.getVersion()));
        sender.sendMessage(Utils.color("&aDependencies: &7" + plugin.hasDependencies()));
        sender.sendMessage(Utils.color(HEADER));
        sender.sendMessage(EMPTY);
    }


    @Subcommand("bal|balance|points")
    @CommandPermission(Permissions.POINTS)
    public void onPointsBalance(final Player player) {
        String str = plugin.getConfig().getString("messages.balance");
        int bl = AdvertisingPoints.lookPoints(player);
        str = str.replace("{points}", Integer.toString(bl));
        player.sendMessage(Utils.color(prefix + str));
    }


    @Subcommand("reload")
    @CommandPermission(Permissions.RELOAD)
    public void onReload(final CommandSender sender) {
        final Date date = new Date();
        plugin.getPluginLoader().disablePlugin(plugin);
        plugin.getPluginLoader().enablePlugin(plugin);
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        Main.reloadFiles();
        plugin.saveConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l(&cAdvertising&f&l) &fSuccesfully reloaded."));
        Utils.logToFile(format.format(date) + " - " + "Reload > " + sender.getName() + " has reloaded the plugin.");
    }
}
