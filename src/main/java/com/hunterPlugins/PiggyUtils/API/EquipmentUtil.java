/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.example.EthanApiPlugin.Collections.Equipment;
/*    */ import com.example.EthanApiPlugin.Collections.EquipmentItemWidget;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.runelite.api.widgets.Widget;
/*    */ 
/*    */ public class EquipmentUtil
/*    */ {
/*    */   public enum EquipmentSlot
/*    */   {
/* 14 */     HEAD(0), CAPE(1), NECKLACE(2), MAIN_HAND(3),
/* 15 */     TORSO(4), OFF_HAND(5), AMMO(13), LEGS(7),
/* 16 */     HANDS(9), FEET(10), RING(12); public int getIndex() {
/* 17 */       return this.index;
/*    */     }
/*    */     private final int index;
/*    */     EquipmentSlot(int index) {
/* 21 */       this.index = index;
/*    */     }
/*    */   }
/*    */   
/*    */   public static Optional<EquipmentItemWidget> getItemInSlot(EquipmentSlot slot) {
/* 26 */     return Equipment.search().filter(item -> {
/*    */           EquipmentItemWidget iw = (EquipmentItemWidget)item;
/*    */           return (iw.getEquipmentIndex() == slot.getIndex());
/* 29 */         }).first();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasItem(String name) {
/* 34 */     return Equipment.search().nameContainsNoCase(name).first().isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasAnyItems(String... names) {
/* 39 */     for (String name : names) {
/* 40 */       if (hasItem(name)) {
/* 41 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static boolean hasItems(String... names) {
/* 50 */     for (String name : names) {
/* 51 */       if (!hasItem(name)) {
/* 52 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public static boolean hasItems(int... ids) {
/* 60 */     for (int id : ids) {
/* 61 */       if (!hasItem(id)) {
/* 62 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 66 */     return true;
/*    */   }
/*    */   
/*    */   public static boolean hasItems(List<Integer> ids) {
/* 70 */     for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext(); ) { int id = ((Integer)iterator.next()).intValue();
/* 71 */       if (!hasItem(id)) {
/* 72 */         return false;
/*    */       } }
/*    */ 
/*    */     
/* 76 */     return true;
/*    */   }
/*    */   
/*    */   public static boolean hasItem(int id) {
/* 80 */     return Equipment.search().withId(id).first().isPresent();
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\EquipmentUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */