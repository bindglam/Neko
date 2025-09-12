# 😺 Neko

---

[![CodeFactor](https://www.codefactor.io/repository/github/bindglam/neko/badge)](https://www.codefactor.io/repository/github/bindglam/neko)

A powerful and easy-to-use Paper plugin that allows you to create fully custom items and blocks on your Minecraft server without the need for any client-side mods.

---

## ⭐ Features
- **Custom Items**: Create items with custom names, lores, enchantments, textures, and behaviors.
- **Custom Blocks**: Design your own blocks with unique textures, break sounds, and drops.
- **Resource Pack Generation**: Automatically generate the required resource pack for your players.
- **Lightweight & Optimized**: Built to be efficient and not bog down your server's performance.
- **Developer API**: Provides a simple API for other plugins to interact with your custom content.

## ⛓️ Requirements
- **Server Version**: Paper (or a fork of Paper) for Minecraft 1.21.8 or higher.

## 📥 Installation
1. Download the latest version of the plugin from the [Releases](https://https://github.com/bindglam/Neko/releases) page.
2. Place the downloaded `.jar` file into your server's `plugins` folder.
3. Install any required dependencies.
4. Restart your server. The plugin will generate its default configuration files.

## 🧑‍💻 For Developers (API)
build.gradle.kts
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.bindglam.Neko:api:0.0.1")
}
```

Item:
```java
BuiltInRegistries.ITEMS.get(Key.key("defaultassets:ruby"));
```

Block:
```java
BuiltInRegistries.BLOCKS.get(Key.key("defaultassets:ruby_block"));
```

Glyph:
```java
BuiltInRegistries.GLYPHS.get(Key.key("defaultassets:ruby_glyph"));
```

## 🛠️ Support and Bug Reports
If you encounter any issues or have a suggestion, please open an issue on our [GitHub Issues](https://github.com/bindglam/Neko/issues) page.

For general questions and support, join our [Discord Server](https://discord.gg/your-invite-code).

## 📬 Contributing
We welcome contributions! If you'd like to contribute, please fork the repository and submit a pull request.

## 📃 License
This project is licensed under the [MIT License](https://github.com/bindglam/Neko/blob/main/LICENSE).