package com.tevonetwork.tevohubv2.Gadget;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;

public abstract class Gadget {
	
	private Player owner;
	private ItemStack item;
	
	public Gadget(Player owner, ItemStack item)
	{
		this.item = item;
		this.owner = owner;
	}
	
	public abstract void handleEvent(Event e);
	
	public Player getOwner()
	{
		return this.owner;
	}
	
	public ItemStack getItem()
	{
		return this.item;
	}
	
	public void setItem(ItemStack is)
	{
		this.item = is;
	}
	
	public void equip()
	{
		this.owner.getInventory().setItem(2, this.item);
	}
	
	public void unequip()
	{
		this.owner.getInventory().setItem(2, new ItemStackFactory().createItemStack(Items.GLASSPANE, CC.tnValue + "No Gadget"));
	}

}
