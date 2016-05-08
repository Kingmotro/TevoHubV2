package com.tevonetwork.tevohubv2.Particle;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.tevonetwork.tevohubv2.TevoHubV2;
import com.tevonetwork.tevohubv2.GUIs.VanityMenu;
import com.tevonetwork.tevohubv2.Util.PermsHandler;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class ParticleManager {

	private static TevoHubV2 main = TevoHubV2.getInstance();
	private static HashMap<String, Particle> particles = new HashMap<String, Particle>();
	
	public static void addParticle(Player p, Particle particle)
	{
		if (particles.containsKey(p.getName()))
		{
			removeParticle(p, true);
		}
		if (PermsHandler.checkAccess(p, ((VanityItem)particle).getPermRequired()))
		{
			BukkitScheduler sch = Bukkit.getScheduler();
			particle.setTask(sch.runTaskTimer(main, particle, particle.getTickDelay(), particle.getTickRate()));
			particles.put(p.getName(), particle);
			String newparticle = ((VanityItem)particle).getItemName();
			VanityManager.equipMessage(p, newparticle);
		}
		else
		{
			VanityManager.notUnlockedMessage(p);
		}
	}
	
	public static void removeParticle(Player p, boolean silent)
	{
		if (!particles.containsKey(p.getName()))
		{
			return;
		}
		String previousparticle = ((VanityItem)particles.get(p.getName())).getItemName();
		if (!silent)
		{
			VanityManager.unequipMessage(p, previousparticle);
		}
		particles.get(p.getName()).getTask().cancel();
		particles.remove(p.getName());
	}
	
	public static boolean hasParticle(Player p)
	{
		return particles.containsKey(p.getName());
	}
	
	public static Particle getActiveParticle(Player p)
	{
		if (particles.containsKey(p.getName()))
		{
			return particles.get(p.getName());
		}
		return null;
	}
	
	public static VanityItem getActiveItem(Player p)
	{
		if (particles.containsKey(p.getName()))
		{
			return (VanityItem)particles.get(p.getName());
		}
		return null;
	}
	
	public static void handleInvClick(InventoryClickEvent e)
	{
		if (e.getInventory().getName().equalsIgnoreCase("Particles"))
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
				String particle = null;
				for (VanityItem item : VanityManager.getAvailableParticles())
				{
					if (item.getIcon().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						particle = item.getItemName();
						break;
					}
					else if (item.getActiveIcon().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						particle = "rem";
						break;
					}
					else if (VanityManager.vanityBack().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						VanityManager.openGUI(p, new VanityMenu(p));
						break;
					}
				}
				if (particle != null)
				{
					switch(particle.toLowerCase().replace(" ", ""))
					{
					case("firewalkparticle"):
						addParticle(p, new FireWalkParticle(p));
						break;
					case("flamehelixparticle"):
						addParticle(p, new FlameHelixParticle(p));
						break;
					case("heartparticles"):
						addParticle(p, new HeartParticle(p));
						break;
					case("lavadropletparticle"):
						addParticle(p, new LavaDropletParticle(p));
						break;
					case("notesparticle"):
						addParticle(p, new NotesParticle(p));
						break;
					case("raincloudparticle"):
						addParticle(p, new RainCloudParticle(p));
						break;
					case("thundercloudparticle"):
						addParticle(p, new ThunderCloudParticle(p));
						break;
					case("waterdropletparticle"):
						addParticle(p, new WaterDropletParticle(p));
						break;
					case("whitehaloparticle"):
						addParticle(p, new WhiteHaloParticle(p));
						break;
					case("rem"):
						removeParticle(p, false);
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
