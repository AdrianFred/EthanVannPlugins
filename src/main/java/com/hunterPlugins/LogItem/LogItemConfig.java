package com.hunterPlugins.LogItem;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("LogItem")
public interface LogItemConfig extends Config {
    @ConfigItem(
            keyName = "logItem",
            name = "Log Item",
            description = "Should we log the item to the chat?"
    )
    default boolean logItem() {
        return true;
    }

    @ConfigItem(
            keyName = "logItemName",
            name = "Log Item Name",
            description = ""
    )
    default String logItemName() {
        return "Oak Logs";
    }
}
