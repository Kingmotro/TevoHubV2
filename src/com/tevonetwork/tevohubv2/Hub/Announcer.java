package com.tevonetwork.tevohubv2.Hub;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.tevonetwork.tevoapi.API.Titles.ActionBar;
import com.tevonetwork.tevohubv2.TevoHubV2;

public class Announcer implements Runnable{

	private TevoHubV2 main = TevoHubV2.getInstance();
	private int counter = 0;
	
	@Override
	public void run()
	{
		List<String> broadcasts = main.getConfigManager().getConfig().getStringList("announcer.messages");
		List<String> enabledworlds = main.getConfigManager().getConfig().getStringList("announcer.worlds");
		for (Player p : Bukkit.getOnlinePlayers())
		{
			for (String world : enabledworlds)
			{
				if (p.getWorld().getName().equalsIgnoreCase(world))
				{
					ActionBar bc = new ActionBar(ChatColor.translateAlternateColorCodes('&', broadcasts.get(counter)));
					bc.send(p);
				}
			}
		}
		counter++;
		if (counter >= broadcasts.size())
		{
			counter = 0;
		}
	}
	
}
