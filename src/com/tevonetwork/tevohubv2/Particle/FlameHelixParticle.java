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

public class FlameHelixParticle extends Particle implements VanityItem{

	public FlameHelixParticle(Player p) {
		super(20L, 1L, p);
	}
	
	private double radialsPerStep = Math.PI / 8;
	
	private float step = 0;
	
	private float radius = 1.0F;
	
	private float yaxis = 0;
	
	@Override
	public void tick() {
		Location loc = getOwner().getLocation();
		loc.subtract(0, 0.5, 0);
		loc.add(Math.cos(radialsPerStep * step) * radius, yaxis, Math.sin(radialsPerStep * step) * radius);
        loc.getWorld().spigot().playEffect(loc, Effect.FLAME, 0, 0, 0F, 0F, 0F, 0F, 1, 50);
        step++;
        
        Location loc2 = getOwner().getEyeLocation();
		loc2.add(0, 0.5, 0);
		loc2.subtract(Math.cos(radialsPerStep * step) * radius, yaxis, Math.sin(radialsPerStep * step) * radius);
        loc2.getWorld().spigot().playEffect(loc2, Effect.FLAME, 0, 0, 0F, 0F, 0F, 0F, 1, 50);
        
		yaxis += 0.05;
		if (yaxis >= 2.5)
		{
			yaxis = 0;
		}
		
	}

	@Override
	public String getItemName() {
		return "Flame Helix Particle";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.BLAZEPOWDER, CC.tnGUIHead + "Activate " + getItemName(), getDescription());
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Deactivate " + CC.tnGUIHead + getItemName(), Items.BLAZEPOWDER);
	}

	@Override
	public String[] getDescription() {
		return new String[] {CC.tnInfo + "Spawns a rotating flame", CC.tnInfo + "helix effect around you!"};
	}

	@Override
	public String getPermRequired() {
		return "flamehelixparticle";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.CRYSTAL;
	}

	

}
