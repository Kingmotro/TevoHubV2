package com.tevonetwork.tevohubv2.GUIs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;
import com.tevonetwork.tevohubv2.Vanity.WardrobeManager;
import com.tevonetwork.tevohubv2.Vanity.WardrobeManager.ArmorPart;

public class Wardrobe extends GUI{

	public Wardrobe(Player p) {
		super("Wardrobe", 44, p);
	}

	@Override
	public void open() {
		Color[] array = WardrobeManager.getColors();
		int index = 9;
		for (ArmorPart part : ArmorPart.values())
		{
			for (Color color : array)
			{
				setIcon(index, createLeatherArmor(color, part));
				index++;
				if (index == 17)
				{
					index = 18;
				}
				if (index == 26)
				{
					index = 27;
				}
				if (index == 35)
				{
					index = 36;
				}
			}
		}
		setIcon(8, new ItemStackFactory().createItemStack(Items.BARRIER, CC.tnDisable + CC.fBold + "Unequip " + CC.tnGUIHead + "all armor!"));
		setIcon(4, VanityManager.vanityBack());
		getViewer().openInventory(getMenu());
		getViewer().updateInventory();
	}

	
	private ItemStack createLeatherArmor(Color color, ArmorPart part)
	{
		Material mat = null;
		String partname = null;
		switch(part)
		{
		case HELMET:
			mat = Material.LEATHER_HELMET;
			partname = "Helmet";
			break;
		case CHESTPLATE:
			mat = Material.LEATHER_CHESTPLATE;
			partname = "Chestplate";
			break;
		case LEGGINGS:
			mat = Material.LEATHER_LEGGINGS;
			partname = "Leggings";
			break;
		case BOOTS:
			mat = Material.LEATHER_BOOTS;
			partname = "Boots";
			break;
		}
		if ((mat == null) || (partname == null))
		{
			return new ItemStackFactory().createItemStack(Items.REDSTONECOMPARATOR, CC.cRED + ":( There was an error!");
		}
		ItemStack is = new ItemStack(mat);
		ItemMeta im = is.getItemMeta();
		((LeatherArmorMeta)im).setColor(color);
		im.setDisplayName(getChar(color) + " §a§l" + partname);
		List<String> lore = new ArrayList<String>();
		lore.add(CC.tnInfo + "Click to equip!");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	private String getChar(Color color)
	{
		
		if (color == Color.RED)
		{
			return "§4§lRed";
		}
		if (color == Color.YELLOW)
		{
			return "§e§lYellow";
		}
		if (color == Color.GREEN)
		{
			return "§2§lGreen";
		}
		if (color == Color.BLUE)
		{
			return "§1§lBlue";
		}
		if (color == Color.BLACK)
		{
			return "§f§lBlack";
		}
		if (color == Color.WHITE)
		{
			return "§f§lWhite";
		}
		if (color == Color.PURPLE)
		{
			return "§5§lPurple";			
		}
		if (color == Color.ORANGE)
		{
			return "§6§lOrange";
		}
		return "§a";
	}
	
	
}
