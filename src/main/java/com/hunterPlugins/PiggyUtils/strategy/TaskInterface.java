package com.hunterPlugins.PiggyUtils.strategy;

import java.util.function.Predicate;
import net.runelite.api.NPC;
import net.runelite.api.TileObject;

public interface TaskInterface {
  boolean validate();
  
  void execute();
  
  boolean interactObject(TileObject paramTileObject, String paramString);
  
  boolean interactObject(String paramString1, String paramString2, boolean paramBoolean);
  
  boolean interactObject(String paramString1, String paramString2, Predicate<TileObject> paramPredicate);
  
  boolean interactNpc(NPC paramNPC, String paramString);
  
  boolean interactNpc(String paramString1, String paramString2, boolean paramBoolean);
  
  boolean interactNpc(String paramString1, String paramString2, Predicate<NPC> paramPredicate);
}


/* Location:              C:\Users\AdrianF\Desktop\RuneLitePlugins\piggy-plugins-aio-1.6.0 - Copy.jar!\com\piggyplugins\PiggyUtils\strategy\TaskInterface.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */