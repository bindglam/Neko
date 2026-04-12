package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.content.ContentType;
import io.github.bindglam.neko.content.ContentsPack;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.registry.DirectWritableRegistry;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.registry.Registry;
import org.jetbrains.annotations.NotNull;

public interface RegistryManager {
    @NotNull GlobalRegistries registries();

    interface GlobalRegistries extends Registries {
        @NotNull Registry<ContentType<?>> types();

        @NotNull DirectWritableRegistry<FeatureFactory<?>> features();

        @NotNull DirectWritableRegistry<ContentsPack> contentsPacks();

        void mergeAll(@NotNull Registries registries);


        static @NotNull GlobalRegistries registries() {
            return Neko.platform().registryManager().registries();
        }
    }
}
