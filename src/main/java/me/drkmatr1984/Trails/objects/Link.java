package me.drkmatr1984.Trails.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.bukkit.Material;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Link implements Serializable
{
	private static final long serialVersionUID = -4891736089855889734L;
	
	private String trailName;
	private String material;
	private Integer minWalks;
	private Integer maxWalks;
	private Integer chance;
	
	public Link(String trailName, Material material, Integer minWalks, Integer maxWalks, Integer chance) {
		this.setTrailName(trailName);
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

	public String getTrailName() {
		return trailName;
	}

	public void setTrailName(String trailName) {
		this.trailName = trailName;
	}
	
	public boolean isLink(Link link) {
		if(link.getDegradeChance().equals(this.chance) && link.getMaterial().equals(getMaterial()) && link.getMaxWalks().equals(this.maxWalks) 
				&& link.getMinWalks().equals(this.minWalks) && link.getTrailName().equals(this.trailName))
			return true;
		return false;
	}
	
	public String toBase64() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            // Save every element in the list            
            dataOutput.writeObject(this);        
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save link.", e);
        }        
    }
	
	public static Link fromBase64(String linkSerial) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(linkSerial));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Link link = (Link)dataInput.readObject();         
            dataInput.close();
            return link;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}