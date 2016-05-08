package com.tevonetwork.tevohubv2.Gadget;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.tevonetwork.tevoapi.API.Cooldown.SilentCooldown;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.API.Util.UtilPlayer;
import com.tevonetwork.tevoapi.Core.Category;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevoapi.Core.Rank;
import com.tevonetwork.tevohubv2.TevoHubV2;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class SmiteGadget extends Gadget implements VanityItem{

	private TevoHubV2 main = TevoHubV2.getInstance();
	
	public SmiteGadget(Player owner) {
		super(owner, new ItemStackFactory().createItemStack(Items.BLAZEPOWDER, CC.tnValue + "Smite"));
	}

	@Override
	public void handleEvent(Event e) {
		if (e instanceof PlayerInteractEntityEvent)
		{
			PlayerInteractEntityEvent event = (PlayerInteractEntityEvent)e;
			Player p = event.getPlayer();
			if (!(event.getRightClicked() instanceof Player))
			{
				return;
			}
			if (!p.getItemInHand().hasItemMeta())
			{
				return;
			}
			if (p.getItemInHand().getType() != getItem().getType())
			{
				return;
			}
			if (!p.getName().equalsIgnoreCase(getOwner().getName()))
			{
				return;
			}
			if (SilentCooldown.isPlayeronSilentCooldown(p, "Smite"))
			{
				return;
			}
			if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getItem().getItemMeta().getDisplayName()))
			{
				final Player target = (Player)event.getRightClicked();
				target.getLocation().getWorld().strikeLightningEffect(target.getLocation());
				Vector dir = target.getLocation().getDirection();
				double x = new Random().nextDouble() * (0.9D - 0.5D) + 0.5D;
				double z = new Random().nextDouble() * (0.9D - 0.5D) + 0.5D;
				final Vector vector = new Vector(dir.getX() * x, 0.5D, dir.getZ() * z);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						target.setVelocity(vector);
					}
				}.runTaskLater(main, 2L);
				VanityManager.useAbility(p, "Smite");
				UtilPlayer.message(Category.VANITY, target, CC.tnInfo + "You were smited by " + CC.tnPlayer + p.getName());
				SilentCooldown.addSilentCooldown(p, "Smite", 10);
			}
		}
		
	}

	@Override
	public String getItemName() {
		return "Smite";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStackFactory().createItemStackwithLore(Items.BLAZEPOWDER, CC.tnGUIHead + "Equip " + getItemName(), getDescription());
	}

	@Override
	public ItemStack getActiveIcon() {
		return new ItemStackFactory().createItemStackwithGlow(CC.tnDisable + CC.fBold + "Unequip " + CC.tnGUIHead + getItemName(), Items.BLAZEPOWDER);
	}

	@Override
	public String[] getDescription() {
		return new String[] {CC.tnUse + "Right-Click " + CC.tnInfo + "players to " + CC.tnAbility + "Smite" + CC.tnInfo + " them!"};
	}

	@Override
	public String getPermRequired() {
		return "smite";
	}

	@Override
	public Rank getExclusiveRank() {
		return Rank.CRYSTAL;
	}

}
