package com.hunterPlugins.PiggyUtils.API;

import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.EthanApiPlugin.Collections.query.ItemQuery;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.Packets.MousePackets;
import com.example.Packets.WidgetPackets;
import net.runelite.api.widgets.Widget;

import java.util.*;

public class InventoryUtil {

    public static boolean useItemNoCase(String name, String... actions) {
        return nameContainsNoCase(name).first()
                .map(item -> {
                    MousePackets.queueClickPacket();
                    WidgetPackets.queueWidgetAction(item, actions);
                    return true;
                }).orElse(false);
    }

    public static ItemQuery nameContainsNoCase(String name) {
        return Inventory.search().filter(widget -> widget.getName().toLowerCase().contains(name.toLowerCase()));
    }

    public static Optional<Widget> getById(int id) {
        return Inventory.search().withId(id).first();
    }

    public static Optional<Widget> getItemNameContains(String name, boolean caseSensitive) {
        return Inventory.search()
                .filter(widget -> caseSensitive
                        ? widget.getName().contains(name)
                        : widget.getName().toLowerCase().contains(name.toLowerCase()))
                .first();
    }

    public static Optional<Widget> getItemNameContains(String name) {
        return getItemNameContains(name, true);
    }

    public static Optional<Widget> getItem(String name, boolean caseSensitive) {
        return Inventory.search()
                .filter(widget -> caseSensitive
                        ? widget.getName().equals(name)
                        : widget.getName().equalsIgnoreCase(name))
                .first();
    }

    public static Optional<Widget> getItem(String name) {
        return getItem(name, true);
    }

    public static int getItemAmount(String name, boolean stacked) {
        if (stacked) {
            return nameContainsNoCase(name).first()
                    .map(Widget::getItemQuantity)
                    .orElse(0);
        }
        return nameContainsNoCase(name).result().size();
    }

    public static int getItemAmount(int id) {
        return getItemAmount(id, false);
    }

    public static int getItemAmount(int id, boolean stacked) {
        if (stacked) {
            return getById(id).map(Widget::getItemQuantity).orElse(0);
        }
        return Inventory.search().withId(id).result().size();
    }

    public static boolean hasItem(String name) {
        return getItemAmount(name, false) > 0;
    }

    public static boolean hasItem(String name, boolean stacked) {
        return getItemAmount(name, stacked) > 0;
    }

    public static boolean hasItem(String name, int amount) {
        return getItemAmount(name, false) >= amount;
    }

    public static boolean hasItem(String name, int amount, boolean stacked) {
        return getItemAmount(name, stacked) >= amount;
    }

    public static boolean hasItems(String... names) {
        for (String name : names) {
            if (!hasItem(name)) return false;
        }
        return true;
    }

    public static boolean hasAnyItems(String... names) {
        for (String name : names) {
            if (hasItem(name)) return true;
        }
        return false;
    }

    public static boolean hasAnyItems(Collection<Integer> itemIds) {
        for (int id : itemIds) {
            if (hasItem(id)) return true;
        }
        return false;
    }

    public static boolean hasItem(int id) {
        return getItemAmount(id) > 0;
    }

    public static List<Widget> getItems() {
        return Inventory.search().result();
    }

    public static int emptySlots() {
        return 28 - Inventory.search().result().size();
    }

    public static boolean runePouchContains(int id) {
        Set<Integer> runePouchIds = new HashSet<>();

        int[] varbitIds = {29, 1622, 1623, 14285};
        for (int varbit : varbitIds) {
            int value = EthanApiPlugin.getClient().getVarbitValue(varbit);
            if (value != 0) {
                runePouchIds.add(Runes.getRune(value).getItemId());
            }
        }

        return runePouchIds.contains(id);
    }

    public static boolean runePouchContains(Collection<Integer> ids) {
        for (int id : ids) {
            if (!runePouchContains(id)) return false;
        }
        return true;
    }

    public static int runePouchQuanitity(int id) {
        Map<Integer, Integer> runePouchSlots = new HashMap<>();

        int[][] varbitPairs = {
                {29, 1624},
                {1622, 1625},
                {1623, 1626},
                {14285, 14286}
        };

        for (int[] pair : varbitPairs) {
            int runeId = EthanApiPlugin.getClient().getVarbitValue(pair[0]);
            if (runeId != 0) {
                runePouchSlots.put(
                        Runes.getRune(runeId).getItemId(),
                        EthanApiPlugin.getClient().getVarbitValue(pair[1])
                );
            }
        }

        return runePouchSlots.getOrDefault(id, 0);
    }
}
