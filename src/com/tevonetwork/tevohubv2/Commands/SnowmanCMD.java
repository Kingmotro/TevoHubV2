package com.tevonetwork.tevohubv2.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevoapi.Core.Messages.CategoryMSG;
import com.tevonetwork.tevoapi.Core.Messages.PermMSG;

public class SnowmanCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player p = (Player)sender;
			if (!p.getWorld().getName().startsWith("hub"))
			{
				return true;
			}
			if (!UtilPlayer.hasRank(p, Rank.DEVELOPER))
			{
				PermMSG.noPerm(sender, Rank.DEVELOPER);
				return true;
			}
			Snowman snowman = (Snowman)p.getWorld().spawnEntity(p.getLocation(), EntityType.SNOWMAN);
			snowman.setCustomName("Mr Blobby");
			snowman.setCustomNameVisible(true);
			snowman.setCanPickupItems(false);
			UtilPlayer.message(Category.HUB, p, CC.tnInfo + "Spawned Mr Blobby!");
		}
		else
		{
			CategoryMSG.senderMessagePlayersOnly(sender, Category.HUB);
		}
		return true;
	}

}
