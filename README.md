# <img width="64" height="32" alt="icon" src="https://github.com/user-attachments/assets/289894f5-2e35-4567-946e-8486a8cdd133" /> Neko

---

[![CodeFactor](https://www.codefactor.io/repository/github/bindglam/neko/badge)](https://www.codefactor.io/repository/github/bindglam/neko)
[![](https://img.shields.io/badge/Github%20Wiki-181717?logo=github&logoColor=white)](https://github.com/bindglam/Neko/wiki)
[![](https://deepwiki.com/badge.svg)](https://deepwiki.com/bindglam/Neko)

A powerful and easy-to-use Paper plugin that allows you to create fully custom items and blocks on your Minecraft server without the need for any client-side mods.

---

## ⭐ Features
- **Custom Items**: Create items with custom names, lores, enchantments, textures, and behaviors.
- **Custom Blocks**: Design your own blocks with unique textures, break sounds, and drops.
- **Resource Pack Generation**: Automatically generate the required resource pack for your players.
- **Lightweight & Optimized**: Built to be efficient and not bog down your server's performance.
- **Developer API**: Provides a simple API for other plugins to interact with your custom content.

## ⛓️ Requirements
- **Server Software**: Paper (or a fork of Paper)

## 🧑‍💻 For Developers (API)
build.gradle.kts
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.bindglam.Neko:api:<VERSION>")
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

For general questions and support, join our [Discord Server](https://discord.gg/CZEabRCuK8).

## 📬 Contributing
We welcome contributions! If you'd like to contribute, please fork the repository and submit a pull request.

## 🧪 Inspired by
- [BetterModel](https://github.com/toxicity188/BetterModel) by toxicity188

## 📃 License
This project is licensed under the [MIT License](https://github.com/bindglam/Neko/blob/main/LICENSE).
