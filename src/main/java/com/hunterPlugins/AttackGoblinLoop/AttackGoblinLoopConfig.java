package com.hunterPlugins.AttackGoblinLoop;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("attackGoblinLoop")
public interface AttackGoblinLoopConfig extends Config {
    @ConfigItem(
            keyName = "npcName",
            name = "NPC Name",
            description = "The name of the NPC to attack"
    )
    default String npcName() {
        return "Goblin";
    }

    @ConfigItem(
            keyName = "eatFood",
            name = "Eat Food",
            description = "Should the plugin eat when low hp?"
    )
    default boolean eatFood() {
        return true;
    }

    @ConfigItem(
            keyName = "foodName",
            name = "Food Name",
            description = "The name of the food to eat"
    )
    default String foodName() {
        return "Lobster";
    }

    @ConfigItem(
            keyName = "foodHp",
            name = "Food HP %",
            description = "The hp % to eat at"
    )
    default int foodHp() {
        return 50;
    }

    @ConfigItem(
            keyName = "shouldLog",
            name = "Should log Values",
            description = "test"
    )
    default boolean shouldLog() {
        return false;
    }

}
