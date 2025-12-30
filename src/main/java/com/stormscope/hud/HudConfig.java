package com.stormscope.hud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class HudConfig {
    public enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public Corner corner = Corner.TOP_RIGHT;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir()
        .resolve("stormscope-hud.json");

    public static HudConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                HudConfig config = GSON.fromJson(reader, HudConfig.class);
                if (config != null && config.corner != null) {
                    return config;
                }
            } catch (Exception ignored) {
                // Fall through to defaults if config is corrupted or unreadable.
            }
        }

        HudConfig config = new HudConfig();
        save(config);
        return config;
    }

    public static void save(HudConfig config) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException ignored) {
            // Best-effort config persistence.
        }
    }
}
