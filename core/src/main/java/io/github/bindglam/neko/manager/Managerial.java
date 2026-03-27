package io.github.bindglam.neko.manager;

import org.jetbrains.annotations.NotNull;

public interface Managerial {
    void preload(@NotNull Context context);

    void start(@NotNull Context context);

    void end(@NotNull Context context);
}
