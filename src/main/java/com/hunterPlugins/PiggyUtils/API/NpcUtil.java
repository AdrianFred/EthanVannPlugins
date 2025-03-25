/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.example.EthanApiPlugin.Collections.NPCs;
/*    */ import com.example.EthanApiPlugin.Collections.query.NPCQuery;
/*    */ import java.util.Optional;
/*    */ import net.runelite.api.NPC;
/*    */ 
/*    */ 
/*    */ public class NpcUtil
/*    */ {
/*    */   public static Optional<NPC> getNpc(String name, boolean caseSensitive) {
/* 12 */     if (caseSensitive) {
/* 13 */       return NPCs.search().withName(name).nearestToPlayer();
/*    */     }
/* 15 */     return nameContainsNoCase(name).nearestToPlayer();
/*    */   }
/*    */ 
/*    */   
/*    */   public static NPCQuery nameContainsNoCase(String name) {
/* 20 */     return NPCs.search().filter(npcs -> (npcs.getName() != null && npcs.getName().toLowerCase().contains(name.toLowerCase())));
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\NpcUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */