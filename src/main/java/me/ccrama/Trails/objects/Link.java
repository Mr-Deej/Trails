package me.ccrama.Trails.objects;

import org.bukkit.Material;

public class Link {
   private final Material mat;
   private final int decay;
   private final int id;
   private final int chanceoccur;
   private final Link next;

   public Link(Material material, int decaynum, int chance, int linknumb, Link nextlink) {
      this.mat = material;
      this.decay = decaynum;
      this.id = linknumb;
      this.chanceoccur = chance;
      this.next = nextlink;
   }

   public Material getMat() {
      return this.mat;
   }

   public int decayNumber() {
      return this.decay;
   }

   public int identifier() {
      return this.id;
   }

   public Link getNext() {
      return this.next;
   }

   public int chanceOccurance() {
      return this.chanceoccur;
   }

}
