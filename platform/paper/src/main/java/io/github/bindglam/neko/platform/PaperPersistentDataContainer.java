package io.github.bindglam.neko.platform;

import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unchecked")
public final class PaperPersistentDataContainer implements PlatformPersistentDataContainer {
    private final PersistentDataContainer persistentDataContainer;

    PaperPersistentDataContainer(PersistentDataContainer persistentDataContainer) {
        this.persistentDataContainer = persistentDataContainer;
    }

    @Override
    public @Nullable <P, C> C get(@NotNull Key key, @NotNull Type<P, C> type) {
        return persistentDataContainer.get(new NamespacedKey(key.namespace(), key.value()), (PersistentDataType<P, C>) type.unwrap());
    }

    @Override
    public <P, C> void set(@NotNull Key key, @NotNull Type<P, C> type, @NonNull C value) {
        persistentDataContainer.set(new NamespacedKey(key.namespace(), key.value()), (PersistentDataType<P, C>) type.unwrap(), value);
    }
}
