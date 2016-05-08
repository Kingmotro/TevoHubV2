package com.tevonetwork.tevohubv2.Gadget;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.tevonetwork.tevoapi.API.Cooldown.Cooldown;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevohubv2.TevoHubV2;
import com.tevonetwork.tevohubv2.Hub.TutorialManager;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class PearlFlightGadget extends Gadget implements VanityItem{

	private TevoHubV2 main = TevoHubV2.getInstance();
	
	public PearlFlightGadget(Player owner) {
		super(owner, new ItemStackFactory().createItemStack(Items.ENDERPEARL, CC.tnValue + "Pearl Flight"));
	}

	@Override
	public void handleEvent(Event e) {
		if (e instanceof PlayerInteractEvent)
		{
			PlayerInteractEvent event = (PlayerInteractEvent)e;
			Player p = event.getPlayer();
			if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK))
			{
				return;
			}
			if ((p.getItemInHand() == null) || (!p.getItemInHand().hasItemMeta()))
			{
				return;
			}
			if (!p.getName().equalsIgnoreCase(getOwner().getName()))
			{
				return;
			}
			if (TutorialManager.inTutorial(p))
			{
				event.setCancelled(true);
				p.updateInventory();
				return;
			}
			if (Cooldown.isPlayeronCooldown(p, "Pearl Flight"))
			{
				UtilPlayer.onc(p, "Pearl Flight");
				event.setCancelled(true);
				p.updateInventory();
				return;
			}
			if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getItem().getItemMeta().getDisplayName()))
			{
				event.setCancelled(true);
				EnderPearl ep = p.launchProjectile(EnderPearl.class);
				ep.setVelocity(p.getLocation().getDirection().normalize().multiply(2.5D));
				ep.setPassenger(p);
				ep.setMetadata("Ability", new FixedMetadataValue(main, "Pearl Flight"));
				p.spigot().setCollidesWithEntities(false);
				VanityManager.useAbility(p, "Pearl Flight");
				p.updateInventory();
				Cooldown.addCooldown(p, "Pearl Flight", 5);
			}
		}
		if (e instanceof ProjectileHitEvent)
		{
			ProjectileHitEvent event = (ProjectileHitEvent)e;
			if (!(event.getEntity() instanceof EnderPearl))
			{
				return;
			}
			if (!(event.getEntity().getShooter() instanceof Player))
			{
				return;
			}
			Player p = (Player)event.getEntity().getShooter();
			if (!p.getName().equalsIgnoreCase(getOwner().getName()))
			{
				return;
			}
			if (event.getEntity().hasMetadata("Ability"))
			{
				if (event.getEntity().getPassenger() != null)
				{
					Entity entity = event.getEntity().getPassenger();
					event.getEntity().eject();
					entity.setVelocity(new Vector(0.0, 0.4, 0.0));
				}
				p.spigot().setCollidesWithEntities(true);
			}
		}
	}

	@Override
	public String getItemName() {
		return "Pearl Flight";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.ENDERPEARL, CC.tnGUIHead + "Equip " + getItemName(), getDescription());
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Unequip " + CC.tnGUIHead + getItemName(), Items.ENDERPEARL);
	}

	@Override
	public String[] getDescription() {
		return new String[] {CC.tnInfo + "Fly through the sky", CC.tnInfo + "on an ender pearl!", CC.tnUse + "Right Click" + CC.tnInfo + " to use " + CC.tnAbility + "Pearl Flight"};
	}

	@Override
	public String getPermRequired() {
		return "pearlflight";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.LOYALIST;
	}

}
