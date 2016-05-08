package com.tevonetwork.tevohubv2.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevohubv2.TevoHubV2;
import com.tevonetwork.tevohubv2.GUIs.VanityMenu;
import com.tevonetwork.tevohubv2.Util.PermsHandler;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class MusicManager {

	private static TevoHubV2 main = TevoHubV2.getInstance();
	private static HashMap<String, Music> jukeboxes = new HashMap<String, Music>();
	private static List<String> hubs = new ArrayList<String>();
	
	public static void addMusic(Player p, Music music)
	{
		if (jukeboxes.containsKey(p.getName()))
		{
			removeMusic(p, true);
		}
		if (PermsHandler.checkAccess(p, ((VanityItem)music).getPermRequired()))
		{
			if (!checkWorld(p))
			{
				UtilPlayer.message(Category.VANITY, p, CC.tnError + "You cannot use that at the moment, only one jukebox allowed in the lobby at a time.");
				p.closeInventory();
				return;
			}
			if (!music.start())
			{
				UtilPlayer.message(Category.VANITY, p, CC.tnError + "Please move to a more open space!");
			}
			hubs.add(p.getWorld().getName());
			String newmusic = ((VanityItem)music).getItemName();
			VanityManager.equipMessage(p, newmusic);
			jukeboxes.put(p.getName(), music);
		}
		else
		{
			VanityManager.notUnlockedMessage(p);
		}
	}
	
	public static void removeMusic(Player p, boolean silent)
	{
		if (!jukeboxes.containsKey(p.getName()))
		{
			return;
		}
		String previousmusic = ((VanityItem)jukeboxes.get(p.getName())).getItemName();
		jukeboxes.get(p.getName()).stop();
		if (!silent)
		{
			VanityManager.unequipMessage(p, previousmusic);
		}
		jukeboxes.remove(p.getName());
		hubs.remove(p.getWorld().getName());
	}
	
	public static boolean hasActiveMusic(Player p)
	{
		return jukeboxes.containsKey(p.getName());
	}
	
	public static Music getActiveMusic(Player p)
	{
		if (jukeboxes.containsKey(p.getName()))
		{
			return jukeboxes.get(p.getName());
		}
		return null;
	}
	
	public static VanityItem getActiveItem(Player p)
	{
		if (jukeboxes.containsKey(p.getName()))
		{
			return (VanityItem)jukeboxes.get(p.getName());
		}
		return null;
	}
	
	private static boolean checkWorld(Player p)
	{
		if (!hubs.contains(p.getWorld().getName()))
		{
			return true;
		}
		return false;
	}
	
	public static void handleInvClick(InventoryClickEvent e)
	{
		if (e.getInventory().getName().equalsIgnoreCase("Music"))
		{
			e.setCancelled(true);
			if (!(e.getWhoClicked() instanceof Player))
			{
				return;
			}
			final Player p = (Player)e.getWhoClicked();
			ItemStack clicked = e.getCurrentItem();
			if ((clicked != null) && (clicked.hasItemMeta()))
			{
				String music = null;
				for (VanityItem item : VanityManager.getAvailableMusic())
				{
					if (item.getIcon().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						music = item.getItemName();
						break;
					}
					else if (item.getActiveIcon().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						music = "rem";
						break;
					}
					else if (VanityManager.vanityBack().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						VanityManager.openGUI(p, new VanityMenu(p));
						break;
					}
				}
				if (music != null)
				{
					
					switch(music.toLowerCase().replace(" ", ""))
					{
					case("blocksjukebox"):
						addMusic(p, new BlocksJukebox(p));
						break;
					case("catjukebox"):
						addMusic(p, new CatJukebox(p));
						break;
					case("chirpjukebox"):
						addMusic(p, new ChirpJukebox(p));
						break;
					case("elevenjukebox"):
						addMusic(p, new ElevenJukebox(p));
						break;
					case("farjukebox"):
						addMusic(p, new FarJukebox(p));
						break;
					case("malljukebox"):
						addMusic(p, new MallJukebox(p));
						break;
					case("mellohijukebox"):
						addMusic(p, new MellohiJukebox(p));
						break;
					case("staljukebox"):
						addMusic(p, new StalJukebox(p));
						break;
					case("stradjukebox"):
						addMusic(p, new StradJukebox(p));
						break;
					case("thirteenjukebox"):
						addMusic(p, new ThirteenJukebox(p));
						break;
					case("waitjukebox"):
						addMusic(p, new WaitJukebox(p));
						break;
					case("wardjukebox"):
						addMusic(p, new WardJukebox(p));
						break;
					case("rem"):
						removeMusic(p, false);
						break;
					}
					new BukkitRunnable() {
						
						@Override
						public void run() {
							p.closeInventory();
						}
					}.runTask(main);
				}
			}
		}
	}
	
}
