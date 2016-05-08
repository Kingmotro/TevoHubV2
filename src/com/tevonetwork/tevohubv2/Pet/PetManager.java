package com.tevonetwork.tevohubv2.Pet;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevohubv2.TevoHubV2;
import com.tevonetwork.tevohubv2.GUIs.VanityMenu;
import com.tevonetwork.tevohubv2.Util.PermsHandler;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class PetManager implements Runnable{

	private static TevoHubV2 main = TevoHubV2.getInstance();
	private static HashMap<String, Pet> pets = new HashMap<String, Pet>();

	public static void addNewPetOwner(Player p, Pet pet)
	{
		if (pets.containsKey(p.getName()))
		{
			removePetOwner(p, true);
			
		}
		if (PermsHandler.checkAccess(p, ((VanityItem)pet).getPermRequired()))
		{
			String newpet = ((VanityItem)pet).getItemName(); 
			pets.put(p.getName(), pet);
			VanityManager.equipMessage(p, newpet);
			pet.spawn();
			p.closeInventory();
		}
		else
		{
			VanityManager.notUnlockedMessage(p);
		}
	}
	
	public static void removePetOwner(Player p, boolean silent)
	{
		if (!pets.containsKey(p.getName()))
		{
			return;
		}
		String previouspet = ((VanityItem)pets.get(p.getName())).getItemName();
		if (!silent)
		{
			VanityManager.unequipMessage(p, previouspet);
		}
		pets.get(p.getName()).remove();
		pets.remove(p.getName());
	}
	
	public static Pet getActivePet(Player p)
	{
		if (pets.containsKey(p.getName()))
		{
			return pets.get(p.getName());
		}
		return null;
	}
	
	public static VanityItem getActiveItem(Player p)
	{
		if (pets.containsKey(p.getName()))
		{
			return (VanityItem) pets.get(p.getName());
		}
		return null;
	}
	
	public static boolean hasActivePet(Player p)
	{
		return pets.containsKey(p.getName());
	}
	
	public static void handleInvClick(InventoryClickEvent e)
	{
		if (e.getInventory().getName().equalsIgnoreCase("Pets"))
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
				String pet = null;
				for (VanityItem item : VanityManager.getAvailablePets())
				{
					if (item.getIcon().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						pet = item.getItemName();
						break;
					}
					else if (item.getActiveIcon().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						pet = "rem";
						break;
					}
					else if (VanityManager.vanityBack().getItemMeta().getDisplayName().equalsIgnoreCase(clicked.getItemMeta().getDisplayName()))
					{
						VanityManager.openGUI(p, new VanityMenu(p));
						break;
					}
				}
				if (pet != null)
				{
					String petname = CC.tnPlayer + p.getName() + "'s " + CC.fReset + pet;
					
					switch(pet.toLowerCase().replace(" ", ""))
					{
					case("chickenpet"):
						PetManager.addNewPetOwner(p, new ChickenPet(petname, p));
						break;
					case("cowpet"):
						PetManager.addNewPetOwner(p, new CowPet(petname, p));
						break;
					case("irongolempet"):
						PetManager.addNewPetOwner(p, new IronGolemPet(petname, p));
						break;
					case("mooshroompet"):
						PetManager.addNewPetOwner(p, new MooshroomPet(petname, p));
						break;
					case("ocelotpet"):
						PetManager.addNewPetOwner(p, new OcelotPet(petname, p));
						break;
					case("pigpet"):
						PetManager.addNewPetOwner(p, new PigPet(petname, p));
						break;
					case("rabbitpet"):
						PetManager.addNewPetOwner(p, new RabbitPet(petname, p));
						break;
					case("sheeppet"):
						PetManager.addNewPetOwner(p, new SheepPet(petname, p));
						break;
					case("wolfpet"):
						PetManager.addNewPetOwner(p, new WolfPet(petname, p));
						break;
					case("rem"):
						PetManager.removePetOwner(p, false);
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
	
	@Override
	public void run()
	{
		for (Pet pet : pets.values())
		{
			pet.updatePath();
		}
	}
}
