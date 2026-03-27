package io.github.bindglam.neko.utils;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.NekoPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class Plugins {
    public static JavaPlugin plugin(NekoPlugin nekoPlugin) {
        return (JavaPlugin) nekoPlugin;
    }

    public static Logger logger() {
        return plugin(Neko.plugin()).getLogger();
    }

    private Plugins() {
    }
}
