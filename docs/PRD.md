# StormScope HUD PRD

## Overview
StormScope HUD is a lightweight, always-on, client-side Fabric mod for Minecraft Java Edition. It renders a minimal HUD overlay that shows the current weather state and in-game time, updating in real time from the client world.

## Goals
- Display only two lines of HUD: weather and time.
- Keep the UI minimal, readable, and always-on.
- Default position: upper-right corner.
- Support four corner positions via simple config.
- Work on Minecraft 1.21.x+ with Fabric + Fabric API.

## Non-Goals
- No GUI config screen in the first release.
- No server-side components.
- No additional HUD elements beyond weather and time.

## User Experience
- HUD is visible during normal gameplay.
- HUD hides when the user hides the vanilla HUD (F1).
- Weather is one of: CLEAR, RAIN, THUNDER (THUNDER emphasized with ⚡).
- Time is shown as HH:MM with DAY/NIGHT label.

## Functional Requirements
- Read state from `MinecraftClient.getInstance().world` each render.
- Weather:
  - `world.isRaining()`
  - `world.isThundering()`
- Time:
  - `world.getTimeOfDay()` (ticks 0-23999)
  - Convert ticks to 24h clock: HH:MM
  - Label DAY for ticks < 12000, NIGHT otherwise
- Render overlay using `HudRenderCallback` (Fabric API).
- Do not render if `client.options.hudHidden` is true.
- Config file stored in `config/stormscope-hud.json` with a `corner` value.

## Configuration
- Config key: `corner`
- Allowed values: `TOP_LEFT`, `TOP_RIGHT`, `BOTTOM_LEFT`, `BOTTOM_RIGHT`
- Default: `TOP_RIGHT`

## Compatibility
- Minecraft Java Edition 1.21.x+
- Fabric Loader + Fabric API
- Java 21 required for build/runtime

## Quality Bar
- No crashes on world load, server join, or dimension change.
- Correctly updates when switching worlds/servers/dimensions.
- HUD never renders while F1 is active.
- Build produces a usable mod JAR.

## Release Checklist
- `./gradlew build` passes.
- HUD visible in-game with Fabric API installed.
- Config file generated on first launch.
- README updated with install steps and config location.
