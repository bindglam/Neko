package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.NekoPluginImpl;
import org.jetbrains.annotations.NotNull;

public record Context(
        @NotNull NekoPluginImpl plugin
) {
}
