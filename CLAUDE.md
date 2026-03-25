# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Neko is a Minecraft Paper plugin that provides a content management system with a registry-based architecture for custom items, features, and content packs. The plugin uses a multi-module Gradle build with Java 21 and Kotlin.

## Common Commands

```bash
# Build the project (creates shadow JAR at build/libs/Neko-{version}.jar)
./gradlew build

# Run a test Paper server with the plugin loaded
./gradlew runServer

# Clean and rebuild
./gradlew clean build
```

## Module Structure

- **api** (`api/src/main/java/`): Public API interfaces (Java). Defines contracts for Content, Feature, Registry, Managers, etc.
- **core** (`core/src/main/kotlin/`): Implementation code (Kotlin). Provides implementations for all API interfaces.
- **Root**: Main plugin implementation (`NekoPluginImpl.kt`, `NekoPluginLoader.kt`) that ties everything together.

## Architecture

### Registry System

The core of Neko is a multi-tiered registry system:

1. **Registry<T>**: Base read-only interface with `get(Key)` and `entries()`
2. **WritableRegistry<T>**: Extends Registry with `lock()`, `unlock()`, and `merge()`
3. **DirectWritableRegistry<T>**: For registering values directly via `register(Key, T)`
4. **EntryWritableRegistry<T, E>**: For registering via builder pattern using `register(Key, Consumer<E>)`

Registry implementations (`ScalableRegistry`, `DirectScalableRegistry`, `EntryScalableRegistry`, `MappedRegistry`) extend `ScalableRegistry` and support locking to prevent modifications after initialization.

### Content System

Content is defined through:

1. **ContentType<T>**: Defines how to load content from YAML config. `ItemType` is the built-in implementation.
2. **Content**: Base interface for all content (items, blocks, etc.)
3. **ContentRegistryEntry<E, T>**: Builder interface for creating content entries
4. **Feature**: Behavior components attached to content, initialized via `FeatureContext.Init`

Content is loaded from packs located at `plugins/Neko/packs/{packName}/`:
- `pack.yml`: Contains pack metadata (`id`, `version`, `author`)
- `configs/*.yml`: YAML files defining content with structure:
  ```yaml
  namespace:key:
    type: content_type_id  # e.g., "item"
    properties:
      # type-specific properties
    features:
      0:
        id: feature_namespace:feature_id
  ```

### Manager Lifecycle

Managers implement the `Managerial` interface with three lifecycle phases:

- **preload(context: Context)**: Called during plugin `onEnable()`, before server loads
- **start(context: Context)**: Called via `ServerLoadEvent`, after server fully loads
- **end(context: Context)**: Called during plugin `onDisable()`

Built-in managers:
- `RegistryManagerImpl`: Initializes GlobalRegistries, registers ContentTypes and Features
- `ContentManagerImpl`: Loads content packs from `plugins/Neko/packs/`
- `CommandManager`: Sets up Cloud-based commands (currently `/neko give`)
- `ResourcePackManagerImpl`: Generates resource packs

### Registry Hierarchy

```
GlobalRegistries (singleton, locked after preload)
├── types()          # Registry<ContentType<?>>
├── features()       # DirectWritableRegistry<Feature>
└── contentsPacks()  # EntryWritableRegistry<ContentsPack, ContentsPackRegistryEntry>

Per-pack Registries (merged into GlobalRegistries after pack load)
└── item()           # EntryWritableRegistry<Item, ItemRegistryEntry>
```

### Event System

`RegistryInitializeEvent` is fired during `RegistryManagerImpl.preload()` before registries are locked. Listen to this event to register custom Features.

### Item Implementation

Items are created via the builder pattern:

```kotlin
registries.item().register(key) { entry ->
    entry.key(key)
        .properties(properties)
        .features(features)
}
```

Items store their Key in PersistentDataContainer (`neko:item`) for identification. The `ImmutableItemStack` wrapper ensures safe cloning.

## Adding New Content Types

1. Create a new class implementing `ContentType<T>`
2. Define `id()`, `clazz()`, and implement `load()` to parse config
3. Register in `RegistryManagerImpl.GlobalRegistriesImpl.types()`
4. Update `Registries` interface and `RegistriesImpl` to include the new registry
5. Implement the corresponding Content interface and Entry

## Adding New Features

1. Implement `Feature` interface with `key()` and `init(FeatureContext.Init)`
2. Register in `RegistryManagerImpl.preload()` (or listen to `RegistryInitializeEvent`)
3. Reference in content config via the feature's Key

## Plugin Loader

`NekoPluginLoader` handles runtime dependency resolution using Paper's plugin loader system, pulling in:
- Cloud (command framework)
- Team Creative (resource pack handling)

## Constants

Key constants are defined in `core/src/main/kotlin/io/github/bindglam/neko/utils/Constants.kt`:
- `PLUGIN_ID = "neko"`
- `PLUGIN_NAME = "Neko"`
- `DATA_FOLDER = File("plugins", PLUGIN_NAME)`
