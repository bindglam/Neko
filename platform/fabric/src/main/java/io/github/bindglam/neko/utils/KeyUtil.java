package io.github.bindglam.neko.utils;

import net.kyori.adventure.key.Key;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public final class KeyUtil {
    private KeyUtil() {
    }

    public static @NotNull Identifier toNMS(@NotNull Key key) {
        return Identifier.fromNamespaceAndPath(key.namespace(), key.value());
    }
}
