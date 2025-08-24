package com.bindglam.neko.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class NekoProvider {
    private static Neko neko;

    private NekoProvider() {
    }

    public static @NotNull Neko neko() {
        if(neko == null)
            throw new IllegalStateException("Not initialized");

        return neko;
    }

    @ApiStatus.Internal
    public static void register(@NotNull Neko neko) {
        NekoProvider.neko = neko;
    }
}
