package com.hunterPlugins.PiggyUtils.BreakHandler;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

@Singleton
public class ReflectBreakHandler {
    private static final Logger log = LoggerFactory.getLogger(ReflectBreakHandler.class);

    @Inject
    private PluginManager pluginManager;

    private Object instance = null;
    private HashMap<String, Method> chinMethods = null;
    private boolean chinBreakHandlerInstalled = true;

    public void registerPlugin(Plugin p, boolean configure) {
        performReflection("registerPlugin2", p, configure);
    }

    public void registerPlugin(Plugin p) {
        performReflection("registerPlugin1", p);
    }

    public void unregisterPlugin(Plugin p) {
        performReflection("unregisterPlugin1", p);
    }

    public void startPlugin(Plugin p) {
        performReflection("startPlugin1", p);
    }

    public void stopPlugin(Plugin p) {
        performReflection("stopPlugin1", p);
    }

    public boolean isBreakActive(Plugin p) {
        return Optional.ofNullable(performReflection("isBreakActive1", p))
                .map(val -> (Boolean) val)
                .orElse(false);
    }

    public boolean shouldBreak(Plugin p) {
        return Optional.ofNullable(performReflection("shouldBreak1", p))
                .map(val -> (Boolean) val)
                .orElse(false);
    }

    public boolean needsBankPin(Client c) {
        return Optional.ofNullable(performReflection("needsBankPin1", c))
                .map(val -> (Boolean) val)
                .orElse(false);
    }

    public String getBankPin(ConfigManager configManager) {
        return (String) performReflection("getBankPin1", configManager);
    }

    public void startBreak(Plugin p) {
        performReflection("startBreak1", p);
    }

    private Object performReflection(String methodName, Object... args) {
        if (checkReflection()) {
            Method method = chinMethods.get(methodName.toLowerCase());
            if (method != null) {
                try {
                    return method.invoke(instance, args);
                } catch (Exception e) {
                    log.error("Reflection error calling method: {}", methodName, e);
                }
            }
        }
        return null;
    }

    private boolean checkReflection() {
        if (!chinBreakHandlerInstalled) {
            return false;
        }

        if (chinMethods != null && instance != null) {
            return true;
        }

        chinMethods = new HashMap<>();

        for (Plugin plugin : pluginManager.getPlugins()) {
            if (!plugin.getClass().getSimpleName().equalsIgnoreCase("ChinBreakHandlerPlugin")) {
                continue;
            }

            for (Field field : plugin.getClass().getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("chinBreakHandler")) {
                    continue;
                }

                field.setAccessible(true);
                try {
                    instance = field.get(plugin);
                    for (Method method : instance.getClass().getDeclaredMethods()) {
                        method.setAccessible(true);
                        chinMethods.put(method.getName().toLowerCase(), method);
                    }
                    return true;
                } catch (IllegalAccessException e) {
                    log.error("Unable to access chinBreakHandler field", e);
                    return false;
                }
            }
        }

        chinBreakHandlerInstalled = false;
        return false;
    }
}
