package com.example;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.hunterPlugins.Example.ExamplePlugin;
import com.hunterPlugins.InventoryEquipper.InventoryEquipperPlugin;
import com.hunterPlugins.ItemDropper.ItemDropperPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import com.hunterPlugins.LogItem.LogItemPlugin;


public class ExamplePluginTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(EthanApiPlugin.class, PacketUtilsPlugin.class, LogItemPlugin.class, ExamplePlugin.class, ItemDropperPlugin.class, InventoryEquipperPlugin.class);
        RuneLite.main(args);
    }
}
