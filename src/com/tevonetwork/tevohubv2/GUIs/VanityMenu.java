package com.tevonetwork.tevohubv2.GUIs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevohubv2.Disguise.DisguiseManager;
import com.tevonetwork.tevohubv2.Gadget.GadgetManager;
import com.tevonetwork.tevohubv2.Music.MusicManager;
import com.tevonetwork.tevohubv2.Particle.ParticleManager;
import com.tevonetwork.tevohubv2.Pet.PetManager;

public class VanityMenu extends GUI{

	public VanityMenu(Player p) {
		super("Vanity Menu", 44, p);
	}

	@Override
	public void open() {
		
		ItemStackFactory isf = new ItemStackFactory();
		ItemStack pets = isf.createItemStack(Items.BONE, CC.tnGUIHead + "Pets");
		ItemStack wardrobe = isf.createItemStack(Items.LEATHERCHESTPLATE, CC.tnGUIHead + "Wardrobe");
		ItemStack gadgets = isf.createItemStack(Items.DIAMONDBARDING, CC.tnGUIHead + "Gadgets");
		ItemStack music = isf.createItemStack(Items.CATDISC, CC.tnGUIHead + "Music");
		ItemStack particles = isf.createItemStack(Items.ROSERED, CC.tnGUIHead + "Particle Effects");
		ItemStack disguises = isf.createItemStack(Items.SPAWNBLAZE, CC.tnGUIHead + "Disguises");
		
		setIcon(1, pets);
		setIcon(4, wardrobe);
		setIcon(7, gadgets);
		setIcon(28, music);
		setIcon(31, particles);
		setIcon(34, disguises);
		
		if (DisguiseManager.getActiveDisguise(getViewer()) != null)
		{
			setActiveIcon(43, DisguiseManager.getActiveItem(getViewer()));
		}
		if (GadgetManager.getActiveItem(getViewer()) != null)
		{
			setActiveIcon(16, GadgetManager.getActiveItem(getViewer()));
		}
		if (PetManager.getActiveItem(getViewer()) != null)
		{
			setActiveIcon(10, PetManager.getActiveItem(getViewer()));
		}
		if (MusicManager.getActiveItem(getViewer()) != null)
		{
			setActiveIcon(37, MusicManager.getActiveItem(getViewer()));
		}
		if (ParticleManager.getActiveItem(getViewer()) != null)
		{
			setActiveIcon(40, ParticleManager.getActiveItem(getViewer()));
		}
		
		getViewer().openInventory(getMenu());
		getViewer().updateInventory();
	}

}
