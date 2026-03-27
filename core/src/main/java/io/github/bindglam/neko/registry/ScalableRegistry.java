package io.github.bindglam.neko.registry;

import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import java.util.*;

public abstract class ScalableRegistry<T> implements WritableRegistry<T> {
    protected final Map<String, T> map = new HashMap<>();
    protected volatile boolean isLocked = false;

    @Override
    public synchronized void lock() {
        isLocked = true;
    }

    @Override
    public synchronized void unlock() {
        isLocked = false;
    }

    @Override
    public void merge(@NotNull Registry<T> registry) {
        if (isLocked) {
            throw new IllegalStateException("The registry is locked");
        }

        Map<String, T> newMap = new HashMap<>(map);
        for (Pair<Key, T> entry : registry.entries()) {
            String key = entry.key().asString();
            if (newMap.containsKey(key)) {
                throw new IllegalStateException("There is conflict with the registry");
            }
            newMap.put(key, entry.value());
        }
        map.clear();
        map.putAll(newMap);
    }

    @Override
    public void clear() {
        if (isLocked) {
            throw new IllegalStateException("The registry is locked");
        }
        map.clear();
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
