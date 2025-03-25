/*    */ package com.hunterPlugins.PiggyUtils.strategy;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskManager
/*    */ {
/* 10 */   private List<AbstractTask> tasks = new LinkedList<>();
/*    */   
/*    */   public void addTask(AbstractTask task) {
/* 13 */     this.tasks.add(task);
/*    */   }
/*    */   
/*    */   public void removeTask(AbstractTask task) {
/* 17 */     this.tasks.remove(task);
/*    */   }
/*    */   
/*    */   public void clearTasks() {
/* 21 */     this.tasks.clear();
/*    */   }
/*    */   
/*    */   public boolean hasTasks() {
/* 25 */     return !this.tasks.isEmpty();
/*    */   }
/*    */   public List<AbstractTask> getTasks() {
/* 28 */     return this.tasks;
/*    */   }
/*    */   
/*    */   public void runTasks() {
/* 32 */     for (AbstractTask task : this.tasks) {
/* 33 */       if (task.validate()) {
/* 34 */         task.execute();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\strategy\TaskManager.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */