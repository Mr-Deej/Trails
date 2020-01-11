package me.drkmatr1984.Trails.objects;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.Material;

public class Trail implements Serializable
{	
	private static final long serialVersionUID = -8693239935399585258L;
	
	private String trailName;
	private ArrayList<Link> links;
	
	public Trail(String trailName, ArrayList<Link> links) {
		this.trailName = trailName;
		this.setLinks(links);
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}

	public String getTrailName() {
		return trailName;
	}
	
	public Link getLinkByMaterial(Material material) {
		for(Link link : this.links) {
			if(link.getMaterial().equals(material)) {
				return link;
			}
		}
		return null;
	}

	public Link getFirstLink() {
		return links.get(0);
	}
}