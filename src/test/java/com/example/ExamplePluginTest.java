package com.example;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.hunterPlugins.AoeWarn.AoeWarningPlugin;
import com.hunterPlugins.AttackGoblinHotkey.AttackGoblinHotkeyPlugin;
import com.hunterPlugins.AttackGoblinLoop.AttackGoblinLoopPlugin;
import com.hunterPlugins.E3T4G.et34g;
import com.hunterPlugins.Example.ExamplePlugin;
import com.hunterPlugins.Firemaking.FiremakingPlugin;
import com.hunterPlugins.InventoryCombiner.InventoryCombinerPlugin;
import com.hunterPlugins.InventoryEquipper.InventoryEquipperPlugin;
import com.hunterPlugins.ItemDropper.ItemDropperPlugin;
import com.hunterPlugins.JadAutoPrayers.JadAutoPrayersPlugin;
import com.hunterPlugins.PiggyUtils.PiggyUtilsPlugin;
import com.hunterPlugins.TinderboxCollector.TinderboxCollectorPlugin;
import com.hunterPlugins.lucidcombat.LucidCombatPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import com.hunterPlugins.LogItem.LogItemPlugin;


public class ExamplePluginTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(EthanApiPlugin.class, PacketUtilsPlugin.class, LogItemPlugin.class, ExamplePlugin.class, ItemDropperPlugin.class, InventoryEquipperPlugin.class, JadAutoPrayersPlugin.class, PiggyUtilsPlugin.class, AttackGoblinHotkeyPlugin.class,
                LucidCombatPlugin.class, AttackGoblinLoopPlugin.class, InventoryCombinerPlugin.class, FiremakingPlugin.class, AoeWarningPlugin.class, et34g.class, TinderboxCollectorPlugin.class);
        RuneLite.main(args);
    }
}
