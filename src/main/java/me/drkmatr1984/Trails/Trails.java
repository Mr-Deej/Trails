package me.drkmatr1984.Trails;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.drkmatr1984.Trails.compatibility.TownyHook;
import me.drkmatr1984.Trails.compatibility.WorldGuardHook;
import me.drkmatr1984.Trails.listeners.MoveEventListener;
import me.drkmatr1984.Trails.objects.Trail;
import me.drkmatr1984.customevents.CustomEvents;

public class Trails extends JavaPlugin{
	
	private TownyHook townyHook = null;
	private WorldGuardHook wgHook = null;
	private TrailsDataManager dataManager = null;
	private TrailsConfigManager configManager = null;
	
	private CustomEvents customEvents;

	private Map<String, Trail> trails = new HashMap<String, Trail>();
	
	public void onEnable() {
	    this.saveDefaultConfig();
	    this.customEvents = new CustomEvents((JavaPlugin)this, false, false, true, false, false);
	  	this.customEvents.initializeLib();
	  	this.dataManager = new TrailsDataManager(this);
	  	this.configManager = new TrailsConfigManager(this);
		if(Bukkit.getServer().getPluginManager().getPlugin("Towny") != null) {
	         this.townyHook = new TownyHook();
	    }
		Bukkit.getServer().getPluginManager().registerEvents(new MoveEventListener(this), this);
		TrailsCommandHandler cHandler = new TrailsCommandHandler();
		getCommand("trails").setExecutor(cHandler);
		getCommand("paths").setExecutor(cHandler);
	}
	
	@Override
	public void onLoad() {
		if(Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
	        this.wgHook = new WorldGuardHook(this);
	    }
	}
	
	public void onDisable(){
		   this.dataManager.getBlockData().saveBlockList();
		   this.dataManager.getPlayerData().savePlayerList();
	}

	public TrailsConfigManager getConfigManager() 
	{
		return this.configManager;
	}
	
	public TrailsDataManager getDataManager() 
	{
		return this.dataManager;
	}
	
	public TownyHook getTownyHook() {
		return townyHook;
	}

	public WorldGuardHook getWgHook() {
		return wgHook;
	}

	public Map<String, Trail> getTrails() {
		return trails;
	}

	public void setTrails(Map<String, Trail> trails) {
		this.trails = trails;
	}
	
	public void addTrail(Trail trail) {
		this.trails.put(trail.getTrailName(), trail);
	}
	
	public Trail getTrailByName(String name) {
		for(String key : trails.keySet()) {
			if(key.equalsIgnoreCase(name)) {
				return trails.get(key);
			}
		}
		return null;
	}
	
	public boolean isToggled(Player p) {
		return this.dataManager.getPlayerData().isToggled(p);
	}
}