/*     */ package com.hunterPlugins.PiggyUtils.API;
/*     */ 
/*     */ import com.example.EthanApiPlugin.Collections.Inventory;
/*     */ import com.example.EthanApiPlugin.Collections.NPCs;
/*     */ import com.example.EthanApiPlugin.Collections.query.NPCQuery;
/*     */ import com.google.inject.Inject;
/*     */ import java.util.List;
/*     */ import net.runelite.api.Client;
/*     */ import net.runelite.api.NPC;
/*     */ import net.runelite.api.Skill;
/*     */ import net.runelite.api.coords.WorldArea;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class PlayerUtil
/*     */ {
/*  17 */   private static final Logger log = LoggerFactory.getLogger(PlayerUtil.class);
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject
/*     */   private Client client;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoRetaliating() {
/*  28 */     return (this.client.getVarpValue(172) == 0);
/*     */   }
/*     */   
/*     */   public boolean inArea(WorldArea area) {
/*  32 */     return area.contains(this.client.getLocalPlayer().getWorldLocation());
/*     */   }
/*     */   
/*     */   public boolean inRegion(int region) {
/*  36 */     return (this.client.getLocalPlayer().getWorldLocation().getRegionID() == region);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inRegion(int... regions) {
/*  46 */     for (int region : regions) {
/*  47 */       if (inRegion(region)) {
/*  48 */         return true;
/*     */       }
/*     */     } 
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasItem(String name) {
/*  55 */     return (Inventory.getItemAmount(name) > 0);
/*     */   }
/*     */   
/*     */   public boolean hasItem(int id) {
/*  59 */     return (Inventory.getItemAmount(id) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int runEnergy() {
/*  68 */     return this.client.getEnergy() / 100;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int specEnergy() {
/*  76 */     return this.client.getVarpValue(300) / 10;
/*     */   }
/*     */   
/*     */   public int hp() {
/*  80 */     return this.client.getBoostedSkillLevel(Skill.HITPOINTS);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStaminaActive() {
/*  85 */     return (this.client.getVarbitValue(25) == 1);
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/*  89 */     return (this.client.getVarpValue(173) == 0);
/*     */   }
/*     */   
/*     */   public boolean inMulti() {
/*  93 */     return (this.client.getVarbitValue(4605) == 1);
/*     */   }
/*     */   
/*     */   public boolean isInteracting() {
/*  97 */     return this.client.getLocalPlayer().isInteracting();
/*     */   }
/*     */   
/*     */   public boolean isBeingInteracted() {
/* 101 */     return NPCs.search().interactingWithLocal().first().isPresent();
/*     */   }
/*     */   
/*     */   public boolean isBeingInteracted(String name) {
/* 105 */     return NPCs.search().filter(npc -> (npc.getName() != null && npc.getName().equalsIgnoreCase(name))).interactingWithLocal().first().isPresent();
/*     */   }
/*     */   
/*     */   public NPCQuery getBeingInteracted(String name) {
/* 109 */     return NPCs.search().filter(npc -> (npc.getName() != null && npc.getName().equalsIgnoreCase(name))).interactingWithLocal();
/*     */   }
/*     */   public NPCQuery getBeingInteracted(List<String> names) {
/* 112 */     return NPCs.search().filter(npc -> (npc.getName() != null && names.contains(npc.getName()))).interactingWithLocal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTaskCount() {
/* 121 */     return this.client.getVarpValue(394);
/*     */   }
/*     */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\PlayerUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */