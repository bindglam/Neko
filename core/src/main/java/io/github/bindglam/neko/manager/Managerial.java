package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.NekoPlatform;
import org.jetbrains.annotations.NotNull;

public interface Managerial<PLATFORM extends NekoPlatform> {
    void preload(@NotNull Context<PLATFORM> context);

    void start(@NotNull Context<PLATFORM> context);

    void end(@NotNull Context<PLATFORM> context);
}
