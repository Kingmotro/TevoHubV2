package com.tevonetwork.tevohubv2.Pet;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;

public class IronGolemPet extends Pet implements VanityItem{

	public IronGolemPet(String petname, Player owner) {
		super(petname, owner, EntityType.IRON_GOLEM);
	}
	
	@Override
	public String getItemName() {
		return "Iron Golem Pet";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.IRONINGOT, CC.tnGUIHead + "Activate " + getItemName(), getDescription());
	}

	@Override
	public String[] getDescription() {
		return new String[]{CC.tnInfo + "Click to access the", CC.tnValue + "Iron Golem Pet"};
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Deactivate " + CC.tnGUIHead + getItemName(), Items.IRONINGOT);
	}

	@Override
	public String getPermRequired() {
		return "irongolempet";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.CRYSTAL;
	}

}
