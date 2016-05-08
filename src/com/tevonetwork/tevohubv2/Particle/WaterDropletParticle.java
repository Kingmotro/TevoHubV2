package com.tevonetwork.tevohubv2.Particle;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;

public class WaterDropletParticle extends Particle implements VanityItem{

	public WaterDropletParticle(Player p) {
		super(20L, 2L, p);
	}
	
	private double radialsPerStep = Math.PI / 8;
	
	private float radius = 1.0F;
	
	private float step = 0;

	@Override
	public void tick() {
		
		Location loc = getOwner().getEyeLocation();
		loc.add(0, 0.5, 0);
		loc.add(Math.cos(radialsPerStep * step) * radius, 0, Math.sin(radialsPerStep * step) * radius);
		loc.getWorld().spigot().playEffect(loc, Effect.WATERDRIP, 0, 0, 0F, 0F, 0F, 0F, 1, 50);
		step++;
		
	}

	@Override
	public String getItemName() {
		return "Water Droplet Particle";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.WATERBUCKET, CC.tnGUIHead + "Activate " + getItemName(), getDescription());
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Deactivate " + CC.tnGUIHead + getItemName(), Items.WATERBUCKET);
	}

	@Override
	public String[] getDescription() {
		return new String[] {CC.tnInfo + "Gives you a water", CC.tnInfo + "droplet effect that rotates", CC.tnInfo + "above your head!"};
	}

	@Override
	public String getPermRequired() {
		return "waterdropletparticle";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.MYSTIC;
	}

}
