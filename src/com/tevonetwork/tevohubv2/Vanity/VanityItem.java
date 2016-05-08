package com.tevonetwork.tevohubv2.Vanity;

import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.Core.Rank;

public interface VanityItem {
	
	public String getItemName();
	public ItemStack getIcon();
	public ItemStack getActiveIcon();
	public String[] getDescription();
	public String getPermRequired();
	public Rank getExclusiveRank();
	
}
