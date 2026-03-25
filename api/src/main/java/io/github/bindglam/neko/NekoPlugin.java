package io.github.bindglam.neko;

import io.github.bindglam.neko.manager.ContentManager;
import io.github.bindglam.neko.manager.RegistryManager;
import org.jetbrains.annotations.NotNull;

public interface NekoPlugin {
    @NotNull RegistryManager registryManager();

    @NotNull ContentManager contentManager();
}
