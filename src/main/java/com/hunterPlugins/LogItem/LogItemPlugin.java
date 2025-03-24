package com.hunterPlugins.LogItem;

import com.example.EthanApiPlugin.Collections.Inventory;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "LogItem by hunter",
        description = "Logs the item to the chat",
        tags = {"ethan", "hunter"}
)

public class LogItemPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private LogItemConfig config;

    @Provides
    LogItemConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(LogItemConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        log.info("LogItem Plugin Started");
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("LogItem Plugin Stopped");
    }

    @Subscribe
    public void onGameTick(GameTick tick) {
        if (config.logItem()) {
            logItem();
        }
    }

    private void logItem() {
      if (GameState.LOGGED_IN.equals(client.getGameState())) {
          Inventory.search().withName(config.logItemName()).first().ifPresent(item -> {
              client.addChatMessage(net.runelite.api.ChatMessageType.GAMEMESSAGE, "", "Found " + item.getName(), null);
          });
      }
    }
}
