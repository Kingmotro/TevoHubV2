package com.tevonetwork.tevohubv2.Particle;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public abstract class Particle implements Runnable{

	private long tick_Rate;
	private long tick_Delay;
	private BukkitTask task;
	private Player owner;
	
	public Particle(long delay, long tickrate, Player p)
	{
		this.owner = p;
		this.tick_Rate = tickrate;
		this.tick_Delay = delay;
	}
	
	public abstract void tick();
	
	@Override
	public void run()
	{
		this.tick();
	}
	
	public Player getOwner()
	{
		return this.owner;
	}
	
	public long getTickRate()
	{
		return this.tick_Rate;
	}
	
	public long getTickDelay()
	{
		return this.tick_Delay;
	}
	
	public void setTask(BukkitTask task)
	{
		this.task = task;
	}
	
	public BukkitTask getTask()
	{
		return this.task;
	}
}
