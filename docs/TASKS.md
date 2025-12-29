# StormScope HUD Tasks

## Milestone 1: Project Setup
- [x] Initialize Fabric mod structure.
- [x] Configure Gradle + Loom for 1.21.x.
- [x] Add Fabric API dependency.
- [x] Add mod metadata (`fabric.mod.json`).
- [x] Add Gradle wrapper and verify build.

## Milestone 2: HUD Implementation
- [x] Create client entrypoint.
- [x] Register `HudRenderCallback` render hook.
- [x] Read client world weather state (CLEAR/RAIN/THUNDER).
- [x] Convert world ticks to HH:MM and label DAY/NIGHT.
- [x] Hide when F1 HUD is hidden.

## Milestone 3: Positioning Config
- [x] Add JSON config file in `config/stormscope-hud.json`.
- [x] Support four corners with default TOP_RIGHT.
- [x] Render text aligned to selected corner.

## Milestone 4: Docs & Packaging
- [x] Add README with install steps and config info.
- [x] Add PRD and tasks doc in `docs/`.
- [ ] Provide example screenshots (optional).
- [ ] Add release notes and versioning plan.

## Milestone 5: Optional Enhancements
- [ ] Add command to cycle corners in-game.
- [ ] Add translucent background panel option.
- [ ] Add configurable text scale.

## Verification
- [ ] Launch game with Fabric loader + Fabric API.
- [ ] Confirm HUD renders in all four corners.
- [ ] Confirm THUNDER shows ⚡ when storming.
- [ ] Confirm time updates smoothly in real time.
- [ ] Confirm HUD hides with F1.
