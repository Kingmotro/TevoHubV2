package com.tevonetwork.tevohubv2.Commands;

import org.bukkit.entity.Player;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevohubv2.Hub.HubManager;

public class HubCMD {

	public boolean execute(Player caller, String[] args)
	{
		if (args.length > 0)
		{
			if (args[0].equalsIgnoreCase("borderline"))
			{
				HubManager.sendToBorderlineHub(caller);
				return true;
			}
		}
		if (caller.getWorld().getName().equalsIgnoreCase("hub-main"))
		{
			UtilPlayer.message(Category.TRAVEL, caller, CC.tnError + "You are already in the hub!");
			return false;
		}
		HubManager.sendtoHub(caller);
		return true;
	}
}
