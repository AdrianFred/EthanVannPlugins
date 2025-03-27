package com.hunterPlugins.AttackGoblinLoop;

import com.example.EthanApiPlugin.Collections.NPCs;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.hunterPlugins.AttackGoblinHotkey.AttackGoblinHotkeyConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@Slf4j
@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[No]</font>Attack Goblin loop</html>",
        description = "Press a key to attack the nearest Goblin on a loop",
        tags = {"hunter"}
)
@Extension
public class AttackGoblinLoopPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private AttackGoblinLoopConfig config;

    @Provides
    AttackGoblinLoopConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(AttackGoblinLoopConfig.class);
    }

    private boolean isGoblinAttackActive = false;
    private boolean hasLogged = false;

    @Override
    protected void startUp() throws Exception {
        log.info("Goblin Attack Hotkey started!");
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("Goblin Attack Hotkey stopped!");
        var localPlayer = client.getLocalPlayer();
        var npc = NPCs.search().nameContains(config.npcName()).walkable().playerNotInteractingWith().nearestToPlayer().orElse(null);
        log.info("npc: {}", npc);
        log.info("localPlayer: {}", localPlayer);
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        if (config.shouldLog()) {

            log.info(String.valueOf(client.getGameState()));
            if (client.getGameState() == GameState.LOGGED_IN) {
                var localPlayer = client.getLocalPlayer();
                var npc = NPCs.search().nameContains(config.npcName()).walkable().playerNotInteractingWith().nearestToPlayer().orElse(null);
                log.info("npc: {}", npc);
                log.info("localPlayer: {}", localPlayer);

            }
        }
    }
}
