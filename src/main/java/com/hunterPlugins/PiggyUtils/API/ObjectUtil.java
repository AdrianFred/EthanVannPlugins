/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.example.EthanApiPlugin.Collections.TileObjects;
/*    */ import com.example.EthanApiPlugin.Collections.query.TileObjectQuery;
/*    */ import java.util.Arrays;
/*    */ import java.util.Optional;
/*    */ import net.runelite.api.ObjectComposition;
/*    */ import net.runelite.api.TileObject;
/*    */ 
/*    */ 
/*    */ public class ObjectUtil
/*    */ {
/*    */   public static Optional<TileObject> getNearestBank() {
/* 14 */     return TileObjects.search().filter(tileObject -> {
/*    */           ObjectComposition comp = TileObjectQuery.getObjectComposition(tileObject);
/*    */ 
/*    */           
/* 18 */           return comp != null && ((comp.getName().toLowerCase().contains("bank") && Arrays.<String>stream(comp.getActions()).anyMatch(action -> action.equalsIgnoreCase("Bank"))) || comp.getName().toLowerCase().contains("bank"));
/* 19 */         }).nearestToPlayer();
/*    */   }
/*    */   
/*    */   public static Optional<TileObject> getNearest(String name, boolean caseSensitive) {
/* 23 */     if (caseSensitive) {
/* 24 */       return TileObjects.search().withName(name).nearestToPlayer();
/*    */     }
/* 26 */     return withNameNoCase(name).nearestToPlayer();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Optional<TileObject> getNearest(int id) {
/* 31 */     return TileObjects.search().withId(id).nearestToPlayer();
/*    */   }
/*    */   
/*    */   public static Optional<TileObject> getNearestNameContains(String name) {
/* 35 */     return nameContainsNoCase(name).nearestToPlayer();
/*    */   }
/*    */   
/*    */   public static TileObjectQuery withNameNoCase(String name) {
/* 39 */     return TileObjects.search().filter(tileObject -> {
/*    */           ObjectComposition comp = TileObjectQuery.getObjectComposition(tileObject);
/*    */           return (comp == null) ? false : comp.getName().toLowerCase().equals(name.toLowerCase());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static TileObjectQuery nameContainsNoCase(String name) {
/* 48 */     return TileObjects.search().filter(tileObject -> {
/*    */           ObjectComposition comp = TileObjectQuery.getObjectComposition(tileObject);
/*    */           return (comp == null) ? false : comp.getName().toLowerCase().contains(name.toLowerCase());
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\ObjectUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */