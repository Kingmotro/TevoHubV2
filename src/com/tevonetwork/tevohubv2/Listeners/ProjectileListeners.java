package com.tevonetwork.tevohubv2.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.tevonetwork.tevohubv2.Gadget.GadgetManager;

public class ProjectileListeners implements Listener {

	@EventHandler
	public void onProjLaunch(ProjectileLaunchEvent e)
	{
		if (!e.getEntity().getWorld().getName().startsWith("hub"))
		{
			return;
		}
		GadgetManager.handleEvent(e);
	}
	
	@EventHandler
	public void onProjHit(ProjectileHitEvent e)
	{
		if (!e.getEntity().getWorld().getName().startsWith("hub"))
		{
			return;
		}
		GadgetManager.handleEvent(e);
	}
	
}
