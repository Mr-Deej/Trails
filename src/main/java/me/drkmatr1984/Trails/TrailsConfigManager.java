package me.drkmatr1984.Trails;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.drkmatr1984.Trails.objects.Link;
import me.drkmatr1984.Trails.objects.Trail;

public class TrailsConfigManager
{
	private Trails plugin;
	private MainConfig config;
	
	public TrailsConfigManager(Trails plugin) {
		this.plugin = plugin;
		this.config = new MainConfig(plugin);
	}
	
	public MainConfig getConfig() {
		return this.config;
	}
	
	public class MainConfig
	{
		private boolean isPathsInWilderness;
		private boolean isTownyPathsPerm;
		private boolean isUseTrailsFlag;
		private boolean isSaveData;
		private int interval;
		
		public MainConfig(Trails plugin) {
			loadConfig();
		}
		
		public void loadConfig() {
			this.isPathsInWilderness = plugin.getConfig().getBoolean("Plugin-Integration.Towny.PathsInWilderness");
			this.isTownyPathsPerm = plugin.getConfig().getBoolean("Plugin-Integration.Towny.TownyPathsPerm");
			this.isUseTrailsFlag = plugin.getConfig().getBoolean("Plugin-Integration.WorldGuard.UseTrailsFlag");
			this.isSaveData = plugin.getConfig().getBoolean("Data.Enabled");
			this.interval = plugin.getConfig().getInt("Data.Interval");
		}
		
		public boolean isPathsInWilderness() {
			return this.isPathsInWilderness;
		}
		
		public boolean isTownyPathsPerm() {
			return this.isTownyPathsPerm;
		}
		
		public boolean isUseTrailsFlag() {
			return this.isUseTrailsFlag;
		}
		
		public boolean isSaveData() {
			return this.isSaveData;
		}
		
		public int getSaveInterval() {
			return this.interval;
		}
	}
	
	public class TrailConfig{
		
		private File dataFile;
		private FileConfiguration data;

		public TrailConfig(Trails plugin) {
			
		}
		
		public void saveDefaultTrailConfig() {
			//pickup toggle data
			if(!(plugin.getDataFolder().exists())){
				plugin.getDataFolder().mkdir();
			}
		    if (dataFile == null) {
		        dataFile = new File(plugin.getDataFolder(), "trails.yml");
		    }
		    if (!dataFile.exists()) {           
		        plugin.saveResource("trails.yml", false);
		    }
	    }
		
		public void loadConfig() {
			ArrayList<Link> links;
			data = YamlConfiguration.loadConfiguration(dataFile);
			ConfigurationSection trailsSection = data.getConfigurationSection("trails");
			for(String trailName : trailsSection.getKeys(false)){
				links = new ArrayList<Link>();
				ConfigurationSection linkSection = data.getConfigurationSection("trails." + trailName);
				for(String linkNumbers : linkSection.getKeys(false)) {
					ConfigurationSection linkConfig = data.getConfigurationSection("trails." + trailName + "." + linkNumbers);
					links.add(new Link(Material.valueOf(linkConfig.getString("Material")), linkConfig.getInt("MinWalks"), linkConfig.getInt("MaxWalks"), linkConfig.getInt("DegradeChance")));					
				}
				if(links.size()>1) {
					plugin.addTrail(new Trail(trailName, links));
				}else {
					Bukkit.getLogger().log(Level.SEVERE, "Your Trail, " + trailName + ", only has one link.");
					Bukkit.getLogger().log(Level.SEVERE, "All trails must have two or more.");
				}
				
			}
		}
		
	}
	
}