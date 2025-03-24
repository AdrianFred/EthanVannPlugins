package com.hunterPlugins.InventoryEquipper;

import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.InteractionApi.InventoryInteraction;
import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[Hunter]</font> Inventory Equipper</html>",
        description = "Automatically equips items in your inventory",
        tags = {"ethan", "hunter"}
)
public class InventoryEquipperPlugin extends Plugin {

    private static final Logger log = LoggerFactory.getLogger(InventoryEquipperPlugin.class);

    @Inject
    private InventoryEquipperConfig config;

    @Inject
    private KeyManager keyManager;

    @Inject
    private ClientThread clientThread;

    @Inject
    private Client client;

    private final List<Integer> itemIds = new ArrayList<>();
    private final List<String> itemNames = new ArrayList<>();
    private final ConcurrentLinkedQueue<Widget> itemsToEquip = new ConcurrentLinkedQueue<>();

    private boolean equipping = false;

    @Provides
    private InventoryEquipperConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(InventoryEquipperConfig.class);
    }

    @Override
    protected void startUp() {
        log.info("Inventory Equipper Plugin Started");
        keyManager.registerKeyListener(this.equipItemsHotkey);
        itemsToEquip.clear();
        itemNames.clear();
        equipping = false;
        updateItemIds();
    }

    @Override
    protected void shutDown() {
        log.info("Inventory Equipper Plugin Stopped");
        equipping = false;
        keyManager.unregisterKeyListener(this.equipItemsHotkey);
    }

    @Subscribe
    public void onConfigChanged(net.runelite.client.events.ConfigChanged event) {
        if (!event.getGroup().equals("inventoryequipper")) {
            return;
        }

        if (event.getKey().equals("itemIdsOrNames")) {
            updateItemIds();
        }
        else if (event.getKey().equals("copyGear")) {
            copyGear();
        }
        else if (event.getKey().equals("copyInventory")) {
            copyInventory();
        }
    }

    @Subscribe
    private void onGameTick(net.runelite.api.events.GameTick gameTick) {
        if (equipping) {
            equipItems();
        }
    }

    private final HotkeyListener equipItemsHotkey = new HotkeyListener(() -> config.getHotkey()) {
        @Override
        public void hotkeyPressed() {
            if (equipping) {
                return;
            }
            clientThread.invoke(() -> {
                buildItemQueue();
                equipping = true;
            });
        }
    };

    private void buildItemQueue() {
        itemsToEquip.clear();
        itemsToEquip.addAll(Inventory.search().idInList(itemIds).result());
        for (String name : itemNames) {
            itemsToEquip.addAll(
                    Inventory.search().matchesWildCardNoCase(name)
                            .filter(w -> !itemsToEquip.contains(w))
                            .result()
            );
        }
        log.info("Items found for equipping: " + itemsToEquip.size());
    }

    private void updateItemIds() {
        itemIds.clear();
        itemNames.clear();
        if (config.itemIdsOrNames().trim().isEmpty()) {
            return;
        }
        String[] parts = config.itemIdsOrNames().trim().split(", |,");
        for (String part : parts) {
            try {
                int id = Integer.parseInt(part.trim());
                itemIds.add(id);
            } catch (NumberFormatException ignored) {
                itemNames.add(part.trim().toLowerCase());
            }
        }
    }

    private void equipItems() {
        while (!itemsToEquip.isEmpty()) {
            Widget item = itemsToEquip.poll();
            if (item == null) {
                continue;
            }
            InventoryInteraction.useItem(item, new String[]{"Wield", "Wear", "Equip"});
        }
        equipping = false;
    }

    // New method to copy gear (equipment) to clipboard
    private void copyGear() {
        clientThread.invoke(() -> {
            StringBuilder output = new StringBuilder();
            Item[] equipmentItems = client.getItemContainer(InventoryID.EQUIPMENT).getItems();
            for (Item item : equipmentItems) {
                if (item != null && item.getId() != -1 && item.getId() != 6512) {
                    ItemComposition comp = client.getItemDefinition(item.getId());
                    output.append(comp.getName()).append(",");
                }
            }
            if (output.length() > 0) {
                output.deleteCharAt(output.length() - 1); // remove trailing comma
            }
            StringSelection selection = new StringSelection(output.toString());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            log.info("Gear copied to clipboard: " + output);
        });
    }

    // New method to copy inventory to clipboard
    private void copyInventory() {
        clientThread.invoke(() -> {
            StringBuilder output = new StringBuilder();
            Item[] inventoryItems = client.getItemContainer(InventoryID.INVENTORY).getItems();
            for (Item item : inventoryItems) {
                if (item != null && item.getId() != -1 && item.getId() != 6512) {
                    ItemComposition comp = client.getItemDefinition(item.getId());
                    output.append(comp.getName()).append(",");
                }
            }
            if (output.length() > 0) {
                output.deleteCharAt(output.length() - 1); // remove trailing comma
            }
            StringSelection selection = new StringSelection(output.toString());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            log.info("Inventory copied to clipboard: " + output);
        });
    }
}
