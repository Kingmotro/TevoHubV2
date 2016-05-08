package com.tevonetwork.tevohubv2.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tevonetwork.tevoapi.API.Portals.PortalManager;
import com.tevonetwork.tevoapi.API.Regions.Region;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevoapi.Core.Messages.CategoryMSG;
import com.tevonetwork.tevoapi.Core.Messages.PermMSG;
import com.tevonetwork.tevohubv2.ConfigManager;
import com.tevonetwork.tevohubv2.TevoHubV2;

public class SetHubCMD implements CommandExecutor{

	private TevoHubV2 main = TevoHubV2.getInstance();
	private ConfigManager cfm = main.getConfigManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player)
		{
			Player p = (Player)sender;
			if (UtilPlayer.hasRank(p, Rank.DEVELOPER))
			{
				if (args.length <= 0)
				{
					CategoryMSG.senderArgsErr(sender, Category.HUB, "/sethub <main/borderline>");
				}
				if (args.length >= 1)
				{
					if (args[0].equalsIgnoreCase("borderline"))
					{
						Location loc = p.getLocation();
						setPath(args[0].toLowerCase(), loc);
						UtilPlayer.message(Category.HUB, p, CC.tnInfo + "Set spawnpoint for " + CC.tnValue + args[0].toLowerCase() + CC.tnInfo + " lobby to " + CC.tnValue + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch());
					}
					else if (args[0].equalsIgnoreCase("main"))
					{
						Location loc = p.getLocation();
						setPath(args[0].toLowerCase(), loc);
						UtilPlayer.message(Category.HUB, p, CC.tnInfo + "Set spawnpoint for " + CC.tnValue + args[0].toLowerCase() + CC.tnInfo + " lobby to " + CC.tnValue + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch());
					}
					else if (args[0].equalsIgnoreCase("chatregion"))
					{
						if (PortalManager.hasSelection(p))
						{
							Region region = new Region(PortalManager.getPlayerMINSelection(p), PortalManager.getPlayerMAXSelection(p));
							setRegion(region);
							UtilPlayer.message(Category.HUB, p, CC.tnInfo + "The chat region has been set!");
						}
						else
						{
							UtilPlayer.message(Category.HUB, p, CC.tnError + "Please make a selection using the portal wand!");
						}
					}
					else
					{
						CategoryMSG.senderInvArgsErr(sender, Category.HUB, "/sethub <main/borderline/chatregion(must have selection)>");
					}
				}
			}
			else
			{
				PermMSG.noPerm(sender, Rank.DEVELOPER);
			}
		}
		else
		{
			CategoryMSG.senderMessagePlayersOnly(sender, Category.HUB);
		}
		return false;
	}
	
	private void setPath(String spawn, Location loc)
	{
		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double yaw = loc.getYaw();
		double pitch = loc.getPitch();
		
		String path = "spawnpoints." + spawn + ".";
		
		cfm.getConfig().set(path + "world", world);
		cfm.getConfig().set(path + "x", x);
		cfm.getConfig().set(path + "y", y);
		cfm.getConfig().set(path + "z", z);
		cfm.getConfig().set(path + "yaw", yaw);
		cfm.getConfig().set(path + "pitch", pitch);
		cfm.saveConfig();
		
	}
	
	private void setRegion(Region region)
	{
		Location min = region.getMin();
		Location max = region.getMax();
		
		String world = min.getWorld().getName();
		double xmin = min.getX();
		double ymin = min.getY();
		double zmin = min.getZ();
		
		double xmax = max.getX();
		double ymax = max.getY();
		double zmax = max.getZ();
		
		String path = "chatregion.";
		
		cfm.getConfig().set(path + "world", world);
		cfm.getConfig().set(path + "min.x", xmin);
		cfm.getConfig().set(path + "min.y", ymin);
		cfm.getConfig().set(path + "min.z", zmin);
		
		cfm.getConfig().set(path + "max.x", xmax);
		cfm.getConfig().set(path + "max.y", ymax);
		cfm.getConfig().set(path + "max.z", zmax);
		cfm.saveConfig();
	}

}
