# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Neko is a Minecraft Paper/Folia plugin framework for creating custom content through a feature-based system. It uses a content pack system where developers define custom items and behaviors using YAML configuration files and Java/Kotlin features.

## Build System

This project uses Gradle with Kotlin DSL. Key properties are defined in `gradle.properties`:
- `plugin_version`: Current plugin version (0.0.1)
- `minecraft_version`: Target Minecraft version (1.20.1)

### Common Commands

```bash
# Build the plugin
./gradlew build

# Build and shadow (creates fat JAR with dependencies)
./gradlew shadowJar

# Clean build artifacts
./gradlew clean

# Run a test Paper server with the plugin
./gradlew runServer

# Run a test Folia server with the plugin
./gradlew runFolia
```

The built JAR is located at `build/libs/Neko-{version}.jar`.

## Module Structure

The project is split into two main modules:

### `api` Module
Public API for content creators and plugin developers. Contains interfaces and abstractions:
- `io.github.bindglam.neko.Neko` - Static singleton accessor to the plugin instance
- `io.github.bindglam.neko.NekoPlatform` - Main plugin interface providing access to managers
- `io.github.bindglam.neko.content` - Content system interfaces (Content, Feature, ContentsPack)
- `io.github.bindglam.neko.content.item` - Item-related interfaces (Item, ItemProperties, ImmutableItemStack)
- `io.github.bindglam.neko.content.feature` - Feature system (Feature, FeatureBuilder, FeatureFactory, FeatureEventBus)
- `io.github.bindglam.neko.manager` - Manager interfaces (RegistryManager, ContentManager, ResourcePackManager)
- `io.github.bindglam.neko.registry` - Registry system interfaces

### `core` Module
Implementation of the API and core functionality:
- `io.github.bindglam.neko.NekoPluginImpl` - Main plugin implementation
- `io.github.bindglam.neko.NekoPluginLoader` - Paper plugin loader for dependency resolution
- `io.github.bindglam.neko.manager.*Impl` - Implementations of manager interfaces
- `io.github.bindglam.neko.content.*Impl` - Implementations of content interfaces
- `io.github.bindglam.neko.content.feature.builtin` - Built-in features (e.g., HelloWorldFeature)
- `io.github.bindglam.neko.registry.*` - Registry implementations (MappedRegistry, DirectScalableRegistry, EntryScalableRegistry)

## Architecture

### Content Pack System
Content packs are loaded from the `run/plugins/Neko/packs/` directory. Each pack requires:
- `pack.yml` - Pack metadata (id, version, author)
- `configs/` - YAML files defining content items

Example pack structure:
```
testpack/
├── pack.yml
└── configs/
    └── items.yml
```

### Manager Lifecycle
All managers implement the `Managerial` interface with three lifecycle phases:
1. `preload(Context)` - Initialize and unlock resources
2. `start(Context)` - Begin operations after server load
3. `end(Context)` - Cleanup on disable

Managers can also implement `Reloadable` to support hot-reloading via `NekoPlugin.reload()`.

### Registry System
The plugin uses a hierarchical registry system:
- `GlobalRegistries` - Global plugin-level registries (types, features, contentsPacks)
- `Registries` (per-pack) - Content registries for individual packs (items)

Registries support locking/unlocking and can be cleared during reloads. The `RegistryInitializeEvent` is fired when global registries are ready for registration.

### Feature System
Features are modular behaviors attached to content items:
- `FeatureFactory` - Creates feature instances with `FeatureArguments`
- `FeatureBuilder` - Combines a factory with arguments
- `FeatureEventBus` - Event-driven communication within content (ItemStackGenerationEvent, ResourcePackGenerationEvent)
- `Feature` is abstract and receives a reference to its parent `Content`

Features can subscribe to events via the content's `FeatureEventBus` to modify behavior during various phases (item stack generation, resource pack generation).

### Content Type System
New content types are registered in `GlobalRegistries.types()`:
- `ItemType` - Built-in item content type
- Implement `ContentType<T extends Content>` to add new types
- Types are responsible for parsing YAML config and registering to registries

## Plugin Loading

Neko uses Paper's plugin loader system (`NekoPluginLoader`) to resolve dependencies at runtime:
- Cloud (command framework) - `org.incendo:cloud-paper:2.0.0-beta.14`
- Creative (resource pack API) - `team.unnamed:creative-*:1.7.3`
- ConfigLib (via compileOnly) - `com.github.bindglam:ConfigLib:1.0.0`

The plugin supports both Paper and Folia (foliaSupported: true in plugin.yml).

## Resource Pack Generation

The `ResourcePackManager.generateResourcePack()` method asynchronously generates the resource pack using the Creative library. This process is triggered after content is loaded.

## Java Version

The project uses Java 21 (configured in buildSrc/src/main/kotlin/paper-conventions.gradle.kts).

## Development

- Test content packs are located in `run/plugins/Neko/packs/`
- The test server runs on default Minecraft server port
- Use `./gradlew runServer` to start the test server with hot-reload support
- Built-in features demonstrate the feature system in `core/src/main/java/io/github/bindglam/neko/content/feature/builtin/`

## License

MIT License - Copyright (c) 2026 Woobeen Jeon
