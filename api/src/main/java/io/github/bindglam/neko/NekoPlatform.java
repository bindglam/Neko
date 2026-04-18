package io.github.bindglam.neko;

import io.github.bindglam.neko.event.EventBus;
import io.github.bindglam.neko.manager.ContentManager;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.manager.ResourcePackManager;
import io.github.bindglam.neko.platform.PlatformAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface NekoPlatform {
    @NotNull Logger logger();

    @NotNull PlatformAdapter platformAdapter();

    @NotNull EventBus eventBus();

    @NotNull RegistryManager registryManager();

    @NotNull ContentManager contentManager();

    @NotNull ResourcePackManager resourcePackManager();

    void reload();
}
