package me.drkmatr1984.Trails.compatibility;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class WorldGuardHook
{
	private WorldGuardPlugin wg;
	public static StateFlag CREATE_TRAILS;

	public WorldGuardHook(){
		this.wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
	    try {
	        // create a flag with the name "my-custom-flag", defaulting to true
	        StateFlag flag = new StateFlag("create-trails", true);
	        registry.register(flag);
	        CREATE_TRAILS = flag; // only set our field if there was no error
	    } catch (FlagConflictException e) {
	        // some other plugin registered a flag by the same name already.
	        // you can use the existing flag, but this may cause conflicts - be sure to check type
	        Flag<?> existing = registry.get("create-trails");
	        if (existing instanceof StateFlag) {
	            CREATE_TRAILS = (StateFlag) existing;
	        } else {
	            Bukkit.getLogger().log(Level.SEVERE, "Trails custom WorldGuard flag failed to register because"
	            		+ " another plugin has registered a flag with the same name.");
	        }
	    }
	}
	
	public boolean canBuild(Player p, Location l) {
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(l);
        if (!hasBypass(p, l)) {
            return query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(p), Flags.BUILD);
        }else {
            return true;
        }
    }


    public boolean hasBypass(Player p, Location l) {
        return WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(this.wg.wrapPlayer(p), BukkitAdapter.adapt(l.getWorld()));
    }
}