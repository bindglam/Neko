package io.github.bindglam.neko;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Neko {
    private static NekoPlugin plugin;

    private Neko() {
    }

    public static @NotNull NekoPlugin plugin() {
        return Objects.requireNonNull(plugin, "Plugin not initialized");
    }

    @ApiStatus.Internal
    static void registerPlugin(NekoPlugin plugin) {
        if (Neko.plugin != null)
            throw new UnsupportedOperationException("Cannot redefine singleton plugin");
        Neko.plugin = plugin;
    }

    @ApiStatus.Internal
    static void unregisterPlugin() {
        Neko.plugin = null;
    }
}
