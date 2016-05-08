package com.tevonetwork.tevohubv2.GUIs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Stats.StatManager;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Gamemodes;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevoapi.Economy.EconomyManager;

public class Profile extends GUI{

	public Profile(Player p) {
		super("Profile", 26, p);
	}
	
	@Override
	public void load()
	{
		ItemStackFactory isf = new ItemStackFactory();
		
		ItemStack playerhead = isf.createItemStackPlayerHeadwithLore(CC.tnHead + "Information:", getViewer().getName(), new String[] {CC.tnInfo + "Loading information..."});
		ItemStack borderlinestats = isf.createItemStackwithLore(Items.TRIPWIREHOOK, CC.tnHead + "Borderline Stats:", new String[] {CC.tnInfo + "Loading stats..."});
		ItemStack kitpvestats = isf.createItemStackwithLore(Items.IRONSWORD, CC.tnHead + "KitPVP Stats:", new String[] {CC.tnInfo + "Loading stats..."});
		
		setIcon(12, borderlinestats);
		setIcon(13, playerhead);
		setIcon(14, kitpvestats);
		
		getViewer().openInventory(getMenu());
	}
	
	@Override
	public void open()
	{
		final Player p = getViewer();
		
		
			
		ItemStackFactory isf = new ItemStackFactory();
		String rank = CC.cGray + CC.fBold + "No Rank";
		if (UtilPlayer.getRank(p) != Rank.DEFAULT)
		{
			rank = Rank.getRankPrefix(UtilPlayer.getRank(p));
		}
		ItemStack playerhead = isf.createItemStackPlayerHeadwithLore(CC.tnHead + "Information:", getViewer().getName(), new String[] {CC.tnInfo + "Name: " + CC.tnPlayer + p.getName(), CC.tnInfo + "Rank: " + rank, CC.tnInfo + "Tevo Tokens: " + CC.tnValue + EconomyManager.getTokensBal(p), CC.tnInfo + "Logins: " + CC.tnValue + StatManager.getLogins(p), CC.tnInfo + "Votes: " + CC.tnValue + StatManager.getVotes(p)});
		ItemStack borderlinestats = isf.createItemStackwithLore(Items.TRIPWIREHOOK, CC.tnHead + "Borderline Stats:", new String[] {CC.tnInfo + "Kills: " + CC.tnValue + StatManager.getKills(p, Gamemodes.BORDERLINE), CC.tnInfo + "Games: " + CC.tnValue + StatManager.getGames(p, Gamemodes.BORDERLINE), CC.tnInfo + "Wins: " + CC.tnValue + StatManager.getWins(p, Gamemodes.BORDERLINE)});
		ItemStack kitpvestats = isf.createItemStackwithLore(Items.IRONSWORD, CC.tnHead + "KitPVP Stats:", new String[] {CC.tnInfo + "Kills: " + CC.tnValue + StatManager.getKills(p, Gamemodes.KITPVE), CC.tnInfo + "Deaths: " + CC.tnValue + StatManager.getDeaths(p, Gamemodes.KITPVE), CC.tnInfo + "K/D: " + CC.tnValue + StatManager.getKD(p)});
			
		setIcon(12, borderlinestats);
		setIcon(13, playerhead);
		setIcon(14, kitpvestats);
	}	
}
