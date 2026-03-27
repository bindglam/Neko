package io.github.bindglam.neko.registry;

import io.github.bindglam.neko.content.item.Item;
import io.github.bindglam.neko.content.item.ItemRegistryEntry;
import io.github.bindglam.neko.content.item.ItemRegistryEntryImpl;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RegistriesImpl implements Registries {
    protected final List<Registry<?>> allRegistries = new ArrayList<>();

    @Getter @Accessors(fluent = true)
    private final EntryWritableRegistry<Item, ItemRegistryEntry> item = create(
            new EntryScalableRegistry<>(ItemRegistryEntryImpl::new)
    );

    protected <T extends Registry<?>> T create(@NotNull T registry) {
        allRegistries.add(registry);
        return registry;
    }

    @Override
    public void lockAll() {
        for (Registry<?> registry : allRegistries) {
            if (registry instanceof WritableRegistry<?> writableRegistry) {
                writableRegistry.lock();
            }
        }
    }

    @Override
    public void unlockAll() {
        for (Registry<?> registry : allRegistries) {
            if (registry instanceof WritableRegistry<?> writableRegistry) {
                writableRegistry.unlock();
            }
        }
    }

    @Override
    public void clearAll() {
        for (Registry<?> registry : allRegistries) {
            if (registry instanceof WritableRegistry<?> writableRegistry) {
                writableRegistry.clear();
            }
        }
    }
}
