package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.registry.WritableRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ContentRegistryEntry<SELF extends ContentRegistryEntry<SELF, T>, T extends Content<T>> extends WritableRegistry.RegistryEntry<T> {
    @NotNull SELF key(@NotNull Key key);

    @NotNull SELF features(@NotNull List<Feature<T>> features);
}
