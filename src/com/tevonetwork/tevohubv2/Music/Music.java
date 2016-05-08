package com.tevonetwork.tevohubv2.Music;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.tevonetwork.tevohubv2.TevoHubV2;

public abstract class Music {

	private TevoHubV2 main = TevoHubV2.getInstance();
	private Block jukeboxblock;
	private Jukebox jukebox;
	private Material record;
	private Player owner;
	private int jukebox_remove_Task;
	private int jukebox_particles_Task;
	private long song_lengthTicks;
	
	public Music(long songlength, Player owner, Material record)
	{
		this.owner = owner;
		this.song_lengthTicks = songlength;
		this.record = record;
	}
	
	public boolean start()
	{
		this.jukeboxblock = this.owner.getLocation().getBlock();
		boolean space = false;
		if (this.jukeboxblock.getType() != Material.AIR)
		{
			for (BlockFace blockface : BlockFace.values())
			{
				if (this.jukeboxblock.getRelative(blockface).getType() == Material.AIR)
				{
					this.jukeboxblock = this.jukeboxblock.getRelative(blockface);
					space = true;
					break;
				}
			}
		}
		else
		{
			space = true;
		}
		if (!space)
		{
			return false;
		}
		this.jukeboxblock.setType(Material.JUKEBOX);
		this.jukebox = (Jukebox)this.jukeboxblock.getState();
		this.jukebox.setPlaying(this.record);
		BukkitScheduler sch = Bukkit.getScheduler();
		this.jukebox_particles_Task = sch.scheduleSyncRepeatingTask(main, new Runnable()
		{
			@Override
			public void run()
			{
				spawnParticles();	
			}
			
		}, 0L, 10L);
		
		this.jukebox_remove_Task = sch.scheduleSyncDelayedTask(main, new Runnable()
		{
			@Override
			public void run()
			{
				remove();
			}
		}, this.song_lengthTicks);
		
		return true;
	}
	
	public void stop()
	{
		BukkitScheduler sch = Bukkit.getScheduler();
		sch.cancelTask(getParticleTaskID());
		sch.cancelTask(getRemoveTaskID());
		this.jukebox.setPlaying(null);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				resetBlock();
			}
		}.runTask(main);
	}
	
	private void remove()
	{
		Bukkit.getScheduler().cancelTask(getParticleTaskID());
		this.jukebox.setPlaying(null);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				resetBlock();
			}
		}.runTask(main);
		MusicManager.removeMusic(this.owner, false);
	}
	
	private void resetBlock()
	{
		this.jukeboxblock.setType(Material.AIR);
	}
	
	private void spawnParticles()
	{
		Location loc = this.jukebox.getLocation();
		loc.getWorld().spigot().playEffect(loc, Effect.NOTE, 0, 0, 0.5F, 0.2F, 0.5F, 1F, 6, 50);
	}
	
	public Jukebox getJukebox()
	{
		return this.jukebox;
	}
	
	public Block getBlock()
	{
		return this.jukeboxblock;
	}
	
	public Material getRecord()
	{
		return this.record;
	}
	
	public Player getOwner()
	{
		return this.owner;
	}
	
	public long getSongLength()
	{
		return this.song_lengthTicks;
	}
	
	public int getParticleTaskID()
	{
		return this.jukebox_particles_Task;
	}
	
	public int getRemoveTaskID()
	{
		return this.jukebox_remove_Task;
	}
}

