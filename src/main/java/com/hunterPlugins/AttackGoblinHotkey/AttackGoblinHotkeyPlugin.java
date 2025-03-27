package com.hunterPlugins.AttackGoblinHotkey;

import com.example.EthanApiPlugin.Collections.NPCs;
import com.example.EthanApiPlugin.Collections.query.NPCQuery;
import com.example.InteractionApi.NPCInteraction;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.hunterPlugins.api.utils.NpcUtils;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;
import com.hunterPlugins.api.*;

import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Comparator;
import java.util.function.Predicate;

@Slf4j
@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[No]</font>Attack Goblin Hotkey</html>",
        description = "Press a key to attack the nearest Goblin",
        tags = {"hunter"}
)
public class AttackGoblinHotkeyPlugin extends Plugin implements KeyListener {
    @Inject
    private Client client;

    @Inject
    private AttackGoblinHotkeyConfig config;

    @Inject
    private KeyManager keyManager;

    private boolean isGoblinAttackActive = false;
    
    @Provides
    AttackGoblinHotkeyConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(AttackGoblinHotkeyConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        log.info("Goblin Attack Hotkey started!");
        keyManager.registerKeyListener(this);
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("Goblin Attack Hotkey stopped!");
        keyManager.unregisterKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (config.attackGoblinHotkey().matches(e)) {
            isGoblinAttackActive = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    @Subscribe
    public void onGameTick(GameTick gameTick) {
        if (isGoblinAttackActive) {
            isGoblinAttackActive = false;

            if (config.logGoblinAttacks() && client.getGameState() == GameState.LOGGED_IN) {
                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Goblin Attack", null);
            }

            if (client.getGameState() == GameState.LOGGED_IN) {
                attackNearestGoblin();
            }
        }
    }

    private void attackNearestGoblin() {
        if (client.getLocalPlayer() == null || client.getGameState() != GameState.LOGGED_IN) {
            return;
        }

        log.info("Attacking nearest goblin");
        interactWithNearestGoblin("Attack");
    }

    public boolean interactNpc(NPC npc, String action) {
        if (npc == null) return false;
        return NPCInteraction.interact(npc, new String[]{action});
    }

    public boolean interactNpc(String npcName, String action, Predicate<NPC> condition) {
        NPC npc = NPCs.search().nameContains(npcName).withAction("Attack").filter(condition).first().orElse(null);
        return interactNpc(npc, action);
    }

    private void interactWithNearestGoblin(String action) {
        NPC nearestGoblin = getNearestGoblin();
        if (nearestGoblin != null) {
            //interactNpc(nearestGoblin, action);
            log.info("Goblin found nearby.");
            NpcUtils.interact(nearestGoblin, action);
        } else {
            log.info("No goblins found nearby.");
        }
    }

    private NPC getNearestGoblin() {
        if (client.getLocalPlayer() == null) {
            return null;
        }

        return NpcUtils.getNearestAttackableNpc("Goblin");


    }
}
