package io.github.bindglam.neko;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Neko {
    private static NekoPlatform platform;

    private Neko() {
    }

    public static @NotNull NekoPlatform platform() {
        return Objects.requireNonNull(platform, "Platform not initialized");
    }

    @ApiStatus.Internal
    static void registerPlugin(NekoPlatform plugin) {
        if (Neko.platform != null)
            throw new UnsupportedOperationException("Cannot redefine singleton plugin");
        Neko.platform = plugin;
    }

    @ApiStatus.Internal
    static void unregisterPlugin() {
        Neko.platform = null;
    }
}
