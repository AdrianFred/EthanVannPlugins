package com.hunterPlugins.ItemDropper;

import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.InteractionApi.InventoryInteraction;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.google.inject.Provides;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Item Dropper by Hunter</html>",
        description = "Automatically drops items on hotkey pressed or if the inventory is full",
        tags = {"ethan", "hunter"}
)
public class ItemDropperPlugin extends Plugin {

    private static final Logger log = LoggerFactory.getLogger(ItemDropperPlugin.class);

    @Inject
    private ItemDropperConfig config;

    @Inject
    private KeyManager keyManager;

    @Inject
    private ClientThread clientThread;

    private final List<Integer> itemIds = new ArrayList<>();
    private final List<String> itemNames = new ArrayList<>();
    private final ConcurrentLinkedQueue<Widget> itemsToDrop = new ConcurrentLinkedQueue<>();
    private boolean dropping = false;

    @Provides
    private ItemDropperConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ItemDropperConfig.class);
    }

    protected void startUp() {
        this.keyManager.registerKeyListener(this.dropItemsHotkey);
        this.itemsToDrop.clear();
        this.itemNames.clear();
        this.dropping = false;
        updateItemIds();
    }

    protected void shutDown() {
        this.dropping = false;
        this.keyManager.unregisterKeyListener(this.dropItemsHotkey);
    }

    @Subscribe
    private void onConfigChanged(ConfigChanged configChanged) {
        if (!configChanged.getGroup().equals("itemdropper")) return;
        if (!configChanged.getKey().equals("itemIds")) return;
        updateItemIds();
    }

    @Subscribe
    private void onGameTick(GameTick gameTick) {
        if (!this.dropping && this.config.dropIfInvFull() && Inventory.full()) {
            buildItemQueue();
            this.dropping = true;
        }

        if (this.dropping) {
            dropItems();
        }
    }

    private final HotkeyListener dropItemsHotkey = new HotkeyListener(() -> this.config.getHotkey()) {
        public void hotkeyPressed() {
            if (ItemDropperPlugin.this.dropping) return;
            ItemDropperPlugin.this.clientThread.invoke(() -> {
                ItemDropperPlugin.this.buildItemQueue();
                ItemDropperPlugin.this.dropping = true;
            });
        }
    };

    private void buildItemQueue() {
        this.itemsToDrop.clear();
        this.itemsToDrop.addAll(Inventory.search().idInList(this.itemIds).result());
        for (String name : this.itemNames) {
            this.itemsToDrop.addAll(
                    Inventory.search().matchesWildCardNoCase(name)
                            .filter(w -> !this.itemsToDrop.contains(w))
                            .result()
            );
        }
    }

    private void updateItemIds() {
        if (this.config.itemIdsOrNames().trim().isEmpty()) {
            this.itemIds.clear();
            this.itemNames.clear();
            return;
        }
        String[] parts = this.config.itemIdsOrNames().trim().split(", |,");
        for (String part : parts) {
            try {
                int id = Integer.parseInt(part.trim());
                this.itemIds.add(id);
            } catch (NumberFormatException ignored) {
                this.itemNames.add(part.trim());
            }
        }
    }

    private void dropItems() {
        int numOfItems = getRandomIntBetweenRange(2, this.config.maxPerTick());
        for (int i = 0; i < numOfItems; i++) {
            if (this.itemsToDrop.peek() == null) {
                this.dropping = false;
                return;
            }
            InventoryInteraction.useItem(this.itemsToDrop.poll(), new String[]{"Drop"});
        }
        if (this.itemsToDrop.isEmpty()) this.dropping = false;
    }

    public int getRandomIntBetweenRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}