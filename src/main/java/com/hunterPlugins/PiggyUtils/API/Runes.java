/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Runes
/*    */ {
/* 19 */   AIR(1, 556),
/* 20 */   WATER(2, 555),
/* 21 */   EARTH(3, 557),
/* 22 */   FIRE(4, 554),
/* 23 */   MIND(5, 558),
/* 24 */   CHAOS(6, 562),
/* 25 */   DEATH(7, 560),
/* 26 */   BLOOD(8, 565),
/* 27 */   COSMIC(9, 564),
/* 28 */   NATURE(10, 561),
/* 29 */   LAW(11, 563),
/* 30 */   BODY(12, 559),
/* 31 */   SOUL(13, 566),
/* 32 */   ASTRAL(14, 9075),
/* 33 */   MIST(15, 4695),
/* 34 */   MUD(16, 4698),
/* 35 */   DUST(17, 4696),
/* 36 */   LAVA(18, 4699),
/* 37 */   STEAM(19, 4694),
/* 38 */   SMOKE(20, 4697),
/* 39 */   WRATH(21, 21880); private static final Map<Integer, Runes> runes; private final int id;
/*    */   private final int itemId;
/*    */   private BufferedImage image;
/*    */   
/*    */   static {
/* 44 */     ImmutableMap.Builder<Integer, Runes> builder = new ImmutableMap.Builder();
/* 45 */     for (Runes rune : values()) {
/* 46 */       builder.put(Integer.valueOf(rune.getId()), rune);
/*    */     }
/* 48 */     runes = (Map<Integer, Runes>)builder.build();
/*    */   }
/*    */   public int getId() {
/* 51 */     return this.id;
/*    */   } public int getItemId() {
/* 53 */     return this.itemId;
/*    */   }
/* 55 */   public BufferedImage getImage() { return this.image; } public void setImage(BufferedImage image) {
/* 56 */     this.image = image;
/*    */   }
/*    */   
/*    */   Runes(int id, int itemId) {
/* 60 */     this.id = id;
/* 61 */     this.itemId = itemId;
/*    */   }
/*    */   
/*    */   public static Runes getRune(int varbit) {
/* 65 */     return runes.get(Integer.valueOf(varbit));
/*    */   }
/*    */   
/*    */   public String getName() {
/* 69 */     String name = name();
/* 70 */     name = name.substring(0, 1) + name.substring(0, 1);
/* 71 */     return name;
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\Runes.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */