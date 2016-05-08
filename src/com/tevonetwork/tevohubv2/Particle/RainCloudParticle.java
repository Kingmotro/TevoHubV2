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

public class RainCloudParticle extends Particle implements VanityItem{

	public RainCloudParticle(Player p) {
		super(10L, 2L, p);
	}
	
	private double radialsPerStep = Math.PI / 4;
	
	private float step = 0;
	
	private float radius = 0.2F;

	@Override
	public void tick() {
		
		Location loc = getOwner().getEyeLocation();
		loc.add(0, 1.5, 0);
		loc.getWorld().spigot().playEffect(loc, Effect.CLOUD, 0, 0, 0.3F, 0F, 0.3F, 0F, 7, 50);
		
		loc.add(Math.cos(radialsPerStep * step) * radius, 0, Math.sin(radialsPerStep * step) * radius);
		loc.getWorld().spigot().playEffect(loc, Effect.WATERDRIP, 0, 0, 0.1F, 0F, 0.1F, 0F, 3, 50);
		step++;
		loc.subtract(Math.cos(radialsPerStep * step) * radius, 0, Math.sin(radialsPerStep * step) * radius);
		loc.getWorld().spigot().playEffect(loc, Effect.WATERDRIP, 0, 0, 0.1F, 0F, 0.1F, 0F, 3, 50);
	}

	@Override
	public String getItemName() {
		return "Rain Cloud Particle";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.WEB, CC.tnGUIHead + "Activate " + getItemName(), getDescription());
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Deactivate " + CC.tnGUIHead + getItemName(), Items.WEB);
	}

	@Override
	public String[] getDescription() {
		return new String[] {CC.tnInfo + "Gives you a small", CC.tnInfo + "raincloud that floats above", CC.tnInfo + "your head and drops water", CC.tnInfo + "particles!"};
	}

	@Override
	public String getPermRequired() {
		return "raincloudparticle";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.CRYSTAL;
	}

	
	
}
