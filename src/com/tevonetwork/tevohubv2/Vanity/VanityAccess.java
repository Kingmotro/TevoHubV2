package com.tevonetwork.tevohubv2.Vanity;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.Core.Rank;

public class VanityAccess {

	private static char nope = '\u2717';
	private static char yep = '\u2714';
	private static String access = Character.toString(yep);
	private static String noaccess = Character.toString(nope);
	
	public static ItemStack unlockStore(ItemStack is)
	{
		if (is == null)
		{
			return is;
		}
		ItemMeta im = is.getItemMeta();
		List<String> newlore = im.getLore();
		newlore.add(" ");
		newlore.add(CC.tnDisable + CC.fBold + noaccess + CC.fReset + CC.tnDisable + " Not unlocked!");
		newlore.add(CC.tnInfo + "Purchase this at");
		newlore.add(CC.tnValue + "store.tevonetwork.com");
		im.setLore(newlore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack unlocked(ItemStack is)
	{
		if (is == null)
		{
			return is;
		}
		ItemMeta im = is.getItemMeta();
		List<String> newlore = im.getLore();
		newlore.add(" ");
		newlore.add(CC.tnEnable + CC.fBold + access + CC.fReset + CC.tnEnable + " Unlocked!");
		im.setLore(newlore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack unlockRank(ItemStack is, Rank rank)
	{
		String r = Rank.getRankPrefix(rank);
		if (is == null)
		{
			return is;
		}
		ItemMeta im = is.getItemMeta();
		List<String> newlore = im.getLore();
		newlore.add(" ");
		newlore.add(CC.tnDisable + CC.fBold + noaccess + CC.fReset + CC.tnDisable + " Not Unlocked!");
		newlore.add(CC.tnInfo + "Unlocked with " + r + CC.tnInfo + " rank!");
		newlore.add(CC.tnInfo + "Purchase " + r + CC.tnInfo + " rank at" + CC.tnValue + " store.tevonetwork.com");
		im.setLore(newlore);
		is.setItemMeta(im);
		return is;
	}
	
}

