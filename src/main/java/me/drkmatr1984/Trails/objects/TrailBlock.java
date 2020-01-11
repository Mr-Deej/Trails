package me.drkmatr1984.Trails.objects;


import java.io.Serializable;
import org.bukkit.Location;

public class TrailBlock implements Serializable{
	
	private static final long serialVersionUID = -5944092517430475806L;
	private WrappedLocation location;
	private Integer walks = 0;
	private Link link;
	
	/**
	 * 
	 */
	
	public TrailBlock(WrappedLocation location, Integer numberOfWalks, Link link) {
		this.location = location;
		this.walks = numberOfWalks;
		this.link = link;
	}
	
	public TrailBlock(Location location, Integer numberOfWalks, Link link) {
		this.location = new WrappedLocation(location);
		this.walks = numberOfWalks;
		this.link = link;
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
		return this.link.getTrailName();
	}
	
	public Link getLink() {
		return this.link;
	}
}