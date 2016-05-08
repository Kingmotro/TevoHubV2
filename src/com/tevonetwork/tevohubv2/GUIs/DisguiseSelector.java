package com.tevonetwork.tevohubv2.GUIs;

import java.util.List;

import org.bukkit.entity.Player;
import com.tevonetwork.tevoapi.API.Util.CC;
import com.tevonetwork.tevoapi.API.Util.ItemStackFactory;
import com.tevonetwork.tevoapi.Core.Items;
import com.tevonetwork.tevohubv2.Disguise.DisguiseManager;
import com.tevonetwork.tevohubv2.Util.PermsHandler;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class DisguiseSelector extends GUI{

	public DisguiseSelector(Player p) {
		super("Disguises", 26, p);
	}

	@Override
	public void open() {
		Player p = getViewer();
		
		List<VanityItem> icons = VanityManager.getAvailableDisguises();
		
		int index = 11;
		
		for (VanityItem icon : icons)
		{
			if ((DisguiseManager.getActiveDisguise(p) != null) && (icon.getActiveIcon().getItemMeta().getDisplayName().equalsIgnoreCase(DisguiseManager.getActiveItem(p).getActiveIcon().getItemMeta().getDisplayName())))
			{
				setActiveIcon(index, icon);
			}
			else if (PermsHandler.checkAccess(p, icon.getPermRequired()))
			{
				setIconUnlocked(index, icon);
			}
			else
			{
				if (icon.getExclusiveRank() != null)
				{
					setIconUnlockRank(index, icon);
				}
				else
				{
					setIconUnlockStore(index, icon);
				}
			}
			index++;
		}
		
		
		setIcon(4, VanityManager.vanityBack());
		setIcon(16, new ItemStackFactory().createItemStack(Items.REDSTAINEDGLASSPANE, CC.tnInfo + "More coming soon..."));
		p.openInventory(getMenu());
		p.updateInventory();
		
	}
	
	

}
