package com.tevonetwork.tevohubv2.Pet;

import net.minecraft.server.v1_8_R3.EntityInsentient;

import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public abstract class Pet {

	private Player owner;
	private EntityType pettype;
	private String petname;
	private Creature pet;
	
	public Pet(String petname, Player owner, EntityType type)
	{
		this.owner = owner;
		this.petname = petname;
		this.pettype = type;
	}
	
	public Player getOwner()
	{
		return this.owner;
	}
	
	public EntityType getEntityType()
	{
		return this.pettype;
	}
	
	public String getPetName()
	{
		return this.petname;
	}
	
	public void spawn()
	{
		this.pet = (Creature)this.owner.getWorld().spawnEntity(this.owner.getLocation(), this.pettype);
		if (this.pet instanceof Ageable)
		{
			((Ageable)this.pet).setBaby();
			((Ageable)this.pet).setAgeLock(true);
		}
		this.pet.setCustomName(this.petname);
		this.pet.setCustomNameVisible(true);
		this.pet.getWorld().spigot().playEffect(this.pet.getEyeLocation(), Effect.HEART);
	}
	
	public void updatePath()
	{
		if (this.pet instanceof Wolf)
		{
			((Wolf)this.pet).setTarget(null);
		}
		if (this.pet.getLocation().distance(this.owner.getLocation()) >= 20)
    	{
    		this.pet.teleport(this.owner);
    	}
        ((EntityInsentient) ((CraftEntity) this.pet).getHandle()).getNavigation().a(this.owner.getLocation().getX(), this.owner.getLocation().getY(), this.owner.getLocation().getZ(), 1.0D);
	}
	
	public void remove()
	{
		this.pet.getWorld().spigot().playEffect(this.pet.getEyeLocation(), Effect.PORTAL);
		this.pet.remove();
	}
	
}
