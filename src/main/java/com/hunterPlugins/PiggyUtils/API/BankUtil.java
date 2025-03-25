/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import com.example.EthanApiPlugin.Collections.Bank;
/*    */ import com.example.EthanApiPlugin.Collections.BankInventory;
/*    */ import com.example.EthanApiPlugin.Collections.query.ItemQuery;
/*    */ import com.example.EthanApiPlugin.EthanApiPlugin;
/*    */ import com.example.Packets.MousePackets;
/*    */ import com.example.Packets.WidgetPackets;
/*    */ import java.util.Collection;
/*    */ import net.runelite.api.widgets.Widget;
/*    */ import net.runelite.api.widgets.WidgetInfo;
/*    */ 
/*    */ 
/*    */ public class BankUtil
/*    */ {
/*    */   public static void depositAll() {
/* 17 */     Widget depositInventory = EthanApiPlugin.getClient().getWidget(WidgetInfo.BANK_DEPOSIT_INVENTORY);
/* 18 */     if (depositInventory != null) {
/* 19 */       MousePackets.queueClickPacket();
/* 20 */       WidgetPackets.queueWidgetAction(depositInventory, new String[] { "Deposit inventory" });
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ItemQuery nameContainsNoCase(String name) {
/* 25 */     return Bank.search().filter(widget -> widget.getName().toLowerCase().contains(name.toLowerCase()));
/*    */   }
/*    */   public static int getItemAmount(int itemId) {
/* 28 */     return getItemAmount(itemId, false);
/*    */   }
/*    */   
/*    */   public static int getItemAmount(int itemId, boolean stacked) {
/* 32 */     return stacked ? (
/* 33 */       (Integer)Bank.search().withId(itemId).first().map(Widget::getItemQuantity).orElse(Integer.valueOf(0))).intValue() : 
/* 34 */       Bank.search().withId(itemId).result().size();
/*    */   }
/*    */   
/*    */   public static int getItemAmount(String itemName) {
/* 38 */     return nameContainsNoCase(itemName).result().size();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasItem(int id) {
/* 43 */     return hasItem(id, 1, false);
/*    */   }
/*    */   
/*    */   public static boolean hasItem(int id, int amount) {
/* 47 */     return (getItemAmount(id, false) >= amount);
/*    */   }
/*    */   
/*    */   public static boolean hasItem(int id, int amount, boolean stacked) {
/* 51 */     return (getItemAmount(id, stacked) >= amount);
/*    */   }
/*    */   
/*    */   public static boolean hasAny(int... ids) {
/* 55 */     for (int id : ids) {
/* 56 */       if (getItemAmount(id) > 0) {
/* 57 */         return true;
/*    */       }
/*    */     } 
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean containsExcept(Collection<Integer> ids) {
/* 65 */     if (!Bank.isOpen()) {
/* 66 */       return false;
/*    */     }
/* 68 */     Collection<Widget> inventoryItems = BankInventory.search().result();
/*    */     
/* 70 */     for (Widget item : inventoryItems) {
/* 71 */       if (!ids.contains(Integer.valueOf(item.getItemId()))) {
/* 72 */         return true;
/*    */       }
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\BankUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */