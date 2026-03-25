package io.github.bindglam.neko.registry;

import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface Registry<T> extends Iterable<T> {
    @NotNull Optional<T> get(@NotNull Key key);

    @Unmodifiable
    @NotNull Collection<Pair<Key, T>> entries();
}
