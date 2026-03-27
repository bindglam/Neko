package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.NekoPluginImpl;
import org.jetbrains.annotations.NotNull;

public final class Context {
    private final NekoPluginImpl plugin;

    public Context(@NotNull NekoPluginImpl plugin) {
        this.plugin = plugin;
    }

    public NekoPluginImpl plugin() {
        return plugin;
    }
}
