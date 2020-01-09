package me.drkmatr1984.Trails;

import java.io.File;
import java.util.ArrayList;
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
	private File dataFolder;
	private MainConfig config;
	private TrailConfig trailConfig;
	private LanguageConfig langConfig;
	
	public TrailsConfigManager(Trails plugin) {
		this.plugin = plugin;
		this.dataFolder = plugin.getDataFolder();
		if(!(dataFolder.exists())){
			dataFolder.mkdir();
		}
		this.config = new MainConfig();
		this.trailConfig = new TrailConfig();
		this.langConfig = new LanguageConfig();
	}
	
	public MainConfig getConfig() {
		return this.config;
	}
	
	public TrailConfig getTrailConfig() {
		return this.trailConfig;
	}
	
	public LanguageConfig getLanguageConfig() {
		return this.langConfig;
	}
	
	public class MainConfig
	{
		private File dataFile;
		private FileConfiguration data;
		
		private boolean isPathsInWilderness;
		private boolean isTownyPathsPerm;
		private boolean isUseTrailsFlag;
		private boolean isSaveData;
		private int interval;
		
		public MainConfig() {
			saveDefaultConfig();
			loadConfig();
		}
		
		public void loadConfig() {
			data = YamlConfiguration.loadConfiguration(dataFile);
			this.isPathsInWilderness = data.getBoolean("Plugin-Integration.Towny.PathsInWilderness");
			this.isTownyPathsPerm = data.getBoolean("Plugin-Integration.Towny.TownyPathsPerm");
			this.isUseTrailsFlag = data.getBoolean("Plugin-Integration.WorldGuard.UseTrailsFlag");
			this.isSaveData = data.getBoolean("Data.Enabled");
			this.interval = data.getInt("Data.Interval");
		}
		
		public void saveDefaultConfig() {
		    if (dataFile == null) {
		        dataFile = new File(dataFolder, "config.yml");
		    }
		    if (!dataFile.exists()) {           
		        plugin.saveResource("config.yml", false);
		    }
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

		public TrailConfig() {
			saveDefaultTrailConfig();
			loadConfig();
		}
		
		public void saveDefaultTrailConfig() {
		    if (dataFile == null) {
		        dataFile = new File(dataFolder, "trails.yml");
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
	
    public class LanguageConfig{
		
		private File dataFile;
		private FileConfiguration data;
		public String noPerms;
		public String errorWriteAccess;
		public String cantSaveBlockData;
		public String cantDecodeBlockData;
		public String cantCreateBlockData;
		public String cantSavePlayerData;
		public String cantCreatePlayerData;

		public LanguageConfig() {
			saveDefaultLanguageFileConfig();
			loadConfig();
		}
		
		public void saveDefaultLanguageFileConfig() {
			//pickup toggle data
		    if (dataFile == null) {
		        dataFile = new File(dataFolder, "language.yml");
		    }
		    if (!dataFile.exists()) {           
		        plugin.saveResource("language.yml", false);
		    }
	    }
		
		public void loadConfig() {
			data = YamlConfiguration.loadConfiguration(dataFile);
			noPerms = data.getString("");
			errorWriteAccess = data.getString("error.write-access");
			cantSaveBlockData = data.getString("error.cant-save-block-data");
			cantDecodeBlockData = data.getString("error.cant-decode-block-data");
			cantCreateBlockData = data.getString("error.cant-create-block-data");
			cantSavePlayerData = data.getString("error.cant-save-player-data");
			cantCreatePlayerData = data.getString("error.cant-create-player-data");
		}
		
	}
	
}