package com.tevonetwork.tevohubv2.GUIs;

import java.util.List;

import org.bukkit.entity.Player;

import com.tevonetwork.tevohubv2.Gadget.GadgetManager;
import com.tevonetwork.tevohubv2.Util.PermsHandler;
import com.tevonetwork.tevohubv2.Vanity.VanityItem;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;

public class GadgetSelector extends GUI{

	public GadgetSelector(Player p) {
		super("Gadgets", 35, p);
		
	}

	@Override
	public void open() {
		
		Player p = getViewer();
		List<VanityItem> icons = VanityManager.getAvailableGadgets();
		
		int index = 11;
		
		for (VanityItem icon : icons)
		{
			if ((GadgetManager.getActiveItem(p) != null) && (icon.getActiveIcon().getItemMeta().getDisplayName().equalsIgnoreCase(GadgetManager.getActiveItem(p).getActiveIcon().getItemMeta().getDisplayName())))
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
			if (index == 16)
			{
				index = 20;
			}
		}
		
		setIcon(4, VanityManager.vanityBack());
		p.openInventory(getMenu());
		p.updateInventory();
	}
	
}
