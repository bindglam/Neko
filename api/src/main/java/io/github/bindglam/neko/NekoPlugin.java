package io.github.bindglam.neko;

import io.github.bindglam.neko.manager.ContentManager;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.manager.ResourcePackManager;
import org.jetbrains.annotations.NotNull;

public interface NekoPlugin {
    @NotNull RegistryManager registryManager();

    @NotNull ContentManager contentManager();

    @NotNull ResourcePackManager resourcePackManager();
}
