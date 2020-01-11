package me.drkmatr1984.Trails.compatibility;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.WorldCoord;

public class TownyHook
{
	
	private final TownyDataSource source;
	
	public TownyHook() {
		source = TownyAPI.getInstance().getDataSource();
	}
	
	public boolean hasTownPermission(Player p) {
		if(p.hasPermission("trails.towny.town")) {
			return true;
		}
		return false;
	}
	
	public boolean hasNationPermission(Player p) {
		if(p.hasPermission("trails.towny.nation")) {
			return true;
		}
		return false;
	}
	
	public boolean isWilderness(Location loc) {
		if(TownyAPI.getInstance().isWilderness(loc)) {
			return true;
		}
		return false;
	}
	
	public boolean isWilderness(Player p) {
		return isWilderness(p.getLocation().subtract(0.0D, 1.0D, 0.0D));
	}
	
	public boolean isWilderness(Block block) {
		return TownyAPI.getInstance().isWilderness(block.getLocation());		
	}
	
	public boolean isInHomeTown(Player p) {
		Resident resident;
		TownBlock block;
		try {
			resident = source.getResident(p.getName());
			block = WorldCoord.parseWorldCoord(p).getTownBlock();
			if(block.hasTown()) {
				if(resident.getTown() == block.getTown()) {
					return true;
				}
			}
		} catch (NotRegisteredException e) {
			//p.sendMessage("[Trails] Are you sure you are in a town/nation?");
		}		
		return false;
	}
	
	public boolean isInHomeNation(Player p) {
		Resident resident;
		TownBlock block;
		try {
			resident = source.getResident(p.getName());
			block = WorldCoord.parseWorldCoord(p).getTownBlock();
			if(block.hasTown()) {
				if(block.getTown().hasNation()) {
					if(resident.getTown().getNation() == block.getTown().getNation()) {
						return true;
					}
				}				
			}
		} catch (NotRegisteredException e) {
			//p.sendMessage("[Trails] Are you sure you are in a town/nation?");
		}		
		return false;
	}

}
