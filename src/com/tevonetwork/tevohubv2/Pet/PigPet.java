package com.tevonetwork.tevohubv2.Pet;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;

public class PigPet extends Pet implements VanityItem{

	public PigPet(String petname, Player owner) {
		super(petname, owner, EntityType.PIG);
	}

	@Override
	public String getItemName() {
		return "Pig Pet";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.SPAWNPIG, CC.tnGUIHead + "Activate " + getItemName(), getDescription());
	}

	@Override
	public String[] getDescription() {
		return new String[]{CC.tnInfo + "Click to access the", CC.tnValue + "Pig Pet"};
	}

	@Override
	public String getPermRequired() {
		return "pigpet";
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Deactivate " + CC.tnGUIHead + getItemName(), Items.SPAWNPIG);
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.MYSTIC;
	}


}
