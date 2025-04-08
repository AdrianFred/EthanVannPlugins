package com.hunterPlugins.TinderboxCollector;

import com.example.EthanApiPlugin.Collections.Bank;
import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.EthanApiPlugin.Collections.TileItems;
import com.example.EthanApiPlugin.Collections.TileObjects;
import com.example.EthanApiPlugin.Collections.query.TileObjectQuery;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.InteractionApi.InventoryInteraction;
import com.example.InteractionApi.TileObjectInteraction;
import com.example.Packets.MousePackets;
import com.example.Packets.MovementPackets;
import com.example.Packets.WidgetPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.hunterPlugins.api.utils.GameObjectUtils;
import com.hunterPlugins.api.utils.InteractionUtils;
import com.hunterPlugins.api.utils.InventoryUtils;
import com.hunterPlugins.api.utils.MessageUtils;
import java.awt.*;

import lombok.extern.java.Log;
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

@PluginDescriptor(
        name = "<html><font color=\"#7ecbf2\">[Hu]</font>Collect Tinderbox</html>",
        description = "Plugin to collect tinderboxes",
        enabledByDefault = false,
        tags = {"hunterno", "plugin"}
)
@Slf4j
public class TinderboxCollectorPlugin extends Plugin {

    @Inject private Client client;

    @Inject private TinderboxCollectorConfig config;

    @Inject private ClientThread clientThread;

    @Provides
    private TinderboxCollectorConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(TinderboxCollectorConfig.class);
    }

    public int timeout = 0;
    private boolean hasReachedTinderboxTile = false;
    private boolean pickingUpTinderboxes = false;

    @Override
    protected void startUp() throws Exception {
        timeout = 0;
        MessageUtils.addMessage("Tinderbox started", Color.red);
    }

    @Override
    protected void shutDown() throws Exception {
        timeout = 0;
        hasReachedTinderboxTile = false;
        pickingUpTinderboxes = false;
        MessageUtils.addMessage("Tinderbox stopped", Color.red);
    }

    @Subscribe
    private void onGameTick(GameTick gameTick) {
        if (
                client.getGameState() != GameState.LOGGED_IN ||
                        !config.toggle() ||
                        EthanApiPlugin.isMoving() ||
                        client.getLocalPlayer().getAnimation() != -1
        ) {
            return;
        }

        if (timeout > 0) {
            timeout--;
            return;
        }

        // Only care about door at 3088, 3251, 0
        WorldPoint doorTile = new WorldPoint(3088, 3251, 0);
        TileObject door = GameObjectUtils.nearest("Door");
        if (
                door != null &&
                        door.getWorldLocation().equals(doorTile) &&
                        GameObjectUtils.hasAction(door.getId(), "Open")
        ) {
            GameObjectUtils.interact(door, "Open");
            MessageUtils.addMessage("Opening door at 3088, 3251, 0", Color.RED);
            return;
        }

        // Banking Logic (If Inventory is Full)
        if (Inventory.full()) {
            pickingUpTinderboxes = false;
            if (!Bank.isOpen()) {
                TileObject closestBank = GameObjectUtils.nearest("Bank booth");
                MessageUtils.addMessage("hello bank", Color.BLUE);
                if (closestBank != null) {
                    MessageUtils.addMessage("Bank found", Color.gray);
                    GameObjectUtils.interact(closestBank, "Bank");
                }
            } else if (Inventory.getEmptySlots() < 27) {
                depositInventory();
            }
            return; // Important:  Exit after banking logic
        }

        //Logic when the inventory is not full and we need to get tinderboxes
        if (!hasReachedTinderboxTile) {
            WorldPoint tileToStand = new WorldPoint(3091, 3254, 0);
            if (client.getLocalPlayer().getWorldLocation().distanceTo(tileToStand) > 2) {
                MovementPackets.queueMovement(tileToStand);
                MessageUtils.addMessage("Walking to pick tile", Color.RED);
                return; // Wait for movement
            } else {
                hasReachedTinderboxTile = true;
            }
        }

        if (hasReachedTinderboxTile) {
            // Check if there are 28 tinderboxes on the ground and we are not
            // already picking them up
            var tinderboxesOnGround =
                    TileItems.search().nameContains("Tinderbox").result().size();
            MessageUtils.addMessage(String.valueOf(tinderboxesOnGround), Color.BLUE);
            if (tinderboxesOnGround == 28 && !pickingUpTinderboxes) {
                pickingUpTinderboxes = true; // Set the flag
            }

            if (pickingUpTinderboxes) {
                //Pickup items
                WorldPoint tileToStand = new WorldPoint(3091, 3254, 0);
                var groundTinderbox =
                        TileObjects
                                .search()
                                .atLocation(tileToStand).nameContains("Tinderbox");
                log.info(groundTinderbox.toString());

                if (groundTinderbox != null) {
                    InteractionUtils.interactWithTileItem("Tinderbox", "Take");


                    MessageUtils.addMessage("Picking up tinderbox from ground", Color.RED);
                    return; // Wait for the interaction
                } else {
                    pickingUpTinderboxes = false; // No more tinderboxes to pick up
                    MessageUtils.addMessage("Finished picking up tinderboxes", Color.RED);
                }
            } else {
                // Inventory is not full and not picking up from ground, so get from
                // shelf
                if (Inventory.getEmptySlots() == 28) {
                    MessageUtils.addMessage("Inventory is emptyyy", Color.BLUE);
                }
                TileObject shelf = GameObjectUtils.nearest(7079);
                if (shelf != null) {
                    GameObjectUtils.interact(shelf, "Search");
                    MessageUtils.addMessage("Taking Tinderbox from shelf", Color.RED);
                } else {
                    MessageUtils.addMessage("No Tinderbox shelf found", Color.RED);
                }

                // Drop non tinderbox items
                var item = InventoryUtils.getFirstItem("Tinderbox");
                if (item != null) {
                    MessageUtils.addMessage("Tinderbox found, dropping", Color.RED);
                    InventoryInteraction.useItem(item.getId(), "Drop");
                } else {
                    MessageUtils.addMessage("No Tinderbox to drop found", Color.RED);
                }
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
