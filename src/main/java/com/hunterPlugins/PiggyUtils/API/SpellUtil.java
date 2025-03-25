/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.example.PacketUtils.WidgetInfoExtended;
/*    */ import net.runelite.api.Client;
/*    */ import net.runelite.api.widgets.Widget;
/*    */ 
/*    */ public class SpellUtil {
/*    */   public static WidgetInfoExtended parseStringForWidgetInfoExtended(String input) {
/*  9 */     for (WidgetInfoExtended value : WidgetInfoExtended.values()) {
/* 10 */       if (value.name().equalsIgnoreCase("SPELL_" + input.replace(" ", "_"))) {
/* 11 */         return value;
/*    */       }
/*    */     } 
/* 14 */     return null;
/*    */   }
/*    */   
/*    */   public static Widget getSpellWidget(Client client, String input) {
/* 18 */     return client.getWidget(parseStringForWidgetInfoExtended(input).getPackedId());
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\SpellUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */