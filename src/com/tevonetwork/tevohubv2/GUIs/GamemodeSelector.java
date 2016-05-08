package com.tevonetwork.tevohubv2.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Networking.ServerPlayerCounter;
import com.tevonetwork.tevoapi.Core.Networking.WorldPlayerCounter;

public class GamemodeSelector extends GUI{

	public GamemodeSelector(Player p) {
		super("Gamemode Selector", 26, p);
	}
	
	@Override
	public void load()
	{
		ItemStackFactory isf = new ItemStackFactory();
		ItemStack borderline = isf.createItemStackwithLore(Items.TRIPWIREHOOK, CC.tnGUIHead + "Borderline", new String[] {CC.tnInfo + "A new, custom, awesome gamemode", CC.tnInfo + "here on the tevo network!", CC.tnInfo + "Use guns, grenade launchers and", CC.tnInfo + "other weapons to kill your enemies", CC.tnInfo + "in a slowly shrinking space!", " ", CC.tnInfo + "Players: " + CC.tnValue + "-"});
		ItemStack kitpve = isf.createItemStackwithLore(Items.IRONSWORD, CC.tnGUIHead + "KitPVP", new String[] {CC.tnInfo + "The KitPVP you all love, but", CC.tnInfo + "completely reinvented!", CC.tnInfo + "Choose your kit with special perks and abilities", CC.tnInfo + "to knock down opponents in this continuous", CC.tnInfo + "free-for-all battle with amazing features", CC.tnInfo + "such as blood, killstreaks and more!", " ", CC.tnInfo + "Players: " + CC.tnValue + "-"});
		ItemStack hub = isf.createItemStackwithLore(Items.BEACON, CC.tnGUIHead + "Hub", new String[] {" ", CC.tnInfo + "Players: " + CC.tnValue + "-"});
		
		setIcon(11, kitpve);
		setIcon(13, hub);
		setIcon(15, borderline);
		getViewer().openInventory(getMenu());
		getViewer().updateInventory();
	}

	@Override
	public void open() {
		int borderlinecount = 0;
		int kitpvecount = 0;
		int hubcount = 0;
		
		int borderlinelobby = WorldPlayerCounter.getWorldCount("hub-borderline");
		int borderlinearenas = 0;
		for (World world : Bukkit.getWorlds())
		{
			if (world.getName().startsWith("borderline"))
			{
				borderlinearenas = borderlinearenas + world.getPlayers().size();
			}
		}
		
		borderlinecount = borderlinelobby + borderlinearenas;
		kitpvecount = ServerPlayerCounter.getPlayerCount("kitpve");
		hubcount = WorldPlayerCounter.getWorldCount("hub-main");
		
		ItemStackFactory isf = new ItemStackFactory();
		ItemStack borderline = isf.createItemStackwithLore(Items.TRIPWIREHOOK, CC.tnGUIHead + "Borderline", new String[] {CC.tnInfo + "A new, custom, awesome gamemode", CC.tnInfo + "here on the tevo network!", CC.tnInfo + "Use guns, grenade launchers and", CC.tnInfo + "other weapons to kill your enemies", CC.tnInfo + "in a slowly shrinking space!", " ", CC.tnInfo + "Players: " + CC.tnValue + borderlinecount});
		ItemStack kitpve = isf.createItemStackwithLore(Items.IRONSWORD, CC.tnGUIHead + "KitPVP", new String[] {CC.tnInfo + "The KitPVE you all love, but", CC.tnInfo + "completely reinvented!", CC.tnInfo + "Choose your kit with special perks and abilities", CC.tnInfo + "to knock down opponents in this continuous", CC.tnInfo + "free-for-all battle with amazing features", CC.tnInfo + "such as blood, killstreaks and more!", " ", CC.tnInfo + "Players: " + CC.tnValue + kitpvecount});
		ItemStack hub = isf.createItemStackwithLore(Items.BEACON, CC.tnGUIHead + "Hub", new String[] {" ", CC.tnInfo + "Players: " + CC.tnValue + hubcount});
		
		setIcon(11, kitpve);
		setIcon(13, hub);
		setIcon(15, borderline);
		getViewer().updateInventory();
	}

}
