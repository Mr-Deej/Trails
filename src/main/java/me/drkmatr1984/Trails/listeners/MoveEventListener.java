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
import me.drkmatr1984.Trails.objects.TrailBlock;
import me.drkmatr1984.Trails.objects.WrappedLocation;
import me.drkmatr1984.customevents.moveEvents.SignificantPlayerMoveEvent;

public class MoveEventListener implements Listener {
	
	private Trails plugin;
	
	
	public MoveEventListener(Trails plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void walk(SignificantPlayerMoveEvent e) {
		Player p = e.getPlayer();
		p.sendMessage("Event is firing");
		if(!plugin.isToggled(p) || !(p.hasPermission("trails.use"))) {
			return;
		}
		// Check towny conditions
		if(plugin.getTownyHook()!=null) {
			if(plugin.getTownyHook().isWilderness(p)) {
				if(!(plugin.getConfigManager().getConfig().isPathsInWilderness())) {
					return;
				}
			}
			if(plugin.getConfigManager().getConfig().isTownyPathsPerm()) {
				p.sendMessage("Checking towny paths perms");
				if(plugin.getTownyHook().isInHomeNation(p) && !plugin.getTownyHook().hasNationPermission(p) && !plugin.getTownyHook().isInHomeTown(p)) {
					return;
				}
				if(plugin.getTownyHook().isInHomeTown(p) && !plugin.getTownyHook().hasTownPermission(p)) {
					return;
				}
			}		
		}
		// Check worldguard conditions
		if(plugin.getWgHook()!=null && !plugin.getWgHook().canBuild(p, p.getLocation().subtract(0.0D, 1.0D, 0.0D)))	{
			p.sendMessage("Worldguard checks programmed wrong");
			return;
		}
		Block trailBlock = e.getFrom().subtract(0.0D, 1.0D, 0.0D).getBlock();
		BlockPlaceEvent event = new BlockPlaceEvent(trailBlock, trailBlock.getState(), (trailBlock.getLocation().subtract(0.0D, 1.0D, 0.0D)).getBlock(),
				p.getInventory().getItemInMainHand(), p, true, EquipmentSlot.HAND);
		Bukkit.getServer().getPluginManager().callEvent(event);
		p.sendMessage("Good up until Checking if event is cancelled");
		if(!event.isCancelled()) {
			if((plugin.getDataManager().getBlockData().isTrailBlock(trailBlock) || plugin.isTrailStartMaterial(trailBlock))) {
				p.sendMessage("Making Trail!");
				degradePath(trailBlock);
			}else {
				p.sendMessage("Not the right type of block I guess?");
			}
		}else {
			p.sendMessage("BlockPlaceEvent is cancelled");
		} 
	}
	
	private void degradePath(Block block) {
		if(plugin.getDataManager().getBlockData().isTrailBlock(block)) {
			//Do stuff with already registered blocks
			TrailBlock tb = plugin.getDataManager().getBlockData().getTrailBlock(block);
			ArrayList<Link> links = plugin.getTrailByName(tb.getTrailName()).getLinks();
			for(Link link : links) {
				if(link.isLink(tb.getLink())) {
					if((links.indexOf(link)+1) == links.size())
						return;
					Link nextLink = links.get((links.indexOf(link)+1));
					int walks = tb.getWalks() + 1;
					if(walks >= link.getMinWalks() && walks < link.getMaxWalks() ) {
		    			int roll = new Random().nextInt(100);
		    			if(roll < link.getDegradeChance()) {
		    				changeNext(block, nextLink.getMaterial());
		    				plugin.getDataManager().getBlockData().addTrailBlock(new TrailBlock(new WrappedLocation(block.getLocation()), 0, nextLink));
		    				return;
		    			}
		    		}
					if(walks >= link.getMaxWalks()) {
		    			changeNext(block, nextLink.getMaterial());
		    			plugin.getDataManager().getBlockData().addTrailBlock(new TrailBlock(new WrappedLocation(block.getLocation()), 0, nextLink));
		    			return;
		    		}
					plugin.getDataManager().getBlockData().addTrailBlock(new TrailBlock(new WrappedLocation(block.getLocation()), walks, tb.getLink()));
				}
			}
		}else {
			//Start new trail
			plugin.getDataManager().getBlockData().addTrailBlock(new TrailBlock(block.getLocation(), 1, plugin.getTrailByStartMaterial(block.getType()).getFirstLink()));
		}
		/*
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
	    */
	}

	private void changeNext(Block block, Material material) {		
	    block.setType(material);
	    block.getState().update(true);
	    
	}	
	
}
	