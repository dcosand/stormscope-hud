# Migration Notes for Future Minecraft Versions

## Mojang Mappings Migration (Post 1.21.11)

Starting with Minecraft snapshots after 1.21.11, Minecraft will no longer be obfuscated. This requires migration from Yarn/Intermediary mappings to Mojang Mappings.

### What to do:
1. Update `build.gradle` to use Mojang mappings instead of Yarn:
   ```gradle
   mappings loom.officialMojangMappings()
   ```

2. Recompile the mod - all mods interacting with Minecraft code will need recompilation

3. Update any mixin targets to use official Mojang names instead of intermediary names

### References:
- Fabric announcement: https://fabricmc.net/2025/12/05/12111.html
- Yarn and Intermediary will stop updating after 1.21.11

## Current Compatibility

This mod is currently compatible with:
- Minecraft 1.21.1
- Fabric Loader >= 0.15.0
- Java 21+

## Resilience Features

The mod includes several defensive programming practices:
- Comprehensive null checks for MinecraftClient components
- Try-catch blocks around rendering to prevent crashes
- Error logging via SLF4J for debugging
- Defensive biome checking with fallback behavior
