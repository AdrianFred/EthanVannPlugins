package com.hunterPlugins.PiggyUtils.strategy;

import net.runelite.client.config.Config;
import net.runelite.client.plugins.Plugin;

public class ExampleTask extends AbstractTask {

    public ExampleTask(Plugin plugin, Config config) {
        super(plugin, config);
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        interactNpc("Goblin", "Attack", true);
    }
}
