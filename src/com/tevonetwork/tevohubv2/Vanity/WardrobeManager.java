package com.tevonetwork.tevohubv2.Vanity;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.HashBasedTable;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer.playerSounds;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevohubv2.TevoHubV2;
import com.tevonetwork.tevohubv2.GUIs.VanityMenu;

public class WardrobeManager implements Runnable{
	
	private static TevoHubV2 main = TevoHubV2.getInstance();
	private static HashBasedTable<Player, ArmorPart, ArmorType> armor = HashBasedTable.create();
	private static String rainbowarmor = "§4§lR§e§la§2§li§1§ln§6§lb§f§lo§5§lw";
	
	public enum ArmorPart
	{
		HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	}
	
	public enum ArmorType
	{
		RED, YELLOW, GREEN, BLUE, ORANGE, BLACK, WHITE, PURPLE, RAINBOW;
	}
	
	private static void equipArmor(Player p, ArmorPart part, ArmorType type)
	{
		armor.put(p, part, type);
	}
	
	public static boolean hasArmor(Player p)
	{
		return armor.containsRow(p);
	}
	
	public static void clearArmor(Player p)
	{
		if (armor.containsRow(p))
		{
			for (ArmorPart parts : ArmorPart.values())
			{
				armor.remove(p, parts);
			}
		}
		p.getInventory().setArmorContents(null);
		p.updateInventory();
	}
	
	public static void handleInvClick(InventoryClickEvent e)
	{
		if (e.getInventory().getTitle().equalsIgnoreCase("Wardrobe"))
		{
			e.setCancelled(true);
			if (!(e.getWhoClicked() instanceof Player))
			{
				return;
			}
			final Player p = (Player)e.getWhoClicked();
			ItemStack is = e.getCurrentItem();
			if ((is == null) || (!is.hasItemMeta()))
			{
				return;
			}
			if (is.getItemMeta().getDisplayName().equalsIgnoreCase(VanityManager.vanityBack().getItemMeta().getDisplayName()))
			{
				new BukkitRunnable() {
					
					@Override
					public void run() {
						p.closeInventory();
						UtilPlayer.sound(p, playerSounds.GUI_PAGECHANGE);
						VanityMenu menu = new VanityMenu(p);
						menu.load();
						menu.open();
					}
				}.runTask(main);
				return;
			}
			if (is.getItemMeta().getDisplayName().equalsIgnoreCase(CC.tnDisable + CC.fBold + "Unequip " + CC.tnGUIHead + "all armor!"))
			{
				new BukkitRunnable() {
					
					@Override
					public void run() {
						p.closeInventory();
						clearArmor(p);
					}
				}.runTask(main);
				UtilPlayer.message(Category.VANITY, p, CC.tnInfo + "You " + CC.tnDisable + "unequipped " + CC.tnInfo + "all armor.");
				UtilPlayer.sound(p, playerSounds.UNEQUIP);
				return;
			}
			String item = is.getItemMeta().getDisplayName();
			String part = item.split(" ")[1].substring(4);
			String type = null;
			if (item.split(" ")[0].equalsIgnoreCase(rainbowarmor))
			{
				type = "rainbow";
			}
			else
			{
				type = item.split(" ")[0].substring(4);
			}
			ArmorPart armorpart = getPartfromString(part);
			ArmorType armortype = getTypefromString(type);
			equipArmor(p, armorpart, armortype);
			
			ItemStack armor = new ItemStack(is.getType());
			ItemMeta meta = armor.getItemMeta();
			((LeatherArmorMeta)meta).setColor(((LeatherArmorMeta)is.getItemMeta()).getColor());
			meta.setDisplayName(is.getItemMeta().getDisplayName());
			armor.setItemMeta(meta);
			
			switch(armorpart)
			{
			case HELMET:
				p.getInventory().setHelmet(armor);
				p.updateInventory();
				break;
			case CHESTPLATE:
				p.getInventory().setChestplate(armor);
				p.updateInventory();
				break;
			case LEGGINGS:
				p.getInventory().setLeggings(armor);
				p.updateInventory();
				break;
			case BOOTS:
				p.getInventory().setBoots(armor);
				p.updateInventory();
				break;
			}
			
			UtilPlayer.sound(p, playerSounds.EQUIP);
			return;
		}
	}
	
	private static ArmorPart getPartfromString(String part)
	{
		switch(part.toLowerCase())
		{
		case("helmet"):
			return ArmorPart.HELMET;
		case("chestplate"):
			return ArmorPart.CHESTPLATE;
		case("leggings"):
			return ArmorPart.LEGGINGS;
		case("boots"):
			return ArmorPart.BOOTS;
		}
		return null;
	}
	
	private static ArmorType getTypefromString(String type)
	{
		switch(type.toLowerCase())
		{
		case("red"):
			return ArmorType.RED;
		case("yellow"):
			return ArmorType.YELLOW;
		case("green"):
			return ArmorType.GREEN;
		case("blue"):
			return ArmorType.BLUE;
		case("orange"):
			return ArmorType.ORANGE;
		case("black"):
			return ArmorType.BLACK;
		case("white"):
			return ArmorType.WHITE;
		case("purple"):
			return ArmorType.PURPLE;
		case("rainbow"):
			return ArmorType.RAINBOW;
		}
		return null;
	}
	
	public static Color[] getColors()
	{
		return new Color[] {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.BLACK, Color.WHITE, Color.ORANGE, Color.PURPLE};
	}
	
	private static int pos = 0;
	private static int pos2 = 1;
	private static int pos3 = 2;
	private static int pos4 = 3;
	
	@Override
	public void run() {
		
		Color[] colors = getColors();
		
		ItemStackFactory isf = new ItemStackFactory();
		ItemStack rainbowhelmet = isf.createItemStackwithLore(Items.LEATHERHELMET, rainbowarmor + " " + CC.tnGUIHead + "Helmet",  new String[] {CC.tnInfo + "Click to equip!"});
		ItemMeta helmetmeta = rainbowhelmet.getItemMeta();
		((LeatherArmorMeta)helmetmeta).setColor(colors[pos]);
		rainbowhelmet.setItemMeta(helmetmeta);
		
		ItemStack rainbowchestplate = isf.createItemStackwithLore(Items.LEATHERCHESTPLATE, rainbowarmor + " " + CC.tnGUIHead + "Chestplate",  new String[] {CC.tnInfo + "Click to equip!"});
		ItemMeta chestplatemeta = rainbowchestplate.getItemMeta();
		((LeatherArmorMeta)chestplatemeta).setColor(colors[pos2]);
		rainbowchestplate.setItemMeta(chestplatemeta);
	
		ItemStack rainbowleggings = isf.createItemStackwithLore(Items.LEATHERLEGGINGS, rainbowarmor + " " + CC.tnGUIHead + "Leggings",  new String[] {CC.tnInfo + "Click to equip!"});
		ItemMeta leggingsmeta = rainbowleggings.getItemMeta();
		((LeatherArmorMeta)leggingsmeta).setColor(colors[pos3]);
		rainbowleggings.setItemMeta(leggingsmeta);
		
		ItemStack rainbowboots = isf.createItemStackwithLore(Items.LEATHERBOOTS, rainbowarmor + " " +CC.tnGUIHead + "Boots",  new String[] {CC.tnInfo + "Click to equip!"});
		ItemMeta bootsmeta = rainbowboots.getItemMeta();
		((LeatherArmorMeta)bootsmeta).setColor(colors[pos4]);
		rainbowboots.setItemMeta(bootsmeta);
		
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if (player.getOpenInventory().getTitle().equalsIgnoreCase("Wardrobe"))
			{
				Player p = player;
				p.getOpenInventory().setItem(17, rainbowhelmet);
				p.getOpenInventory().setItem(26, rainbowchestplate);
				p.getOpenInventory().setItem(35, rainbowleggings);
				p.getOpenInventory().setItem(44, rainbowboots);
				p.updateInventory();
			}
			
			if (armor.containsRow(player))
			{
				if (armor.get(player, ArmorPart.HELMET) == ArmorType.RAINBOW)
				{
					ItemMeta helmeta = rainbowhelmet.getItemMeta();
					helmeta.setLore(null);
					rainbowhelmet.setItemMeta(helmeta);
					player.getInventory().setHelmet(rainbowhelmet);
					player.updateInventory();
				}
				if (armor.get(player, ArmorPart.CHESTPLATE) == ArmorType.RAINBOW)
				{
					ItemMeta chestmeta = rainbowchestplate.getItemMeta();
					chestmeta.setLore(null);
					rainbowchestplate.setItemMeta(chestmeta);
					player.getInventory().setChestplate(rainbowchestplate);
					player.updateInventory();
				}
				if (armor.get(player, ArmorPart.LEGGINGS) == ArmorType.RAINBOW)
				{
					ItemMeta leggmeta = rainbowleggings.getItemMeta();
					leggmeta.setLore(null);
					rainbowleggings.setItemMeta(leggmeta);
					player.getInventory().setLeggings(rainbowleggings);
					player.updateInventory();
				}
				if (armor.get(player, ArmorPart.BOOTS) == ArmorType.RAINBOW)
				{
					ItemMeta boometa = rainbowboots.getItemMeta();
					boometa.setLore(null);
					rainbowboots.setItemMeta(boometa);
					player.getInventory().setBoots(rainbowboots);
					player.updateInventory();
				}
			}
		}
		pos++;
		pos2++;
		pos3++;
		pos4++;

		if (pos >= colors.length)
		{
			pos = 0;
		}
		if (pos2 >= colors.length)
		{
			pos2 = 0;
		}
		if (pos3 >= colors.length)
		{
			pos3 = 0;
		}
		if (pos4 >= colors.length)
		{
			pos4 = 0;
		}	
	}	
}
