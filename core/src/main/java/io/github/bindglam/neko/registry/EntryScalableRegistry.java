package io.github.bindglam.neko.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

public final class EntryScalableRegistry<T, E extends EntryWritableRegistry.RegistryEntry<T>> extends ScalableRegistry<T> implements EntryWritableRegistry<T, E> {
    private final java.util.function.Supplier<E> entrySupplier;

    public EntryScalableRegistry(@NotNull java.util.function.Supplier<E> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    @Override
    public @NotNull T register(@NotNull Key key, @NotNull Consumer<E> entry) {
        if (isLocked) {
            throw new IllegalStateException("The registry is locked");
        }
        if (map.containsKey(key.asString())) {
            throw new IllegalStateException("The registry already contains the key");
        }
        E registryEntry = entrySupplier.get();
        entry.accept(registryEntry);
        T value = registryEntry.toValue();
        map.put(key.asString(), value);
        return value;
    }
}
