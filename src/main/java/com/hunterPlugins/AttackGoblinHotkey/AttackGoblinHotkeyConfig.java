package com.hunterPlugins.AttackGoblinHotkey;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

import java.awt.event.KeyEvent;

@ConfigGroup("attackGoblinHotkey")
public interface AttackGoblinHotkeyConfig extends Config
{
    @ConfigItem(
            keyName = "attackGoblinHotkey",
            name = "Attack Goblin Hotkey",
            description = "The key to press to attack the nearest goblin.",
            position = 1
    )
    default Keybind attackGoblinHotkey()
    {
        return new Keybind(KeyEvent.VK_SPACE, 0);
    }

    @ConfigItem(
            keyName = "logGoblinAttacks",
            name = "Log Goblin Attacks",
            description = "Should goblin attacks be logged to the chat?",
            position = 2
    )
    default boolean logGoblinAttacks() { return true; }
}
