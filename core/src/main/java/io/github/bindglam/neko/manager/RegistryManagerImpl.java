package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.content.ContentType;
import io.github.bindglam.neko.content.ContentsPack;
import io.github.bindglam.neko.content.item.ItemType;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.event.RegistryInitializeEvent;
import io.github.bindglam.neko.registry.DirectScalableRegistry;
import io.github.bindglam.neko.registry.DirectWritableRegistry;
import io.github.bindglam.neko.registry.MappedRegistry;
import io.github.bindglam.neko.registry.Registry;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.registry.RegistriesImpl;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Logger;

public final class RegistryManagerImpl implements RegistryManager, Managerial, Reloadable {
    private static final Logger LOGGER = Logger.getLogger(RegistryManagerImpl.class.getName());

    private final GlobalRegistries globalRegistries = new GlobalRegistriesImpl();

    @Override
    public void preload(@NotNull Context context) {
        LOGGER.info("Initializing registries...");

        globalRegistries.unlockAll();
        globalRegistries.clearAll();
    }

    @Override
    public void start(@NotNull Context context) {
        Bukkit.getPluginManager().callEvent(new RegistryInitializeEvent(globalRegistries));

        globalRegistries.lockAll();

        LOGGER.info("Successfully initialized registries!");
    }

    @Override
    public void end(@NotNull Context context) {
    }

    @Override
    public @NotNull GlobalRegistries registries() {
        return globalRegistries;
    }

    public static final class GlobalRegistriesImpl extends RegistriesImpl implements GlobalRegistries {
        @Getter @Accessors(fluent = true)
        private final Registry<ContentType<?>> types;
        @Getter @Accessors(fluent = true)
        private final DirectWritableRegistry<FeatureFactory<?>> features;
        @Getter @Accessors(fluent = true)
        private final DirectWritableRegistry<ContentsPack> contentsPacks;

        private GlobalRegistriesImpl() {
            Map<Key, ContentType<?>> typeMap = Map.of(ItemType.KEY, new ItemType());
            this.types = create(new MappedRegistry<>(typeMap));
            this.features = create(new DirectScalableRegistry<>());
            this.contentsPacks = create(new DirectScalableRegistry<>());
        }

        @Override
        public void mergeAll(@NotNull Registries registries) {
            item().merge(registries.item());
        }
    }
}
