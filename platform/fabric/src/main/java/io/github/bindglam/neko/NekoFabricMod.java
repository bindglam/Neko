package io.github.bindglam.neko;

import io.github.bindglam.neko.event.EventBus;
import io.github.bindglam.neko.event.EventBusImpl;
import io.github.bindglam.neko.manager.*;
import io.github.bindglam.neko.platform.FabricAdapter;
import io.github.bindglam.neko.platform.PlatformAdapter;
import io.github.bindglam.neko.utils.Constants;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public final class NekoFabricMod implements ModInitializer, NekoPlatform {
    public static final Logger LOGGER = Logger.getLogger(Constants.MOD_ID);

    @Getter
    @Accessors(fluent = true)
    private final FabricAdapter platformAdapter = new FabricAdapter();
    @Getter @Accessors(fluent = true)
    private final EventBusImpl eventBus = new EventBusImpl();

    @Getter @Accessors(fluent = true)
    private final RegistryManagerImpl registryManager = new RegistryManagerImpl();
    @Getter @Accessors(fluent = true)
    private final ContentManagerImpl contentManager = new ContentManagerImpl();
    @Getter @Accessors(fluent = true)
    private final ResourcePackManagerImpl resourcePackManager = new ResourcePackManagerImpl();

    private final CommandManager commandManager = new CommandManager();

    private final List<Managerial> managers = List.of(
            registryManager,
            contentManager,
            resourcePackManager,
            commandManager
    );

    @Override
    public void onInitialize() {
        Neko.registerPlugin(this);
        LOGGER.info("Neko is running!");
    }

    @Override
    public @NotNull Logger logger() {
        return LOGGER;
    }

    @Override
    public void reload() {
    }
}
