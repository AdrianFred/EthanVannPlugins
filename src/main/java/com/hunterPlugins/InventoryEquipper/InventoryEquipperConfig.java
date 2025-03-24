package com.hunterPlugins.InventoryEquipper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup("inventoryequipper")
public interface InventoryEquipperConfig extends Config {

    @ConfigItem(
            keyName = "hotkey",
            name = "Hotkey",
            description = "Hotkey to equip items in your inventory",
            position = 0
    )
    default Keybind getHotkey() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(
            keyName = "itemIdsOrNames",
            name = "Item IDs or Names",
            description = "Comma-separated list of item IDs or names to equip",
            position = 1
    )
    default String itemIdsOrNames() {
        return "Axe";
    }

    @ConfigItem(
            keyName = "maxPerTick",
            name = "Max Items Per Tick",
            description = "Maximum number of items to equip per game tick",
            position = 2
    )
    default int maxPerTick() {
        return 3;
    }

    @ConfigItem(
            keyName = "copyGear",
            name = "Copy Gear",
            description = "Toggle to copy your equipped gear to the clipboard",
            position = 3
    )
    default boolean copyGear() {
        return false;
    }

    @ConfigItem(
            keyName = "copyInventory",
            name = "Copy Inventory",
            description = "Toggle to copy your inventory to the clipboard",
            position = 4
    )
    default boolean copyInventory() {
        return false;
    }
}
