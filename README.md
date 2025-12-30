# StormScope HUD

A lightweight, client-side Fabric mod that displays a minimal weather and time HUD overlay in Minecraft Java Edition.

![Minecraft Version](https://img.shields.io/badge/minecraft-1.21.x-green)
![Mod Loader](https://img.shields.io/badge/mod%20loader-fabric-orange)

## Features
- **Weather Display:** CLEAR ☀, RAIN 🌧, SNOW ❄, THUNDER ⚡
- **Time Display:** Real-time HH:MM clock with DAY/NIGHT labels
- **Corner Positioning:** Place HUD in any corner (TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT)
- **Auto-hide:** Hides when you press F1 (vanilla HUD toggle)
- **Smart Detection:** Automatically detects snow in cold biomes
- **Crash Protection:** Comprehensive error handling to prevent game crashes

## What's New in v1.0.3

**Bug Fixes:**
- Fixed `NoSuchMethodError` crash when loading a world (text rendering API compatibility)
- Fixed snow detection using temperature-based checking for better cross-version support

**Compatibility:**
- Broadened Minecraft version support to all 1.21.x releases (1.21.0 - 1.21.4)
- Updated text rendering to use stable `drawText` API instead of version-sensitive methods
- Improved Fabric API version constraints for better dependency resolution

**Reliability:**
- Added null safety check for config object during HUD rendering
- Enhanced biome temperature detection for accurate snow/rain display

### Previous Updates

**v1.0.2:**
- Fixed initialization crash from corrupted config files

**v1.0.1:**
- Fixed crash on Windows 11 systems

**v1.0.0:**
- Initial release

## Installation

### Prerequisites
1. **Minecraft Java Edition 1.21.x** (not Bedrock Edition)
2. **Fabric Loader** - [Download here](https://fabricmc.net/use/installer/)
3. **Fabric API** - [Download here](https://modrinth.com/mod/fabric-api)

### Steps

#### Windows
1. Download the latest `stormscope-hud-X.X.X.jar` from the [Releases page](../../releases)
2. Press `Windows Key + R`, type `%APPDATA%\.minecraft\mods`, press Enter
3. Place the downloaded JAR file in the `mods` folder
4. Make sure `fabric-api` is also in the mods folder
5. Launch Minecraft with the **Fabric** profile

#### macOS/Linux
1. Download the latest `stormscope-hud-X.X.X.jar` from the [Releases page](../../releases)
2. Navigate to your Minecraft directory:
   - **macOS:** `~/Library/Application Support/minecraft/mods`
   - **Linux:** `~/.minecraft/mods`
3. Place the downloaded JAR file in the `mods` folder
4. Make sure `fabric-api` is also in the mods folder
5. Launch Minecraft with the **Fabric** profile

## Configuration

After first launch, a config file will be created at:
- **Windows:** `%APPDATA%\.minecraft\config\stormscope-hud.json`
- **macOS:** `~/Library/Application Support/minecraft/config/stormscope-hud.json`
- **Linux:** `~/.minecraft/config/stormscope-hud.json`

### Config Options

```json
{
  "corner": "TOP_RIGHT"
}
```

**Available corner positions:**
- `TOP_LEFT`
- `TOP_RIGHT` (default)
- `BOTTOM_LEFT`
- `BOTTOM_RIGHT`

Edit the file with any text editor and restart Minecraft to apply changes.

## Testing

You can test the weather display using these commands:
```
/weather clear
/weather rain
/weather thunder
```

Visit a snowy biome during rain to see the SNOW ❄ indicator.

## Building from Source

### Requirements
- Java 21 or higher

### Build Steps

1. Clone this repository:
   ```bash
   git clone https://github.com/dcosand/stormscope-hud.git
   cd stormscope-hud
   ```

2. Build the mod:
   ```bash
   ./gradlew build
   ```
   On Windows, use `gradlew.bat build`

3. Find the compiled JAR in `build/libs/stormscope-hud-X.X.X.jar`

## Compatibility
- **Minecraft:** Java Edition 1.21.x
- **Mod Loader:** Fabric Loader 0.15.0+
- **Dependencies:** Fabric API
- **Java:** 21+

## License
MIT License - See [LICENSE](LICENSE) file for details

## Support
If you encounter issues, please [open an issue](../../issues) on GitHub.
