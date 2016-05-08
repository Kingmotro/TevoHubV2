package com.tevonetwork.tevohubv2.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.tevonetwork.tevoapi.API.Permissions.PermissionsHandler;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevoapi.Core.Messages.AuthorMSG;
import com.tevonetwork.tevoapi.Core.Messages.CategoryMSG;
import com.tevonetwork.tevohubv2.TevoHubV2;

public class TevoHubCMD implements CommandExecutor {

	private TevoHubV2 main = TevoHubV2.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1)
		{
			if (args[0].equalsIgnoreCase("reload"))
			{
				if (PermissionsHandler.hasRankSender(sender, Rank.DEVELOPER))
				{
					main.getConfigManager().reloadConfig();
					CategoryMSG.senderMessage(sender, Category.HUB, CC.tnInfo + "The config has been reloaded!");
				}
				else
				{
					AuthorMSG.sendAuthorStamp("HubV2", main.getDescription().getVersion(), sender);
				}
			}
		}
		else
		{
			AuthorMSG.sendAuthorStamp("HubV2", main.getDescription().getVersion(), sender);
		}
		return false;
	}

}
