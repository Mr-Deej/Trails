package me.drkmatr1984.Trails.objects;


import java.io.Serializable;
import org.bukkit.Location;

public class TrailBlock implements Serializable{
	
	private static final long serialVersionUID = -5944092517430475806L;
	private WrappedLocation location;
	private Integer walks = 0;
	private String trail;
	
	/**
	 * 
	 */
	
	public TrailBlock(WrappedLocation location, Integer numberOfWalks, String trail) {
		this.location = location;
		this.walks = numberOfWalks;
		this.trail = trail;
	}
	
	public TrailBlock(Location location, Integer numberOfWalks, String trail) {
		this.location = new WrappedLocation(location);
		this.walks = numberOfWalks;
		this.trail = trail;
	}
	
	public Location getLocation() {
		return this.location.getLocation();
	}
	
	public int getWalks() {
		return this.walks;
	}
	
	public WrappedLocation getWrappedLocation() {
		return this.location;
	}
	
	public String getTrailName() {
		return this.trail;
	}
}