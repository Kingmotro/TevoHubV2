package com.tevonetwork.tevohubv2.Particle;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;

public class WhiteHaloParticle extends Particle implements VanityItem {
	
	public WhiteHaloParticle(Player p) {
		super(20L, 3L, p);
	}
	
	private int amount = 12;
	
	private float radius = 0.5F;
	
	@Override
	public void tick() {
		
		Location center = getOwner().getEyeLocation();
		center.add(0, 0.5, 0);
		World world = center.getWorld();
		double increment = (2 * Math.PI) / amount;
		for (int i = 0; i < amount; i++) {
			double angle = i * increment;
			double x = center.getX() + (radius * Math.cos(angle));
			double z = center.getZ() + (radius * Math.sin(angle));
			Location particle = new Location(world, x, center.getY(), z);
			particle.getWorld().spigot().playEffect(particle, Effect.CRIT, 0, 0, 0F, 0F, 0F, 0F, 1, 50);
		}
	}

	@Override
	public String getItemName() {
		return "White Halo Particle";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.NETHERQUARTZ, CC.tnGUIHead + "Activate " + getItemName(), getDescription());
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Deactivate " + CC.tnGUIHead + getItemName(), Items.NETHERQUARTZ);
	}

	@Override
	public String[] getDescription() {
		return new String[] {CC.tnInfo + "Gives you a white sparkling", CC.tnInfo + "halo effect above your head!"};
	}

	@Override
	public String getPermRequired() {
		return "whitehaloparticle";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.LOYALIST;
	}

}
