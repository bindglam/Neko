package com.bindglam.neko.api.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public interface LifecycleContext {
    JavaPlugin plugin();

    FileConfiguration config();
}
