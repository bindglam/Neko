package io.github.bindglam.neko.content;

import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.registry.WritableRegistry;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface ContentsPackRegistryEntry extends WritableRegistry.RegistryEntry<ContentsPack> {
    @NotNull ContentsPackRegistryEntry id(@NotNull String id);

    @NotNull ContentsPackRegistryEntry version(@NotNull String version);

    @NotNull ContentsPackRegistryEntry author(@NotNull String author);

    @NotNull ContentsPackRegistryEntry packFolder(@NotNull File packFolder);

    @NotNull ContentsPackRegistryEntry registries(@NotNull Registries registries);
}
