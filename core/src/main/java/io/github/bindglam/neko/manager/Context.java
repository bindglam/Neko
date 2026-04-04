package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.NekoPlatform;
import org.jetbrains.annotations.NotNull;

public record Context<PLATFORM extends NekoPlatform>(
        @NotNull PLATFORM plugin
) {
}
