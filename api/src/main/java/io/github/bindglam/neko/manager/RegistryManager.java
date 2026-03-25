package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.content.ContentType;
import io.github.bindglam.neko.content.ContentsPackRegistryEntry;
import io.github.bindglam.neko.content.ContentsPack;
import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.registry.DirectWritableRegistry;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.registry.Registry;
import io.github.bindglam.neko.registry.EntryWritableRegistry;
import org.jetbrains.annotations.NotNull;

public interface RegistryManager {
    @NotNull GlobalRegistries registries();

    interface GlobalRegistries extends Registries {
        @NotNull Registry<ContentType<?>> types();

        @NotNull DirectWritableRegistry<Feature> features();

        @NotNull EntryWritableRegistry<ContentsPack, ContentsPackRegistryEntry> contentsPacks();

        void mergeAll(@NotNull Registries registries);


        static @NotNull GlobalRegistries registries() {
            return Neko.plugin().registryManager().registries();
        }
    }
}
