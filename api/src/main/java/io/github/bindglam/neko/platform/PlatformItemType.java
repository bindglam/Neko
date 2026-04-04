package io.github.bindglam.neko.platform;

import io.github.bindglam.neko.Neko;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface PlatformItemType extends Keyed, Translatable {
    PlatformItemType NETHER_BRICK = PlatformItemType.get(Key.key(Key.MINECRAFT_NAMESPACE, "nether_brick")).orElseThrow();

    @NotNull PlatformItemStack createItemStack();

    @NotNull Object unwrap();

    static @NotNull Optional<PlatformItemType> get(@NotNull Key key) {
        return Neko.platform().platformAdapter().itemType(key);
    }
}
