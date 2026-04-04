package io.github.bindglam.neko.platform;

import io.github.bindglam.neko.Neko;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface PlatformAdapter {
    @NotNull PlatformItemStack itemStack(@NotNull Object itemStack);

    @NotNull Optional<PlatformItemType> itemType(@NotNull Key key);

    <P, C> @NotNull Optional<PlatformPersistentDataContainer.Type<P, C>> persistentDataType(@NotNull Class<P> clazz);

    static @NotNull PlatformAdapter adapter() {
        return Neko.platform().platformAdapter();
    }
}
