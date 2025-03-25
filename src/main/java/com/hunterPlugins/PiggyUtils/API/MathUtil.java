/*    */ package com.hunterPlugins.PiggyUtils.API;
/*    */ 
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import net.runelite.api.coords.WorldPoint;
/*    */ 
/*    */ 
/*    */ public class MathUtil
/*    */ {
/*    */   public static int random(int min, int max) {
/* 10 */     return ThreadLocalRandom.current().nextInt(min, max + 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static WorldPoint randomizeTile(WorldPoint wp, int rX, int rY) {
/* 21 */     return wp.dx(random(-rX, rX + 1)).dy(random(-rY, rY + 1));
/*    */   }
/*    */   
/*    */   public static WorldPoint randomizeTile2(WorldPoint wp, int rX, int rY) {
/* 25 */     return wp.dx(random(rX, rX + 1)).dy(random(rY, rY + 1));
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\API\MathUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */