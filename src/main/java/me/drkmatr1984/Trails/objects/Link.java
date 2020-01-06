package me.drkmatr1984.Trails.objects;

import java.io.Serializable;

import org.bukkit.Material;

public class Link implements Serializable
{
	private static final long serialVersionUID = -4891736089855889734L;
	
	private String material;
	private Integer minWalks;
	private Integer maxWalks;
	private Integer chance;
	
	public Link(Material material, Integer minWalks, Integer maxWalks, Integer chance) {
		this.material = material.toString();
		this.minWalks = 2;
		this.maxWalks = maxWalks;
		this.chance = chance;
	}

	public Material getMaterial() {
		return Material.getMaterial(material);
	}

	public Integer getMinWalks() {
		return minWalks;
	}

	public Integer getMaxWalks() {
		return maxWalks;
	}

	public Integer getDegradeChance() {
		return chance;
	}
	
	public void setMaterial(Material material) {
		this.material = material.toString();
	}
	
	public void setMinWalks(Integer minWalks) {
		this.minWalks = minWalks;
	}
	
	public void setMaxWalks(Integer maxWalks) {
		this.maxWalks = maxWalks;
	}
	
	public void setDegradeChance(Integer chance) {
		this.chance = chance;
	}
	
}