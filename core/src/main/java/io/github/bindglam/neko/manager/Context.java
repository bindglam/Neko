package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.NekoPlatform;
import io.github.bindglam.neko.event.EventBus;
import org.jetbrains.annotations.NotNull;

public record Context(
        @NotNull NekoPlatform platform,
        @NotNull EventBus eventBus
) {
}
