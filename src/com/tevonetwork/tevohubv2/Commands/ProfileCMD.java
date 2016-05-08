package com.tevonetwork.tevohubv2.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tevonetwork.tevoapi.API.Cooldown.SilentCooldown;
import com.tevonetwork.tevoapi.API.Stats.StatManager;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevoapi.Core.Messages.CategoryMSG;
import com.tevonetwork.tevohubv2.GUIs.Profile;

public class ProfileCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player)
		{
			Player p = (Player)sender;
			if (p.getWorld().getName().startsWith("hub"))
			{
				if (!SilentCooldown.isPlayeronSilentCooldown(p, "Profile"))
				{
					SilentCooldown.addSilentCooldown(p, "Profile", 10);
					if (!StatManager.isInCache(p))
					{
						return true;
					}
					Profile profilemenu = new Profile(p);
					profilemenu.load();
					profilemenu.open();
				}
			}
		}
		else
		{
			CategoryMSG.senderMessagePlayersOnly(sender, Category.HUB);
		}
		return false;
	}

}
