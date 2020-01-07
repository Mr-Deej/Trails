package me.drkmatr1984.Trails.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.drkmatr1984.Trails.Trails;
import me.drkmatr1984.Trails.objects.Link;
import me.drkmatr1984.Trails.objects.Trail;
import me.drkmatr1984.Trails.objects.TrailBlock;
import me.drkmatr1984.customevents.moveEvents.SignificantPlayerMoveEvent;

public class MoveEventListener implements Listener {
	
	private Trails plugin;
	
	
	public MoveEventListener(Trails plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void walk(SignificantPlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(!plugin.isToggled(p)) {
			return;
		}
		// Check towny conditions
		if(plugin.getTownyHook()!=null) {
			if(plugin.getTownyHook().isWilderness(p) && !plugin.getConfigManager().getConfig().isPathsInWilderness()) {
				return;
			}
			if(plugin.getConfigManager().getConfig().isTownyPathsPerm()) {
				if(plugin.getTownyHook().isInHomeNation(p) && !plugin.getTownyHook().hasNationPermission(p) && !plugin.getTownyHook().isInHomeTown(p)) {
					return;
				}
				if(plugin.getTownyHook().isInHomeTown(p) && !plugin.getTownyHook().hasTownPermission(p)) {
					return;
				}
			}		
		}
		// Check worldguard conditions
		if(plugin.getWgHook()!=null && !plugin.getWgHook().canBuild(p, p.getLocation().subtract(0.0D, 1.0D, 0.0D)))	
			return;
		Block trailBlock = e.getFrom().subtract(0.0D, 1.0D, 0.0D).getBlock();
		BlockPlaceEvent event = new BlockPlaceEvent(trailBlock, trailBlock.getState(), (trailBlock.getLocation().subtract(0.0D, 1.0D, 0.0D)).getBlock(),
				p.getInventory().getItemInMainHand(), p, true, EquipmentSlot.HAND);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			makePath(e.getFrom().subtract(0.0D, 1.0D, 0.0D).getBlock());
		}
	}
	
	private void makePath(Block block) {
		Material type = block.getType();
	    for(Trail trail : plugin.getTrails().values()) {
	    	ArrayList<Link> links = trail.getLinks();
	    	int size = links.size();
	    	for(int i = 0; i < size; i++) {
	    		Link link = links.get(i);
	    		if(link.getMaterial().equals(type)) {
	    			//the block is part of a trail type
	    			if((i+1) == links.size()) {
	    				return;
	    			}
	    			int walks = 1;
	    			for(TrailBlock tb : plugin.getDataManager().getBlockData().getTrailBlocks()) {
	    				if(tb.getWrappedLocation().isLocation(block.getLocation())) {	    					
	    					walks = tb.getWalks();
	    					plugin.getDataManager().getBlockData().removeTrailBlock(tb);
	    				}
	    			}
	    			if(walks >= link.getMinWalks() && walks < link.getMaxWalks() ) {
		    			int roll = new Random().nextInt(100);
		    			if(roll < link.getDegradeChance()) {
		    				changeNext(block, trail, i);
		    				return;
		    			}
		    		}
		    		if(walks >= link.getMaxWalks()) {
		    			changeNext(block, trail, i);
		    			return;
		    		}
		    		plugin.getDataManager().getBlockData().addTrailBlock(new TrailBlock(block.getLocation(), walks, trail.getTrailName()));	    		    			
	    		}
	    	}
	    	
	    }
	}

	private void changeNext(Block block, Trail trail, int linkIndex) {		
	    block.setType((trail.getLinks().get(linkIndex +1)).getMaterial());
	    block.getState().update(true);
	}	
	
}
	