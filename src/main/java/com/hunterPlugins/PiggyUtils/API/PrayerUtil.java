/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.example.EthanApiPlugin.EthanApiPlugin;
/*    */ import com.example.PacketUtils.WidgetInfoExtended;
/*    */ import com.example.Packets.MousePackets;
/*    */ import com.example.Packets.WidgetPackets;
/*    */ import java.util.HashMap;
/*    */ import net.runelite.api.Prayer;
/*    */ 
/*    */ public class PrayerUtil
/*    */ {
/* 12 */   public static final HashMap<Prayer, WidgetInfoExtended> prayerMap = new HashMap<>();
/*    */   static {
/* 14 */     prayerMap.put(Prayer.AUGURY, WidgetInfoExtended.PRAYER_AUGURY);
/* 15 */     prayerMap.put(Prayer.BURST_OF_STRENGTH, WidgetInfoExtended.PRAYER_BURST_OF_STRENGTH);
/* 16 */     prayerMap.put(Prayer.CHIVALRY, WidgetInfoExtended.PRAYER_CHIVALRY);
/* 17 */     prayerMap.put(Prayer.CLARITY_OF_THOUGHT, WidgetInfoExtended.PRAYER_CLARITY_OF_THOUGHT);
/* 18 */     prayerMap.put(Prayer.EAGLE_EYE, WidgetInfoExtended.PRAYER_EAGLE_EYE);
/* 19 */     prayerMap.put(Prayer.HAWK_EYE, WidgetInfoExtended.PRAYER_HAWK_EYE);
/* 20 */     prayerMap.put(Prayer.IMPROVED_REFLEXES, WidgetInfoExtended.PRAYER_IMPROVED_REFLEXES);
/* 21 */     prayerMap.put(Prayer.INCREDIBLE_REFLEXES, WidgetInfoExtended.PRAYER_INCREDIBLE_REFLEXES);
/* 22 */     prayerMap.put(Prayer.MYSTIC_MIGHT, WidgetInfoExtended.PRAYER_MYSTIC_MIGHT);
/* 23 */     prayerMap.put(Prayer.PIETY, WidgetInfoExtended.PRAYER_PIETY);
/* 24 */     prayerMap.put(Prayer.PRESERVE, WidgetInfoExtended.PRAYER_PRESERVE);
/* 25 */     prayerMap.put(Prayer.PROTECT_FROM_MAGIC, WidgetInfoExtended.PRAYER_PROTECT_FROM_MAGIC);
/* 26 */     prayerMap.put(Prayer.PROTECT_FROM_MELEE, WidgetInfoExtended.PRAYER_PROTECT_FROM_MELEE);
/* 27 */     prayerMap.put(Prayer.PROTECT_FROM_MISSILES, WidgetInfoExtended.PRAYER_PROTECT_FROM_MISSILES);
/* 28 */     prayerMap.put(Prayer.RETRIBUTION, WidgetInfoExtended.PRAYER_RETRIBUTION);
/* 29 */     prayerMap.put(Prayer.RIGOUR, WidgetInfoExtended.PRAYER_RIGOUR);
/* 30 */     prayerMap.put(Prayer.ROCK_SKIN, WidgetInfoExtended.PRAYER_ROCK_SKIN);
/* 31 */     prayerMap.put(Prayer.SHARP_EYE, WidgetInfoExtended.PRAYER_SHARP_EYE);
/* 32 */     prayerMap.put(Prayer.SMITE, WidgetInfoExtended.PRAYER_SMITE);
/* 33 */     prayerMap.put(Prayer.STEEL_SKIN, WidgetInfoExtended.PRAYER_STEEL_SKIN);
/* 34 */     prayerMap.put(Prayer.THICK_SKIN, WidgetInfoExtended.PRAYER_THICK_SKIN);
/* 35 */     prayerMap.put(Prayer.ULTIMATE_STRENGTH, WidgetInfoExtended.PRAYER_ULTIMATE_STRENGTH);
/* 36 */     prayerMap.put(Prayer.REDEMPTION, WidgetInfoExtended.PRAYER_REDEMPTION);
/* 37 */     prayerMap.put(Prayer.RAPID_RESTORE, WidgetInfoExtended.PRAYER_RAPID_RESTORE);
/* 38 */     prayerMap.put(Prayer.RAPID_HEAL, WidgetInfoExtended.PRAYER_RAPID_HEAL);
/* 39 */     prayerMap.put(Prayer.PROTECT_ITEM, WidgetInfoExtended.PRAYER_PROTECT_ITEM);
/* 40 */     prayerMap.put(Prayer.MYSTIC_LORE, WidgetInfoExtended.PRAYER_MYSTIC_LORE);
/* 41 */     prayerMap.put(Prayer.SUPERHUMAN_STRENGTH, WidgetInfoExtended.PRAYER_SUPERHUMAN_STRENGTH);
/* 42 */     prayerMap.put(Prayer.MYSTIC_WILL, WidgetInfoExtended.PRAYER_MYSTIC_WILL);
/*    */   }
/*    */   
/*    */   public static int getPrayerWidgetId(Prayer prayer) {
/* 46 */     return ((WidgetInfoExtended)prayerMap.getOrDefault(prayer, WidgetInfoExtended.PRAYER_THICK_SKIN)).getPackedId();
/*    */   }
/*    */   
/*    */   public static boolean isPrayerActive(Prayer prayer) {
/* 50 */     return EthanApiPlugin.getClient().isPrayerActive(prayer);
/*    */   }
/*    */   
/*    */   public static void togglePrayer(Prayer prayer) {
/* 54 */     if (prayer == null) {
/*    */       return;
/*    */     }
/* 57 */     MousePackets.queueClickPacket();
/* 58 */     WidgetPackets.queueWidgetActionPacket(1, getPrayerWidgetId(prayer), -1, -1);
/*    */   }
/*    */   
/*    */   public static void toggleMultiplePrayers(Prayer... prayers) {
/* 62 */     for (Prayer prayer : prayers)
/* 63 */       togglePrayer(prayer); 
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\PrayerUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */