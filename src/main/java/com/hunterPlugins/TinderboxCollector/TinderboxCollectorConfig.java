package com.hunterPlugins.TinderboxCollector;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("TinderboxCollector")
public interface TinderboxCollectorConfig extends Config {

    @ConfigItem(
            keyName = "toggle",
            name = "Toggle",
            description = "Toggle the plugin on or off",
            position = 0
    )
    default boolean toggle() {
        return false;
    }
}
