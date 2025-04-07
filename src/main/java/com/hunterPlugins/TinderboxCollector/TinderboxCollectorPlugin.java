package com.hunterPlugins.TinderboxCollector;

import com.example.EthanApiPlugin.Collections.Bank;
import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.EthanApiPlugin.Collections.NPCs;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Packets.MousePackets;
import com.example.Packets.MovementPackets;
import com.example.Packets.NPCPackets;
import com.example.Packets.WidgetPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.hunterPlugins.PiggyUtils.API.InventoryUtil;
import com.hunterPlugins.PiggyUtils.API.MathUtil;
import com.hunterPlugins.api.utils.GameObjectUtils;
import com.hunterPlugins.api.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.awt.*;
import java.util.Optional;

@PluginDescriptor(
        name = "<html><font color=\"#7ecbf2\">[Hu]</font>Collect Tinderbox</html>",
        description = "Plugin to collect tinderboxes",
        enabledByDefault = false,
        tags = {"hunterno", "plugin"}
)
@Slf4j
public class TinderboxCollectorPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private TinderboxCollectorConfig config;

    @Inject
    private ClientThread clientThread;

    @Provides
    private TinderboxCollectorConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(TinderboxCollectorConfig.class);
    }

    public int timeout = 0;
    private int stuckTicks = 0;
    private int lastX = -1, lastY = -1;
    private NPC currentBanker = null;
    private boolean hasReachedBankTile = false;

    @Override
    protected void startUp() throws Exception {
        timeout = 0;
        MessageUtils.addMessage("Tinderbox started", Color.red);
    }

    @Override
    protected void shutDown() throws Exception {
        timeout = 0;
        hasReachedBankTile = false;
        MessageUtils.addMessage("Tinderbox stopped", Color.red);
    }

    @Subscribe
    private void onGameTick(GameTick gameTick) {
        if (client.getGameState() != GameState.LOGGED_IN || !config.toggle() || EthanApiPlugin.isMoving() || client.getLocalPlayer().getAnimation() != -1) {
            return;
        }

        if (timeout > 0) {
            timeout--;
            return;
        }

        // Only care about door at 3088, 3251, 0
        WorldPoint doorTile = new WorldPoint(3088, 3251, 0);
        TileObject door = GameObjectUtils.nearest("Door");
        if (door != null && door.getWorldLocation().equals(doorTile) && GameObjectUtils.hasAction(door.getId(), "Open")) {
            GameObjectUtils.interact(door, "Open");
            MessageUtils.addMessage("Opening door at 3088, 3251, 0", Color.RED);
            return;
        }

        if (Inventory.full()) {
            if (!Bank.isOpen()) {
                WorldPoint bankTile = new WorldPoint(3093, 3245, 0);
                WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

                if (!hasReachedBankTile) {
                    if (playerLocation.distanceTo(bankTile) > 1) {
                        MovementPackets.queueMovement(bankTile);
                        MessageUtils.addMessage("Walking to bank area before searching banker...", Color.CYAN);

                        int currentX = playerLocation.getX();
                        int currentY = playerLocation.getY();
                        if (currentX == lastX && currentY == lastY) {
                            stuckTicks++;
                        } else {
                            stuckTicks = 0;
                        }
                        lastX = currentX;
                        lastY = currentY;

                        if (stuckTicks >= 4) {
                            MessageUtils.addMessage("Can't reach bank tile. Maybe blocked?", Color.RED);
                            stuckTicks = 0;
                            timeout = 3;
                        }
                        return;
                    } else {
                        hasReachedBankTile = true;
                    }
                }

                if (hasReachedBankTile) {
                    if (currentBanker == null || !currentBanker.getName().contains("Banker")) {
                        currentBanker = NPCs.search().nameContains("Banker").withAction("Bank").nearestToPlayer().orElse(null);
                    }

                    if (currentBanker != null) {
                        MousePackets.queueClickPacket();
                        NPCPackets.queueNPCAction(currentBanker, "Bank");
                        timeout = MathUtil.random(1, 3);
                        stuckTicks = 0;
                        return;
                    } else {
                        MessageUtils.addMessage("No banker found nearby.", Color.RED);
                    }
                }
            } else if (Inventory.getEmptySlots() < 28) {
                depositInventory();
            }
        }
    }

    public void depositInventory() {
        Widget depositInventory = client.getWidget(WidgetInfo.BANK_DEPOSIT_INVENTORY);
        if (depositInventory != null) {
            MousePackets.queueClickPacket();
            WidgetPackets.queueWidgetAction(depositInventory, "Deposit inventory");
        }
    }
}