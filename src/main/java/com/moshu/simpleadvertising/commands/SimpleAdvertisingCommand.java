package com.moshu.simpleadvertising.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.moshu.simpleadvertising.Permissions;

@CommandAlias("simpleadvertising|simplead")
public class SimpleAdvertisingCommand extends BaseCommand {

    @CommandAlias("ad")
    @Subcommand("ad")
    @CommandPermission(Permissions.AD_COMMAND)
    public class AdvertisementSubCommand extends BaseCommand{

    }
}
