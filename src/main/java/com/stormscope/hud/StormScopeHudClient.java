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

        context.drawTextWithShadow(textRenderer, Text.literal(line), x, y, 0xFFFFFF);
    }

    private String getWeatherLine(ClientWorld world) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (world.isThundering()) {
            return "Weather: THUNDER ⚡";
        }

        if (world.isRaining()) {
            // Check if it's snowing at player position
            if (client.player != null) {
                try {
                    var pos = client.player.getBlockPos();
                    var biome = world.getBiome(pos);
                    // Check if precipitation at this position is snow
                    if (biome != null && biome.value() != null && !biome.value().doesNotSnow(pos)) {
                        return "Weather: SNOW ❄";
                    }
                } catch (Exception e) {
                    LOGGER.debug("Error checking biome for snow", e);
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
