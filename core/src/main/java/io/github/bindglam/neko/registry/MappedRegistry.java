package io.github.bindglam.neko.registry;

import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import java.util.*;

public final class MappedRegistry<T> implements Registry<T> {
    private final Map<String, T> map;

    public MappedRegistry(@NotNull Map<Key, T> data) {
        this.map = data.entrySet().stream().collect(
                HashMap::new,
                (m, e) -> m.put(e.getKey().asString(), e.getValue()),
                HashMap::putAll
        );
    }

    @Override
    public @NotNull Optional<T> get(@NotNull Key key) {
        return Optional.ofNullable(map.get(key.asString()));
    }

    @Override
    public @NotNull @Unmodifiable Collection<Pair<Key, T>> entries() {
        List<Pair<Key, T>> result = new ArrayList<>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            result.add(Pair.of(Key.key(entry.getKey()), entry.getValue()));
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return map.values().iterator();
    }
}
