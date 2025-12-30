package com.stormscope.hud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StormScopeHudClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("stormscope-hud");
    private HudConfig config;

    @Override
    public void onInitializeClient() {
        config = HudConfig.load();

        HudRenderCallback.EVENT.register(this::renderHud);
    }

    private void renderHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        try {
            if (config == null) {
                return;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null || client.options == null || client.options.hudHidden) {
                return;
            }

            ClientWorld world = client.world;
            if (world == null) {
                return;
            }

            if (client.textRenderer == null || client.getWindow() == null) {
                return;
            }

            String weatherLine = getWeatherLine(world);
            String timeLine = getTimeLine(world);

            TextRenderer textRenderer = client.textRenderer;
            int lineHeight = textRenderer.fontHeight + 2;
            int padding = 6;
            int lines = 2;

            Window window = client.getWindow();
            int screenWidth = window.getScaledWidth();
            int screenHeight = window.getScaledHeight();

            int startY = switch (config.corner) {
                case TOP_LEFT, TOP_RIGHT -> padding;
                case BOTTOM_LEFT, BOTTOM_RIGHT -> screenHeight - (lines * lineHeight) - padding;
            };

            drawLine(context, textRenderer, weatherLine, 0, startY, screenWidth, padding);
            drawLine(context, textRenderer, timeLine, 1, startY, screenWidth, padding);
        } catch (Exception e) {
            LOGGER.error("Error rendering StormScope HUD", e);
        }
    }

    private void drawLine(DrawContext context, TextRenderer textRenderer, String line, int index,
                          int startY, int screenWidth, int padding) {
        int lineHeight = textRenderer.fontHeight + 2;
        int y = startY + (index * lineHeight);
        int lineWidth = textRenderer.getWidth(line);

        int x = switch (config.corner) {
            case TOP_LEFT, BOTTOM_LEFT -> padding;
            case TOP_RIGHT, BOTTOM_RIGHT -> screenWidth - padding - lineWidth;
        };

        // Try multiple rendering methods for cross-version compatibility
        // Different Minecraft 1.21.x versions have different DrawContext APIs
        drawTextSafely(context, textRenderer, line, x, y, 0xFFFFFF);
    }

    /**
     * Attempts multiple text rendering methods for maximum version compatibility.
     * Minecraft 1.21.x has inconsistent DrawContext APIs across minor versions.
     */
    private void drawTextSafely(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int color) {
        try {
            // Method 1: drawText with Text and shadow boolean (some 1.21.x versions)
            context.drawText(textRenderer, Text.literal(text), x, y, color, true);
        } catch (NoSuchMethodError e1) {
            try {
                // Method 2: drawTextWithShadow with Text (other 1.21.x versions)
                context.drawTextWithShadow(textRenderer, Text.literal(text), x, y, color);
            } catch (NoSuchMethodError e2) {
                try {
                    // Method 3: drawText with String and shadow boolean (fallback)
                    context.drawText(textRenderer, text, x, y, color, true);
                } catch (NoSuchMethodError e3) {
                    try {
                        // Method 4: drawTextWithShadow with String (another fallback)
                        context.drawTextWithShadow(textRenderer, text, x, y, color);
                    } catch (NoSuchMethodError e4) {
                        // If all methods fail, log the error
                        LOGGER.error("Could not find compatible text rendering method for this Minecraft version. " +
                                    "Please report this issue with your Minecraft version.", e4);
                    }
                }
            }
        }
    }

    private String getWeatherLine(ClientWorld world) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (world.isThundering()) {
            return "Weather: THUNDER ⚡";
        }

        if (world.isRaining()) {
            // Check if it's snowing at player position using temperature
            // Biomes with temperature < 0.15 have snow instead of rain
            if (client.player != null) {
                try {
                    var pos = client.player.getBlockPos();
                    var biome = world.getBiome(pos);
                    if (biome != null && biome.value() != null) {
                        float temp = biome.value().getTemperature();
                        if (temp < 0.15f) {
                            return "Weather: SNOW ❄";
                        }
                    }
                } catch (Exception e) {
                    LOGGER.debug("Error checking biome temperature for snow", e);
                }
            }
            return "Weather: RAIN \uD83C\uDF27";
        }

        return "Weather: CLEAR ☀";
    }

    private String getTimeLine(ClientWorld world) {
        long timeOfDay = world.getTimeOfDay() % 24000L;
        int hours = (int) ((timeOfDay / 1000L + 6) % 24);
        int minutes = (int) ((timeOfDay % 1000L) * 60 / 1000);
        String label = timeOfDay < 12000L ? "DAY" : "NIGHT";
        return String.format("Time: %02d:%02d %s", hours, minutes, label);
    }
}
