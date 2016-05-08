package com.tevonetwork.tevohubv2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.tevonetwork.tevoapi.API.Util.UtilLogger;
import com.tevonetwork.tevoapi.Core.LogLevel;
import com.tevonetwork.tevohubv2.Commands.HubCMD;
import com.tevonetwork.tevohubv2.Commands.ProfileCMD;
import com.tevonetwork.tevohubv2.Commands.SetHubCMD;
import com.tevonetwork.tevohubv2.Commands.SnowmanCMD;
import com.tevonetwork.tevohubv2.Commands.TevoHubCMD;
import com.tevonetwork.tevohubv2.Commands.TutorialCMD;
import com.tevonetwork.tevohubv2.Hub.Announcer;
import com.tevonetwork.tevohubv2.Hub.HubManager;
import com.tevonetwork.tevohubv2.Hub.TutorialManager;
import com.tevonetwork.tevohubv2.Listeners.EntityListeners;
import com.tevonetwork.tevohubv2.Listeners.InventoryListeners;
import com.tevonetwork.tevohubv2.Listeners.PlayerListeners;
import com.tevonetwork.tevohubv2.Listeners.ProjectileListeners;
import com.tevonetwork.tevohubv2.Pet.PetManager;
import com.tevonetwork.tevohubv2.Vanity.VanityManager;
import com.tevonetwork.tevohubv2.Vanity.WardrobeManager;

public class TevoHubV2 extends JavaPlugin{

	private UtilLogger logger;
	private static TevoHubV2 main;
	private ConfigManager cfm;
	private HubCMD hubcmd;
	
	@Override
	public void onEnable()
	{
		main = this;
		this.logger = new UtilLogger(this);
		checkDependencies();
		startManagers();
		registerCMDS();
		registerListeners();
		startTasks();
		this.logger.logEnableDisable(true);
	}
	
	@Override
	public void onDisable()
	{
		TutorialManager.shutdown();
		this.logger.logEnableDisable(false);
	}
	
	public static TevoHubV2 getInstance()
	{
		return main;
	}
	
	public HubCMD getHubCMD()
	{
		return this.hubcmd;
	}
	
	public ConfigManager getConfigManager()
	{
		return this.cfm;
	}
	
	public UtilLogger getUtilLogger()
	{
		return this.logger;
	}
	
	private void startTasks()
	{
		logger.logNormal("Plugin> Starting tasks...");
		BukkitScheduler s = this.getServer().getScheduler();
		s.scheduleSyncRepeatingTask(this, new PetManager(), 300L, 30L);
		s.scheduleSyncRepeatingTask(this, new WardrobeManager(), 300L, 6L);
		s.scheduleSyncRepeatingTask(this, new Announcer(), 300L, cfm.getConfig().getInt("announcer.interval") * 20);
		logger.logNormal("Plugin> Tasks have been started!");
	}
	
	private void registerListeners()
	{
		logger.logNormal("Plugin> Registering listeners...");
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new EntityListeners(), this);
		pm.registerEvents(new PlayerListeners(), this);
		pm.registerEvents(new InventoryListeners(), this);
		pm.registerEvents(new ProjectileListeners(), this);
		logger.logNormal("Plugin> Registered listners!");
	}
	
	private void registerCMDS()
	{
		logger.logNormal("Plugin> Registering commands...");
		this.hubcmd = new HubCMD();
		getCommand("tevohub").setExecutor(new TevoHubCMD());
		getCommand("sethub").setExecutor(new SetHubCMD());
		getCommand("profile").setExecutor(new ProfileCMD());
		getCommand("tutorial").setExecutor(new TutorialCMD());
		getCommand("snowman").setExecutor(new SnowmanCMD());
		logger.logNormal("Plugin> Registered Commands!");
	}
	
	private void startManagers()
	{
		logger.logNormal("Plugin> Starting managers...");
		this.cfm = new ConfigManager();
		this.cfm.load();
		VanityManager.loadItems();
		TutorialManager.load();
		HubManager.loadRegion();
		logger.logNormal("Plugin> Managers have been initialized!");
	}
	
	private void checkDependencies()
	{
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if (pm.getPlugin("TevoAPI") == null)
		{
			logger.logLevel(LogLevel.WARNING, "Plugin> TevoAPI is missing, disabling!");
			this.setEnabled(false);
		}
		else
		{
			logger.logNormal("Plugin> Found TevoAPI!");
		}
		if (pm.getPlugin("LibsDisguises") == null)
		{
			logger.logLevel(LogLevel.WARNING, "Plugin> LibsDisguises is missing, disabling!");
			this.setEnabled(false);
		}
		else
		{
			logger.logNormal("Plugin> Found LibsDisguises!");
		}
	}
}
